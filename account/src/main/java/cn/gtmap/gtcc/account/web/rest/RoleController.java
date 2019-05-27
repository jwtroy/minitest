package cn.gtmap.gtcc.account.web.rest;

import cn.gtmap.gtcc.account.service.RoleService;
import cn.gtmap.gtcc.domain.sec.Operation;
import cn.gtmap.gtcc.domain.sec.Role;
import cn.gtmap.gtcc.domain.sec.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * .RoleController
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/27 15:31
 */
@RestController
@RequestMapping("/rest/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @RequestMapping("/list")
    public Page getRoles(Pageable pageable) {
        return roleService.getRoles(pageable);
    }

    @GetMapping("/roleList")
    List<Role> getRolesList() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable("id") String id) {
        return roleService.getRole(id);
    }

    @GetMapping("/{id}/users")
    public Page<User> getRoleUsers(@PathVariable("id") String id, Pageable pageable) {
        return roleService.getRoleUsers(id, pageable);
    }

    @GetMapping("/queryRoles")
    public Page<Role> queryRoles(Pageable pageable,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "alias") String alias) {
        return roleService.queryRoles(pageable, name, alias);
    }
    @GetMapping("/findRoleListByAlias")
    public List<Role> findRoleByAlias(@RequestParam(name = "alias") String alias) {
        return roleService.findRoleByAlias(alias);
    }

    @GetMapping("/findRoleListByIds")
    public List<Role> findRoleByIds(@RequestParam(name = "ids") List<String> ids) {
        List<Role> roles = new ArrayList<>();
        for(String id :ids ){
            roles.add(roleService.getRole(id));
        }
        return roles;
    }

    @GetMapping("/{id}/usersList")
    public List<User> getRoleUsers(@PathVariable("id") String id) {
        return roleService.getRoleUserList(id);
    }

    @GetMapping("/getUsersByRole")
    public Page<User> getUsersByRole(Pageable pageable, @RequestParam(name = "id") String id) {
        return roleService.getUsersByRole(id, pageable);
    }

    @GetMapping("/{id}/operations")
    public Iterable<Operation> getRoleOperations(@PathVariable("id") String id) {
        return roleService.getRoleOperations(id);
    }

    @PostMapping("/{id}/update")
    public Role updateRole(@PathVariable("id") String id, @RequestBody Role role) {
        role.setId(id);
        return roleService.saveRole(role);
    }

    @PostMapping("/{id}/delete")
    public void deleteRole(@PathVariable("id") String id) {
        roleService.deleteRole(id);
    }

    @PutMapping("/add")
    public Role addRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @PostMapping("/{id}/deleteRoleRef")
    public void deleteRoleRef(@PathVariable("id") String id) {
        roleService.deleteRoleRef(id);
    }

    @GetMapping("/deleteRefByUser")
    public boolean deleteRefByUser(@RequestParam("roleId") String roleId, @RequestParam("userId") String userId) {
        roleService.deleteRefByUser(roleId, userId);
        return true;
    }

    @PostMapping("/{id}/deleteUserRef")
    public void deleteUserRef(@PathVariable("id") String id) {
        roleService.deleteUserRef(id);
    }

    @PostMapping("/{id}/updareRoleOperations")
    void updareRoleOperations(@PathVariable("id") String id, @RequestBody Iterable<String> operationIds) {
        roleService.updateRoleOperations(id, operationIds);
    }

    @GetMapping("/queryRoleByRoleName")
    public Role queryRoleByRoleName(@RequestParam("name") String name) {

        return roleService.findRoleByName(name);
    }
}
