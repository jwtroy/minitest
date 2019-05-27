package cn.gtmap.gtcc.account.dao;

import cn.gtmap.gtcc.domain.sec.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * .RoleRepo
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/22 16:21
 */
public interface RoleRepo<R extends Role> extends PagingAndSortingRepository<R, String> {
    /**
     * findByName
     *
     * @param name
     * @return
     */
    Optional<R> findByName(String name);

    /**
     * 删除ref
     *
     * @param id role_id
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "DELETE  FROM gt_user_role_ref WHERE role_id = ?1", nativeQuery = true)
    void deleteRef(String id);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "DELETE  FROM gt_user_role_ref WHERE role_id = ?1 and user_id=?2", nativeQuery = true)
    void deleteRefByUser(String roleId, String userId);

    /**
     * deleteUserRef
     *
     * @param id user_id
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "DELETE  FROM gt_user_role_ref WHERE user_id = ?1", nativeQuery = true)
    void deleteUserRef(String id);

    /**
     * deleteOperaRef
     *
     * @param id role_id
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "DELETE  FROM gt_role_opera_ref WHERE role_id = ?1", nativeQuery = true)
    void deleteOperaRef(String id);

    /**
     * deleteOperaRefByOpera
     *
     * @param id operation_id
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "DELETE  FROM gt_role_opera_ref WHERE operation_id = ?1", nativeQuery = true)
    void deleteOperaRefByOpera(String id);

    @Query(value = " SELECT * FROM gt_role WHERE  alias like %?1%", nativeQuery = true)
    List<Role> findRoleByAliasLike(String alias);

    List<Role>  findRoleByIdIn(List<String> ids);

    @Query(value = " SELECT * FROM gt_role WHERE  alias = ?1", nativeQuery = true)
    Role findRoleByRoleAlias(String alias);

    Role findRoleByName(String name);
}
