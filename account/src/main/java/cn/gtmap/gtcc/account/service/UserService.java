package cn.gtmap.gtcc.account.service;

import cn.gtmap.gtcc.domain.sec.Department;
import cn.gtmap.gtcc.domain.sec.Role;
import cn.gtmap.gtcc.domain.sec.User;
import cn.gtmap.gtcc.domain.sec.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * .UserService
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/21 14:48
 */
public interface UserService<U extends User, R extends Role, D extends Department> extends UserDetailsService {

    /**
     * get user by id
     *
     * @param id
     * @return
     */
    U getUser(String id);

    /**
     * get user info
     *
     * @param id
     * @return
     */
    UserInfo getUserInfo(String id);

    /**
     * get user's roles
     *
     * @param id
     * @return
     */
    List<R> getUserRoles(String id);

    /**
     * get user's departments
     *
     * @param id
     * @return
     */
    List<D> getUserDepartments(String id);

    /**
     * get use by name
     *
     * @param username
     * @return
     */
    U getUserByUsername(String username);

    public List<U> findUserByStr(String strContent);

    /**
     * get users by pageable
     *
     * @param pageable
     * @return
     */
    Page<U> getUsers(Pageable pageable);

    Page<U> queryUsers(Pageable pageable, String username, String alias);

    /**
     * save user
     *
     * @param user
     * @return
     */
    U saveUser(U user);

    /**
     * save users
     *
     * @param users
     * @return
     */
    Iterable<U> saveUsers(Iterable<U> users);

    /**
     * update user
     *
     * @param user
     * @return
     */
    U updateUser(U user);

    /**
     * delete user
     *
     * @param id
     */
    void deleteUser(String id);

    /**
     * delete user
     *
     * @param user
     */
    void deleteUser(U user);

    /**
     * disable user
     *
     * @param user
     */
    void disableUser(U user);

    /**
     * save user info
     *
     * @param userId
     * @param userInfo
     * @return
     */
    UserInfo saveUserInfo(String userId, UserInfo userInfo);

    /**
     * save user roles
     *
     * @param userId
     * @param roles
     * @return
     */
    Iterable<R> saveUserRoles(String userId, Iterable<R> roles);

    Iterable<D> saveUserDepartmnet(String userId, Iterable<D> departments);

    /**
     * updateUserByRoleIds
     *
     * @param userId
     * @param roleIds
     */
    void updateUserByRoleIds(String userId, Iterable<String> roleIds);

    void updateUserByDepartmentIds(String userId, Iterable<String> departmentIds);

    /**
     * findUserByDepartmentId
     *
     * @param pageable
     * @param id
     * @return
     */
    Page<U> findUserByDepartmentId(Pageable pageable, String id);
}
