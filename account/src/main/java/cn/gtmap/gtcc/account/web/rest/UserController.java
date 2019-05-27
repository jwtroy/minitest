package cn.gtmap.gtcc.account.web.rest;

import cn.gtmap.gtcc.account.service.RoleService;
import cn.gtmap.gtcc.account.service.UserService;
import cn.gtmap.gtcc.domain.sec.Department;
import cn.gtmap.gtcc.domain.sec.Role;
import cn.gtmap.gtcc.domain.sec.User;
import cn.gtmap.gtcc.domain.sec.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * .UserController
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/22 13:38
 */
@RestController
@RequestMapping("/rest/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static final String COMMON_USER = "user";

    final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @RequestMapping("/list")
    public Page getUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @GetMapping("/queryUsers")
    public Page<User> queryUsers(Pageable pageable,
                                 @RequestParam("username") String username,
                                 @RequestParam("alias") String alias) {
        Page<User> userPage = userService.queryUsers(pageable, username, alias);
        List<User> userList = userPage.getContent();
        userList.forEach(user -> user.setRoles(user.getRoles().stream()
                        .filter(role -> !role.getName().equals(COMMON_USER))
                        .collect(Collectors.toList())));
        return userPage;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @GetMapping("/username")
    public User getUserByUsername(@RequestParam("username") String username) {
        User user = null;
        try {
            user = userService.getUserByUsername(username);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return user;
    }

    @GetMapping("/findUserByStr")
    public List<User> findUserByStr(@RequestParam("username") String username) {
        return userService.findUserByStr(username);
    }

    @GetMapping("/{id}/info")
    public UserInfo getUserInfo(@PathVariable("id") String id) {
        return userService.getUserInfo(id);
    }

    @PutMapping("/{id}/info/update")
    public UserInfo updateUserInfo(@PathVariable("id") String id, @RequestBody UserInfo userInfo) {
        return userService.saveUserInfo(id, userInfo);
    }

    @GetMapping("/{id}/roles")
    public List getUserRoles(@PathVariable("id") String id) {
        return userService.getUserRoles(id);
    }

    @GetMapping("/{id}/findRolesByUserId")
    public List<Role> findRolesByUserId(@PathVariable("id") String id) {
        return userService.getUserRoles(id);
    }


    @PostMapping("/{id}/roles/update")
    public boolean updateUserRoles(@PathVariable("id") String id, @RequestBody Iterable<String> roleIds) {
        userService.updateUserByRoleIds(id, roleIds);
        return true;
    }

    @PostMapping("/{id}/departments/update")
    boolean updateUserDepartments(@PathVariable("id") String id, @RequestBody Iterable<String> departmentIds) {
        userService.updateUserByDepartmentIds(id, departmentIds);
        return true;
    }

    @GetMapping("/{id}/departments")
    public List getUserDepartments(@PathVariable("id") String id) {
        return userService.getUserDepartments(id);
    }

    @GetMapping("/{id}/findDepartMentsByUserId")
    public List<Department> findDepartMentsByUserId(@PathVariable("id") String id) {
        return userService.getUserDepartments(id);
    }

    /**
     * @param user such as
     *             {
     *             "password": "t7",
     *             "enabled": true,
     *             "username": "t7",
     *             "alias": "t_7_ww2",
     *             "expired": false,
     *             "locked": false,
     *             "userInfo": {
     *             "birthday": "2017-09-23 14:54:53",
     *             "avatar": null,
     *             "email": "lanxy88@gmail.com",
     *             "mobile": "13451832127",
     *             "tel": "113",
     *             "address": "samove",
     *             "zipCode": "223602",
     *             "description": null
     *             }
     *             }
     * @return
     */
    @PutMapping("/add")
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}/update")
    public User updateUser(@PathVariable("id") String id, @RequestBody User user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @PostMapping("/{id}/disable")
    public boolean disableUser(@PathVariable("id") String id) {
        userService.disableUser(userService.getUser(id));
        return true;
    }

    @DeleteMapping("/{id}/delete")
    public boolean deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return true;
    }

    /**
     * 密码校验
     *
     * @param password
     * @param id
     * @return
     */
    @PostMapping("/checkUserPassword")
    public boolean checkUserPassword(@RequestParam(name = "password") String password, @RequestParam(name = "id") String id) {
        return passwordEncoder.matches(password, userService.getUser(id).getPassword());
    }

    /**
     * @param pageable
     * @param id
     * @return
     */
    @GetMapping("/findUserByDepartmentId")
    public Page<User> findUserByDepartmentId(Pageable pageable, @RequestParam("id") String id) {
        return userService.findUserByDepartmentId(pageable, id);
    }

}
