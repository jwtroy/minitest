package cn.gtmap.gtcc.account.dao;

import cn.gtmap.gtcc.domain.sec.Department;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * .DepartmentRepo
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/22 20:16
 */
public interface DepartmentRepo<D extends Department> extends PagingAndSortingRepository<D, String> {

    Optional<D> findByName(String name);

    Iterable<D> findByParentIsNull();

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "DELETE  FROM gt_user_depart_ref WHERE user_id = ?1", nativeQuery = true)
    void deleteDepartmentRef(String id);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "DELETE  FROM gt_user_depart_ref WHERE department_id = ?1", nativeQuery = true)
    void deleteDepartmentUser(String id);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "DELETE  FROM gt_user_depart_ref WHERE department_id=?1 and user_id = ?2", nativeQuery = true)
    void deleteDepartmentRef(String departmentId, String userId);

    @Query(value = "Select * FROM gt_department WHERE name=?1", nativeQuery = true)
    Department findByDepartmentName(String name);
}
