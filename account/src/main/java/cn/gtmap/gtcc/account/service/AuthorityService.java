package cn.gtmap.gtcc.account.service;

import cn.gtmap.gtcc.domain.sec.Authority;
import cn.gtmap.gtcc.domain.sec.Department;
import cn.gtmap.gtcc.domain.sec.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * .AuthorityService
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/29 21:05
 */
public interface AuthorityService {



    /**
     * getAuthorities
     *
     * @param type
     * @param pageable
     * @return
     */
    Page<Authority> getAuthorities(String type, Pageable pageable);

    /**
     * get all authorities
     *
     * @param pageable
     * @return
     */
    Page<Authority> getAllAuthorities(Pageable pageable);

    /**
     * get user's authorities
     *
     * @param username
     * @param type
     * @return
     */
    List<Authority> getUserAuthorities(String username, String type);

    /**
     * get user simple authorities
     *
     * @param username
     * @param type
     * @return
     */
    List<String> getUserSimpleAuthorities(String username, String type);

    /**
     * save authority
     *
     * @param authority
     */
    Authority saveAuthority(Authority authority);

    /**
     * save authorities
     *
     * @param authorities
     */
    Iterable<Authority> saveAuthorities(Iterable<Authority> authorities);

    /**
     * save authorities
     *
     * @param type
     * @param username
     * @param authorities
     * @return
     */
    Iterable<Authority> saveAuthorities(String type, String username, String... authorities);

    /**
     * delete authorities
     *
     * @param type
     * @param username
     * @param authorities
     * @return
     */
    void deleteAuthorities(String type, String username, String... authorities);

    /**
     * delete authority
     *
     * @param authority
     */
    void deleteAuthorities(Authority authority);

    /**
     * delete authorities
     *
     * @param authorities
     */
    void deleteAuthorities(Iterable<Authority> authorities);

    /**
     * delete authority by id
     *
     * @param id
     */
    void deleteAuthorities(String id);


    /**
     * check authority
     *
     * @param userName
     * @param authority
     * @return
     */
    boolean checkAuthority(String userName, String authority);

    boolean checkAuthority2(List<Role> roleList, List<Department> departmentList, String userName, String alias, String authority);

    /**
     * check authorities
     *
     * @param authorities
     * @return
     */
    List<Boolean> checkAuthorities(List<Authority> authorities);


    /**
     * get Authorities By Authority Likes
     *
     * @param authority
     * @param pageable
     * @return
     */
    Page<Authority> getAuthoritiesByAuthorityLike(String authority, Pageable pageable);

    public Authority getAuthorityById(String id);

    public Authority queryByAuthority(String authority);
}
