package cn.gtmap.gtcc.account.service;

import cn.gtmap.gtcc.domain.sec.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * .DepartmentService
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/22 20:18
 */
public interface DepartmentService<D extends Department> {

    /**
     * get department
     *
     * @param id
     * @return
     */
    D getDepartment(String id);

    /**
     * get depart by name
     *
     * @param name
     * @return
     */
    D getDepartmentByName(String name);

    /**
     * get root department
     *
     * @return
     */
    Iterable<D> getRootDepartments();

    /**
     * get department's children
     *
     * @param parentId
     * @return
     */
    Iterable<D> getChildren(String parentId);

    /**
     * get deprts by pageable
     *
     * @param pageable
     * @return
     */
    Page<D> getDepartments(Pageable pageable);

    /**
     * save
     *
     * @param department
     * @return
     */
    D saveDepartment(D department);


    /**
     * save department
     *
     * @param department
     * @param parentId
     * @return
     */
    D saveDepartment(D department, String parentId);

    void deleteDepartmentRef(String id);

    /**
     * save
     *
     * @param departments
     * @return
     */
    Iterable<D> saveDepartment(Iterable<D> departments);

    /**
     * delete
     *
     * @param id
     */
    void deleteDepartment(String id);

    /**
     * delete
     *
     * @param department
     */
    void deleteDepartment(D department);

    boolean deleteDepartmentById(String id);

    boolean deleteDepartmentUser(String departmentId, String userId);

    Department findByDepartmentName(String name);
}
