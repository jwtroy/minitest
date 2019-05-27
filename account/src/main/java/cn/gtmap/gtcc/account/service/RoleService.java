package cn.gtmap.gtcc.account.service;

import cn.gtmap.gtcc.domain.sec.Operation;
import cn.gtmap.gtcc.domain.sec.Role;
import cn.gtmap.gtcc.domain.sec.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * .RoleService
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/22 16:34
 */
public interface RoleService<R extends Role, O extends Operation> {

    /**
     * get role by id
     *
     * @param id
     * @return
     */
    R getRole(String id);

    /**
     * get role by name
     *
     * @param name
     * @return
     */
    R getRoleByName(String name);

    /**
     * get roles
     *
     * @param pageable
     * @return
     */
    Page<R> getRoles(Pageable pageable);

    /**
     * get role's users
     *
     * @param id
     * @param pageable
     * @return
     */
    Page<User> getRoleUsers(String id, Pageable pageable);

    Page<R> queryRoles(Pageable pageable, String name, String alias);

    List<User> getRoleUserList(String id);

    public Page<User> getUsersByRole(String id, Pageable pageable);

    /**
     * get role's operations
     *
     * @param roleId
     * @return
     */
    Iterable<Operation> getRoleOperations(String roleId);

    /**
     * save role
     *
     * @param role
     * @return
     */
    R saveRole(R role);

    /**
     * save roles
     *
     * @param roles
     * @return
     */
    Iterable<R> saveRoles(Iterable<R> roles);

    /**
     * delete role by id
     *
     * @param id
     */
    void deleteRole(String id);

    /**
     * delete ref
     *
     * @param id
     */
    void deleteRoleRef(String id);

    void deleteRefByUser(String roleId, String userId);

    /**
     * delete ref
     *
     * @param id
     */
    void deleteUserRef(String id);

    /**
     * delete role
     *
     * @param role
     */
    void deleteRole(R role);

    /**
     * list
     *
     * @return
     */
    public List<Role> findAll();

    /**
     * update
     *
     * @param id
     * @param operationIds
     */
    void updateRoleOperations(String id, Iterable<String> operationIds);

    /**
     * save
     *
     * @param userId
     * @param operations
     * @return
     */
    public Iterable<O> saveRoleOperations(String userId, Iterable<O> operations);

    /**
     * update role
     *
     * @param role
     * @return
     */
    public R updateRole(R role);

    List<Role> findRoleByAlias(String alias);

    List<Role> findRoleByIds(List<String> ids);

    Role findRoleByRoleAlias(String alias);

    Role findRoleByName(String name);
}
