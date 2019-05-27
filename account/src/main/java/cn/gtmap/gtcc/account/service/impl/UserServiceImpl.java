package cn.gtmap.gtcc.account.service.impl;

import cn.gtmap.gtcc.account.config.Author;
import cn.gtmap.gtcc.account.dao.DepartmentRepo;
import cn.gtmap.gtcc.account.dao.RoleRepo;
import cn.gtmap.gtcc.account.dao.UserInfoRepo;
import cn.gtmap.gtcc.account.dao.UserRepo;
import cn.gtmap.gtcc.account.service.AuthorityService;
import cn.gtmap.gtcc.account.service.UserService;
import cn.gtmap.gtcc.domain.sec.*;
import cn.gtmap.gtcc.ex.UserNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * .UserServiceImpl
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/21 14:55
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl<U extends User, R extends Role, D extends Department> implements UserService<U, R, D> {

    private static final  String ROLE_PERFIX = "ROLE_";

    @Autowired
    UserRepo<U> userRepo;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    UserInfoRepo<UserInfo> userInfoRepo;

    @Autowired
    RoleRepo<R> roleRepo;
    @Autowired
    DepartmentRepo<D> departmentRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthorityService authorityService;


    /**
     * get user by id
     *
     * @param id
     * @return
     */
    @Override

    public U getUser(String id) {
        U user = userRepo.findOne(id);
        if (user != null) {
            return user;
        }
        throw new UserNotFoundException(id);
    }

    /**
     * get user info
     *
     * @param id
     * @return
     */
    @Override
    public UserInfo getUserInfo(String id) {
        return getUser(id).getUserInfo();
    }

    /**
     * get user's roles
     *
     * @param id
     * @return
     */
    @Override
    public List<R> getUserRoles(String id) {
        return (List<R>) getUser(id).getRoles();
    }

    /**
     * get user's departments
     *
     * @param id
     * @return
     */
    @Override
    public List<D> getUserDepartments(String id) {
        return (List<D>) getUser(id).getDepartments();
    }

    /**
     * get use by name
     *
     * @param username
     * @return
     */
    @Override
    public U getUserByUsername(String username) {
        Optional<U> user = userRepo.findByUsername(username);
        if (!user.isPresent()) {
            throw new UserNotFoundException(username);
        }
        return user.get();
    }

    @Override
    public List<U> findUserByStr(String strContent) {
        return userRepo.findByUsernameContaining(strContent);
    }


    /**
     * get users by pageable
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<U> getUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public Page<U> queryUsers(Pageable pageable, String username, String alias) {
        int start = pageable.getPageNumber() * pageable.getPageSize();
        String hql = "select u from User u where 1=1 ";

        Query query;

        if (StringUtils.isNotBlank(username)) {
            hql = hql + " and u.username like :username";
        }
        if (StringUtils.isNotBlank(alias)) {
            hql = hql + " and u.alias like :alias";
        }

        query = entityManager.createQuery(hql, User.class);

        if (StringUtils.isNotBlank(username)) {
            query.setParameter("username", "%" + username + "%");
        }
        if (StringUtils.isNotBlank(alias)) {
            query.setParameter("alias", "%" + alias + "%");
        }
        int total = query.getResultList().size();
        // 判断分页
        if (start < total && pageable.getPageSize() > 0) {
            query.setFirstResult(start);
            query.setMaxResults(pageable.getPageSize());
        }
        return new PageImpl<>(query.getResultList(), pageable, total);
    }

    /**
     * save user
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public U saveUser(U user) {
        UserInfo info = user.getUserInfo();
        if (info != null) {
            user.setUserInfo(userInfoRepo.save(info));
        }
        try {
            user = userRepo.save(encodePwd(user));
            return user;
        } finally {
            updateUserAuthorities(user);
        }
    }

    /**
     * save users
     *
     * @param users
     * @return
     */
    @Override
    @Transactional
    public Iterable<U> saveUsers(Iterable<U> users) {
        for (U user : users) {
            encodePwd(user);
        }
        try {
            users = userRepo.save(users);
            return users;
        } finally {
            for (U user : users) {
                updateUserAuthorities(user);
            }
        }
    }

    /**
     * update user
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public U updateUser(U user) {
        U t = getUser(user.getId());
        BeanUtils.copyProperties(user, t, "id", "password", "userInfo", "roles", "departments");
        if (StringUtils.isNotBlank(user.getPassword())) {
            t.setPassword(user.getPassword());
        }
        return saveUser(t);
    }

    /**
     * encode password
     *
     * @param user
     * @return
     */
    protected U encodePwd(U user) {
        String pwd = user.getPassword();
        if (StringUtils.isNotBlank(pwd) && pwd.length() < 20) {
            return (U) user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return user;
    }

    /**
     * delete user
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String id) {
        roleRepo.deleteUserRef(id);
        departmentRepo.deleteDepartmentRef(id);
        userRepo.delete(id);
    }

    /**
     * delete user
     *
     * @param user
     */
    @Override
    @Transactional
    public void deleteUser(U user) {
        userRepo.delete(user);
    }

    /**
     * disable user
     *
     * @param user
     */
    @Override
    @Transactional
    public void disableUser(U user) {
        user.setEnabled(false);
        saveUser(user);
    }

    /**
     * save user info
     *
     * @param userId
     * @param userInfo
     * @return
     */
    @Override
    @Transactional
    public UserInfo saveUserInfo(String userId, UserInfo userInfo) {
        U user = getUser(userId);
        UserInfo t = user.getUserInfo();
        if (t != null) {
            BeanUtils.copyProperties(userInfo, t, "id", "user");
            return userInfoRepo.save(t);
        } else {
            userInfo = userInfoRepo.save(userInfo);
            user.setUserInfo(userInfo);
            return saveUser(user).getUserInfo();
        }
    }

    /**
     * save user roles
     *
     * @param userId
     * @param roles
     * @return
     */
    @Override
    @Transactional
    public Iterable<R> saveUserRoles(String userId, Iterable<R> roles) {
        U user = getUser(userId);
        user.setRoles(((List<Role>) roleRepo.save(roles)));
        return (List<R>) updateUser(user).getRoles();
    }

    @Override
    @Transactional
    public Iterable<D> saveUserDepartmnet(String userId, Iterable<D> departments) {
        U user = getUser(userId);
        user.setDepartments(((List<Department>) departmentRepo.save(departments)));
        return (List<D>) updateUser(user).getDepartments();
    }

    /**
     * updateUserByRoleIds
     *
     * @param userId
     * @param roleIds
     */
    @Override
    @Transactional
    public void updateUserByRoleIds(String userId, Iterable<String> roleIds) {
        saveUserRoles(userId, roleRepo.findAll(roleIds));
    }

    @Override
    @Transactional
    public void updateUserByDepartmentIds(String userId, Iterable<String> departmentIds) {
        saveUserDepartmnet(userId, departmentRepo.findAll(departmentIds));
    }

    @Override
    public Page<U> findUserByDepartmentId(Pageable pageable, String id) {
        return userRepo.findByDepartmentsId(id, pageable);
    }

    /**
     * load user by username
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<U> u = userRepo.findByUsername(username);
        if (!u.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        List<Authority> authorities = authorityService.getUserAuthorities(username, Author.LOGIN.toString());
        return buildUserDetail(u.get(), authorities);
    }

    protected UserDetails buildUserDetail(User user, List<Authority> authorities) {
        return new UD(user, authorities);
    }

    /**
     * update user authorities
     *
     * @param user
     */
    private void updateUserAuthorities(U user) {
        String username = user.getUsername();
        List<Authority> authorities = authorityService.getUserAuthorities(username, Author.LOGIN.toString());
        authorityService.deleteAuthorities(authorities);
        List<Authority> na = new ArrayList<>();
        List<Role> roles = user.getRoles();
        if (roles != null) {
            for (Role role : roles) {
                na.add(new Authority().setType(Author.LOGIN.toString())
                        .setUsername(username)
                        .setAuthority(ROLE_PERFIX.concat(role.getName()).toUpperCase()));
                List<Operation> ops = role.getOperations();
                if (ops != null) {
                    for (Operation op : ops) {
                        na.add(new Authority().setType(Author.LOGIN.toString())
                                .setUsername(username)
                                .setAuthority(ROLE_PERFIX.concat(role.getName()).concat("_".concat(op.getName())).toUpperCase()));
                    }
                }
            }
        }
        authorityService.saveAuthorities(na);
    }

    /**
     * save authorities
     *
     * @param username
     * @param authorities
     */
    private void saveUserAuthorities(String username, String... authorities) {
        authorityService.saveAuthorities(Author.LOGIN.toString(), username, authorities);
    }

    /**
     * delete authorities
     *
     * @param username
     * @param authorities
     */
    private void deleteAuthorities(String username, String... authorities) {
        authorityService.deleteAuthorities(Author.LOGIN.toString(), username, authorities);
    }

    /**
     * .UD
     */
    public static class UD extends User implements UserDetails {

        List<GrantedAuthority> authorities = new ArrayList<>();

        public UD(User user, List<Authority> authes) {
            BeanUtils.copyProperties(user, this);
            if (authes != null) {
                authes.parallelStream().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getAuthority())));
            }
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }


        @Override
        public boolean isAccountNonExpired() {
            return !isExpired();
        }


        @Override
        public boolean isAccountNonLocked() {
            return !isLocked();
        }


        @Override
        public boolean isCredentialsNonExpired() {
            return !isExpired();
        }
    }
}
