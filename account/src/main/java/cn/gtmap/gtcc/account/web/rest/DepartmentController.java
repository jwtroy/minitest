package cn.gtmap.gtcc.account.web.rest;

import cn.gtmap.gtcc.account.service.DepartmentService;
import cn.gtmap.gtcc.domain.sec.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * .DepartmentController
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/27 15:32
 */
@RestController
@RequestMapping("/rest/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    private static final String ROOTID = "root";

    @GetMapping("/list")
    public Page<Department> getDepartments(Pageable pageable) {
        return departmentService.getDepartments(pageable);
    }

    @GetMapping("/root")
    public Iterable<Department> getRootDepartments() {
        return departmentService.getRootDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable(name = "id") String id) {
        return departmentService.getDepartment(id);
    }

    @GetMapping("/{id}/children")
    public Iterable<Department> getChildren(@PathVariable(name = "id") String id) {
        return departmentService.getChildren(id);
    }

    @PutMapping("/add/{parentId}")
    public Department addDepartment(@PathVariable(name = "parentId") String parentId, @RequestBody Department department) {
        if (ROOTID.equals(parentId)) {
            parentId = null;
        }
        return departmentService.saveDepartment(department, parentId);
    }

    @PostMapping("/{id}/deleteDepartmentRef")
    public void deleteDepartmentRef(@PathVariable("id") String id) {
        departmentService.deleteDepartmentRef(id);
    }

    @GetMapping("/{id}/deleteDepartment")
    boolean deleteDepartment(@PathVariable(name = "id") String id) {
        return departmentService.deleteDepartmentById(id);
    }

    @GetMapping("department/{departmentId}/{userId}/deleteDepartmentUser")
    boolean deleteDepartmentUser(@PathVariable(name = "departmentId") String departmentId, @PathVariable(name = "userId") String userId) {
        return departmentService.deleteDepartmentUser(departmentId, userId);
    }

    @PutMapping("/update/{parentId}")
    boolean updateDepartment(@PathVariable(name = "parentId") String parentId, @RequestBody Department department){
        departmentService.saveDepartment(department,parentId);
        return true;
    }
}
