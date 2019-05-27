package cn.gtmap.gtcc.account.service.impl;

import cn.gtmap.gtcc.account.dao.OperationRepo;
import cn.gtmap.gtcc.account.dao.RoleRepo;
import cn.gtmap.gtcc.account.dao.UserRepo;
import cn.gtmap.gtcc.account.service.RoleService;
import cn.gtmap.gtcc.domain.sec.Operation;
import cn.gtmap.gtcc.domain.sec.Role;
import cn.gtmap.gtcc.domain.sec.User;
import cn.gtmap.gtcc.ex.RoleNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * .RoleServiceImpl
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/22 16:34
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl<R extends Role, O extends Operation> implements RoleService<R, O> {

    @Autowired
    RoleRepo<R> roleRepo;

    @Autowired
    OperationRepo<O> operationRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private EntityManager entityManager;

    /**
     * get role by id
     *
     * @param id
     * @return
     */
    @Override
    public R getRole(String id) {
        return roleRepo.findOne(id);
    }

    /**
     * get role by name
     *
     * @param name
     * @return
     */
    @Override
    public R getRoleByName(String name) {
        Optional<R> role = roleRepo.findByName(name);
        if (role.isPresent()) {
            return role.get();
        }
        throw new RoleNotFoundException(name);
    }

    /**
     * get roles
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<R> getRoles(Pageable pageable) {
        return roleRepo.findAll(pageable);
    }

    /**
     * get role's users
     *
     * @param id
     * @param pageable
     * @return
     */
    @Override
    public Page<User> getRoleUsers(String id, Pageable pageable) {
        return userRepo.findByRolesId(id, pageable);
    }

    @Override
    public Page<R> queryRoles(Pageable pageable, String name, String alias) {
        int start = pageable.getPageNumber() * pageable.getPageSize();
        String hql = "select r from Role r where 1=1 ";

        Query query;

        if (StringUtils.isNotBlank(name)) {
            hql = hql + " and r.name like :name";
        }
        if (StringUtils.isNotBlank(alias)) {
            hql = hql + " and r.alias like :alias";
        }

        query = entityManager.createQuery(hql, Role.class);

        if (StringUtils.isNotBlank(name)) {
            query.setParameter("name", "%" + name + "%");
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

    @Override
    public List<User> getRoleUserList(String id) {
        return userRepo.findByRolesId(id);
    }

    @Override
    public Page<User> getUsersByRole(String id, Pageable pageable) {
        return userRepo.findByRolesId(id, pageable);
    }

    /**
     * get role's operations
     *
     * @param roleId
     * @return
     */
    @Override
    public Iterable<Operation> getRoleOperations(String roleId) {
        return getRole(roleId).getOperations();
    }

    /**
     * save role
     *
     * @param role
     * @return
     */
    @Override
    @Transactional
    public R saveRole(R role) {
        return roleRepo.save(role);
    }

    /**
     * save roles
     *
     * @param roles
     * @return
     */
    @Override
    @Transactional
    public Iterable<R> saveRoles(Iterable<R> roles) {
        return roleRepo.save(roles);
    }

    /**
     * delete role by id
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(String id) {
        deleteRoleRef(id);
        roleRepo.deleteOperaRef(id);
        roleRepo.delete(id);
    }

    /**
     * delete
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleRef(String id) {
        roleRepo.deleteRef(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRefByUser(String roleId,String userId) {
        roleRepo.deleteRefByUser(roleId,userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRef(String id) {
        roleRepo.deleteUserRef(id);
    }

    /**
     * delete role
     *
     * @param role
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(R role) {
        roleRepo.delete(role);
    }


    @Override
    @Transactional
    public List<Role> findAll() {
        return (List<Role>) roleRepo.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleOperations(String roleId, Iterable<String> operationIds) {
        saveRoleOperations(roleId, operationRepo.findAll(operationIds));
    }

    @Override
    @Transactional
    public Iterable<O> saveRoleOperations(String userId, Iterable<O> operations) {
        R role = getRole(userId);
        role.setOperations(((List<Operation>) operationRepo.save(operations)));
        return (List<O>) updateRole(role).getOperations();
    }

    @Override
    @Transactional
    public R updateRole(R role) {
        R t = getRole(role.getId());
        BeanUtils.copyProperties(role, t, "id", "name", "alias", "operations");
        return saveRole(t);
    }

    @Override
    public List<Role> findRoleByAlias(String alias) {
        return roleRepo.findRoleByAliasLike(alias);
    }

    @Override
    public List<Role> findRoleByIds(List<String> ids) {
        return roleRepo.findRoleByIdIn(ids);
    }

    @Override
    public Role findRoleByRoleAlias(String alias) {
        return roleRepo.findRoleByRoleAlias(alias);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepo.findRoleByName(name);
    }
}
