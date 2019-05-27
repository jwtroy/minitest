package cn.gtmap.gtcc.account.service.impl;

import cn.gtmap.gtcc.account.dao.OperationRepo;
import cn.gtmap.gtcc.account.dao.UserInfoRepo;
import cn.gtmap.gtcc.account.service.RoleService;
import cn.gtmap.gtcc.account.service.UserService;
import cn.gtmap.gtcc.domain.sec.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * .UserServiceImplTest
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/21 14:56
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class UserServiceImplTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    OperationRepo operationRepo;

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    DepartmentServiceImpl departmentService;

    @Test
    public void getUser() throws Exception {
    }

    @Test
    public void getUserByUsername() throws Exception {
        logger.debug(userService.getUserByUsername("test1").getId());

    }

    @Test
    public void saveUser() throws Exception {
        User user = new User();
        user.setUsername("test").setAlias("test").setPassword("test");
        userService.saveUser(user);
        logger.debug(userService.getUserByUsername("test").toString());
    }

    @Test
    public void saveUser1() throws Exception {
        User user = new User();
        user.setUsername("test_1").setAlias("test_1").setPassword("test1");
        UserInfo userInfo = new UserInfo();
        userInfo.setBirthday(new Date()).setEmail("lanxy88@gmail.com");
        user.setUserInfo(userInfo);
        User u = userService.saveUser(user);
        logger.debug(userService.getUser(u.getId()).toString());
    }

    @Test
    public void saveUsers3() {
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setUsername("test" + i).setPassword("test" + i);
            UserInfo userInfo = new UserInfo();
            userInfo.setBirthday(new Date()).setEmail("lanxy88@gmail.com");
            user.setUserInfo(userInfo);
            userService.saveUser(user);
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void search() {
        Page<User> users = userService.getUsers(new PageRequest(0, 1000));
        for (User user : users) {
            logger.debug("------ " + user.getUserInfo().toString());
        }
    }

    @Test
    public void deleteUser() throws Exception {
        User user = new User();
        user.setUsername("test2").setPassword("test2");
        UserInfo userInfo = new UserInfo();
        userInfo.setBirthday(new Date()).setEmail("lanxy88@gmail.com");
        user.setUserInfo(userInfo);
        User u = userService.saveUser(user);
        logger.debug(userService.getUser(u.getId()).toString());
        userService.deleteUser(u);
    }

    @Test
    public void deleteUser1() throws Exception {
    }

    @Test
    public void loadUserByUsername() throws Exception {
    }

    @Test
    public void saveUserAndRoles() throws Exception {

        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation().setName("read").setAlias("read"));
        operations.add(new Operation().setName("write").setAlias("write"));
        operationRepo.save(operations);

        List<Role> roles = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            roles.add(new Role("admin" + j, "admin" + j).setOperations(operations));
        }

        List<Department> departments = new ArrayList<>();
        Department d1 = new Department().setName("部门1").setCode("bm1");
        Department d11 = new Department().setName("部门11").setCode("bm11").setParent(d1);
        Department d2 = new Department().setName("部门2").setCode("bm2");
        Department d21 = new Department().setName("部门21").setCode("bm21").setParent(d2);
        Department d22 = new Department().setName("部门22").setCode("bm22").setParent(d2);
        departments.add(d1);
        departments.add(d11);
        departments.add(d2);
        departments.add(d22);
        departments.add(d21);

        departmentService.saveDepartment(departments);

        roleService.saveRoles(roles);

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setUsername("test" + i).setPassword("test" + i).setAlias("test_" + i);
            UserInfo userInfo = new UserInfo();
            userInfo.setBirthday(new Date()).setEmail("lanxy88@gmail.com");
            userInfoRepo.save(userInfo);
            user.setUserInfo(userInfo);
            user.setRoles(roles);
            user.setDepartments(departments);
            users.add(user);
            userService.saveUser(user);
        }
//        userService.saveUsers(users);
    }

    @Test
    public void updateUser() throws Exception {
        User user = userService.getUserByUsername("test1");
        user.setAlias("test1_updated1");
        List<Role> roles = new ArrayList<>();
        for (int j = 2; j < 8; j++) {
            roles.add(new Role("user" + j, "user" + j));
        }
        roles = (List<Role>) roleService.saveRoles(roles);
        user.setRoles(roles);
        userService.saveUser(user);
    }

    @Test
    public void testInsertNUser() throws Exception {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation().setName("read").setAlias("read"));
        operations.add(new Operation().setName("write").setAlias("write"));
        operationRepo.save(operations);

        List<Role> roles = new ArrayList<>();
        roles.add(new Role("user", "一般用户").setOperations(operations));
        roles.add(new Role("admin", "管理员").setOperations(operations));
        roleService.saveRoles(roles);

        List<User> users = new ArrayList<>();
        UserInfo userInfo = new UserInfo().setBirthday(new Date()).setEmail("lanxy88@gmail.com");
        userInfoRepo.save(userInfo);
        User user = new User().setUsername("user").setPassword("1").setAlias("用户").setUserInfo(userInfo);
        user.setRoles(Arrays.asList(roles.get(0)));
        users.add(user);
        User admin = new User().setUsername("admin").setPassword("1").setAlias("管理员");
        admin.setRoles(roles);
        users.add(admin);
        userService.saveUsers(users);
    }
}