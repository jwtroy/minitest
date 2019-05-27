package cn.gtmap.gtcc.account.service.impl;

import cn.gtmap.gtcc.account.dao.OperationRepo;
import cn.gtmap.gtcc.account.service.DepartmentService;
import cn.gtmap.gtcc.account.service.RoleService;
import cn.gtmap.gtcc.account.service.UserService;
import cn.gtmap.gtcc.domain.sec.Department;
import cn.gtmap.gtcc.domain.sec.Role;
import cn.gtmap.gtcc.domain.sec.User;
import cn.gtmap.gtcc.utils.utils.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zgl on 2018-05-15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoExportImplTest {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    OperationRepo operationRepo;

    final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Test
    public void importExcel() {
        System.out.println("开始读取excel-------------------------");
        long startRead = System.currentTimeMillis();
        File file = new File("C:\\Users\\Administrator\\Desktop\\连云港人员.xlsx");
        ExcelUtil excelUtil = new ExcelUtil();
        ArrayList<ArrayList<Object>> result = excelUtil.readExcel(file);
        System.out.println("读取excel结束-------------------------");
        long endRead = System.currentTimeMillis();
        double readTime = (endRead - startRead);
        System.out.println("读取excel共用时-------------------------" + readTime);

        List<User> userList = new ArrayList<>();
        String firstLevelDepartmentName = "";
        String secondeLevelDepartmentName = "";
        String departmentName = "";
        String roleName = "";
        Role role = new Role();
        String code = "";
        Department department = new Department();
        Department parentDepartMent = new Department();
        for (int i = 0; i < result.size(); i++) {
            User user = new User();
            List<Role> roleList = new ArrayList<>();
            List<Department> departmentList = new ArrayList<>();
            for (int j = 0; j < result.get(i).size(); j++) {
                if (j == 7) {
                    //获取code
                    /*System.out.println(i + "行 " + j + "列  " + result.get(i).get(j).toString());*/
                    if (!"code".equals(result.get(i).get(j).toString()) && !"".equals(result.get(i).get(j).toString())) {
                        code = result.get(i).get(j).toString();
                    }
                }
                if (j == 6) {
                    //获取二级部门
                    /*System.out.println(i + "行 " + j + "列  " + result.get(i).get(j).toString());*/
                    if (!"二级部门".equals(result.get(i).get(j).toString()) && !"".equals(result.get(i).get(j).toString())) {
                        secondeLevelDepartmentName = result.get(i).get(j).toString();
                        department = departmentService.findByDepartmentName(secondeLevelDepartmentName);
                    }

                }
                if (j == 5) {
                    //获取一级部门
                    if (!"一级部门".equals(result.get(i).get(j).toString()) && !"".equals(result.get(i).get(j).toString())) {
                        firstLevelDepartmentName = result.get(i).get(j).toString();
                        parentDepartMent = departmentService.findByDepartmentName(firstLevelDepartmentName);
                    }
                }
                if (j == 4) {
                    //获取角色

                    if (!"角色".equals(result.get(i).get(j).toString()) && !"".equals(result.get(i).get(j).toString())) {
                        roleName = result.get(i).get(j).toString();
                    }
                }
                if (j == 3) {
                    //获取密码
                    user.setPassword(result.get(i).get(j).toString());
                }
                if (j == 2) {
                    //获取别名
                    user.setUsername(result.get(i).get(j).toString());
                }
                if (j == 1) {
                    //获取用户名
                    user.setAlias(result.get(i).get(j).toString());
                }
                if (StringUtils.isNotBlank(secondeLevelDepartmentName)) {
                    departmentName = secondeLevelDepartmentName;
                } else {
                    departmentName = firstLevelDepartmentName;
                }
                try {
                    if (parentDepartMent == null) {
                        parentDepartMent = new Department();
                        parentDepartMent.setName(firstLevelDepartmentName);
                        parentDepartMent.setWeight(3);
                        parentDepartMent.setCode(code);
                        parentDepartMent.setRegionCode(code);
                        parentDepartMent.setParent(null);
                        departmentService.saveDepartment(parentDepartMent);
                    }
                    if (department == null) {
                        department = new Department();
                        department.setName(secondeLevelDepartmentName);
                        department.setWeight(3);
                        department.setCode(code);
                        department.setRegionCode(code);
                        department.setParent(parentDepartMent);
                        departmentService.saveDepartment(department);
                    }
                } catch (Exception e) {
                    logger.error("父级部门name---------------------------" + parentDepartMent.getName());
                    logger.error("子集部门name----------------------------" + department.getName());
                }

                //2018年7月15日 13:25:34 导入 20180713人员名单.xls
//                user.setAlias(secondeLevelDepartmentName);
            }
            departmentList.add(departmentService.findByDepartmentName(departmentName));
            if (!CollectionUtils.isEmpty(departmentList)) {
                user.setDepartments(departmentList);
            }
            user.setMaxAnalysisArea(0.0);
            role = roleService.findRoleByRoleAlias(roleName);
            roleList.add(role);
            roleList.add(roleService.findRoleByRoleAlias("一般用户"));
            user.setRoles(roleList);
            if (!"".equals(user.getAlias()) && user.getAlias() != null && !"别名".equals(user.getAlias()) && CollectionUtils.isNotEmpty(user.getRoles())) {
                userList.add(user);
            }
            /*userService.saveUser(user);*/
        }
        /*userList.forEach((e) ->{
            System.out.println(e);
        });*/
        /*userList = userList.stream().filter((User user) -> !user.getAlias().equals("") && user.getAlias()!=null).collect(Collectors.toList());*/
        long packageData = System.currentTimeMillis();
        double packageTime = (packageData - endRead);
        System.out.println("封装数据-------------------------" + packageTime);
        List<User> resList = new ArrayList<>();
        for (User u : userList) {
            if (!"登录名".equals(u.getUsername())) {
                resList.add(u);
            }
        }
        userService.saveUsers(resList);
        long saveData = System.currentTimeMillis();
        double saveDataTime = (saveData - packageData);
        System.out.println("封装数据-------------------------" + saveDataTime);
        /*ExcelUtil.writeExcel(result, "C:\\Users\\Administrator\\Desktop\\大数据平台\\人员信息\\厅人员名单_export.xls");*/
    }
}