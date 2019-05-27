package cn.gtmap.gtcc.account.dao;

import cn.gtmap.gtcc.domain.sec.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * .AuthorityRepo
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/29 21:02
 */
public interface AuthorityRepo extends PagingAndSortingRepository<Authority, String> {

    List<Authority> findAllByUsernameAndType(String username, String type);

    List<Authority> findAllByUsernameAndAuthority(String username, String authority);

    Page<Authority> findAllByType(String type, Pageable pageable);

    @Modifying
    @Query("delete from Authority a where a.type=?1 and a.username = ?2 and a.authority = ?3")
    void deleteAuthorities(String type, String username, String authority);

    /**
     * find authority
     *
     * @param userName
     * @param authority
     * @return
     */
    List<Authority> findByUsernameAndAuthority(String userName, String authority);


    /**
     * find By Authority Likes
     *
     * @param authorityLike
     * @param pageable
     * @return
     */
    Page<Authority> findByAuthorityLike(String authorityLike, Pageable pageable);

    Authority findByAuthority(String authority);
}
