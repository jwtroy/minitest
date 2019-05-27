package cn.gtmap.gtcc.account.service.impl;

import cn.gtmap.gtcc.account.dao.AuthorityRepo;
import cn.gtmap.gtcc.account.service.AuthorityService;
import cn.gtmap.gtcc.domain.sec.Authority;
import cn.gtmap.gtcc.domain.sec.Department;
import cn.gtmap.gtcc.domain.sec.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * .AuthorityServiceImpl
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/29 21:08
 */
@Service
@Transactional(readOnly = true)
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    AuthorityRepo authorityRepo;


    /**
     * getAuthorities
     *
     * @param type
     * @param pageable
     * @return
     */
    @Override
    public Page<Authority> getAuthorities(String type, Pageable pageable) {
        return authorityRepo.findAllByType(type, pageable);
    }

    /**
     * get all authorities
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Authority> getAllAuthorities(Pageable pageable) {
        return authorityRepo.findAll(pageable);
    }

    /**
     * get user's authorities
     *
     * @param username
     * @return
     */
    @Override
    public List<Authority> getUserAuthorities(String username, String type) {
        return authorityRepo.findAllByUsernameAndType(username, type);
    }

    /**
     * get user simple authorities
     *
     * @param username
     * @param type
     * @return
     */
    @Override
    public List<String> getUserSimpleAuthorities(String username, String type) {
        List<Authority> authorities = getUserAuthorities(username, type);
        return authorities.parallelStream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
    }

    /**
     * save authority
     *
     * @param authority
     */
    @Override
    @Transactional
    public Authority saveAuthority(Authority authority) {
        return authorityRepo.save(authority);
    }

    /**
     * save authorities
     *
     * @param authorities
     */
    @Override
    @Transactional
    public Iterable<Authority> saveAuthorities(Iterable<Authority> authorities) {
        return authorityRepo.save(authorities);
    }

    /**
     * save authorities
     *
     * @param type
     * @param username
     * @param authorities
     * @return
     */
    @Override
    @Transactional
    public Iterable<Authority> saveAuthorities(String type, String username, String... authorities) {
        List<Authority> al = new ArrayList<>();
        for (String a : authorities) {
            al.add(new Authority().setUsername(username).setAuthority(a).setType(type));
        }
        return saveAuthorities(al);
    }

    /**
     * delete authorities
     *
     * @param type
     * @param username
     * @param authorities
     * @return
     */
    @Override
    @Transactional
    public void deleteAuthorities(String type, String username, String... authorities) {
        for (String authority : authorities) {
            authorityRepo.deleteAuthorities(type, username, authority);
        }
    }

    /**
     * delete authority
     *
     * @param authority
     */
    @Override
    @Transactional
    public void deleteAuthorities(Authority authority) {
        authorityRepo.delete(authority);
    }

    /**
     * delete authorities
     *
     * @param authorities
     */
    @Override
    @Transactional
    public void deleteAuthorities(Iterable<Authority> authorities) {
        authorityRepo.delete(authorities);
    }

    /**
     * delete authority by id
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteAuthorities(String id) {
        authorityRepo.delete(id);
    }


    /**
     * check authority
     *
     * @param userName
     * @param authority
     * @return
     */
    @Override
    public boolean checkAuthority(String userName, String authority) {
        return !CollectionUtils.isEmpty(authorityRepo.findAllByUsernameAndAuthority(userName, authority));
    }

    @Override
    public boolean checkAuthority2(List<Role> roleList, List<Department> departmentList, String userName, String alias, String authority) {
        Boolean hasRight = false;
        List<Authority> list = new ArrayList<>();
        //查询角色是否拥有权限
        for (Role role : roleList) {
            list = authorityRepo.findAllByUsernameAndAuthority(role.getAlias(), "r".concat("-").concat(authority).concat("-").concat(role.getName()));
            if (!list.isEmpty()) {
                hasRight = true;
                break;
            }
        }
        if (!hasRight) {
            //查询部门是否拥有权限grantAthority
            for (Department department : departmentList) {
                list = authorityRepo.findAllByUsernameAndAuthority(department.getName(), "d".concat("-").concat(authority).concat("-").concat(department.getName()));
                if (!list.isEmpty()) {
                    hasRight = true;
                    break;
                }
            }
        }
        if (!hasRight) {
            //角色无权限再查用户
            list = authorityRepo.findAllByUsernameAndAuthority(alias, "u".concat("-").concat(authority).concat("-").concat(userName));
            if (!list.isEmpty()) {
                hasRight = true;
            }
        }
        return hasRight;
    }

    /**
     * check authorities
     *
     * @param authorities
     * @return
     */
    @Override
    public List<Boolean> checkAuthorities(List<Authority> authorities) {
        List<Boolean> authorized = new ArrayList<>();
        for (Authority authority : authorities) {
            authorized.add(checkAuthority(authority.getUsername(), authority.getAuthority()));
        }
        return authorized;
    }

    /**
     * find by authority likes
     *
     * @param authorityStr
     * @param pageable
     * @return
     */
    @Override
    public Page<Authority> getAuthoritiesByAuthorityLike(String authorityStr, Pageable pageable) {
        String authorityLike = "%".concat(authorityStr).concat("%");
        return authorityRepo.findByAuthorityLike(authorityLike, pageable);
    }

    @Override
    public Authority getAuthorityById(String id) {
        return authorityRepo.findOne(id);
    }

    @Override
    public Authority queryByAuthority(String authority) {
        return authorityRepo.findByAuthority(authority);
    }
}
