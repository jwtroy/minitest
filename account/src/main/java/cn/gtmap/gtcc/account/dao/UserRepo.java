package cn.gtmap.gtcc.account.dao;

import cn.gtmap.gtcc.domain.sec.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * .UserRepo
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/21 14:44
 */
public interface UserRepo<U extends User> extends PagingAndSortingRepository<U, String> {

    Optional<U> findByUsername(String username);

    Page<U> findByRolesId(String roleId, Pageable pageable);

    List<U> findByRolesId(String roleId);

    /**
     * find by str
     *
     * @param str
     * @return
     */
    List<U> findByUsernameContaining(String str);

    Page<U> findByDepartmentsId(String departmentId, Pageable pageable);

}
