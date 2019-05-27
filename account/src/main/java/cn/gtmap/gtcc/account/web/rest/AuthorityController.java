package cn.gtmap.gtcc.account.web.rest;

import cn.gtmap.gtcc.account.service.AuthorityService;
import cn.gtmap.gtcc.domain.resource.CommonListBean;
import cn.gtmap.gtcc.domain.sec.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * .AuthorityController
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/30 11:14
 */
@RestController
@RequestMapping("/rest/authority")
public class AuthorityController {

    @Autowired
    AuthorityService authorityService;

    @GetMapping("/all")
    public Page<Authority> getAllAuthorities(Pageable pageable) {
        return authorityService.getAllAuthorities(pageable);
    }

    @GetMapping("/list")
    public Page<Authority> getAuthorities(OAuth2Authentication authentication, Pageable pageable) {
        return authorityService.getAuthorities(getAuthenticationType(authentication), pageable);
    }

    @GetMapping("/{username}")
    public List<String> getUserAuthorities(@PathVariable(name = "username") String username, OAuth2Authentication authentication) {
        return authorityService.getUserSimpleAuthorities(username, getAuthenticationType(authentication));
    }

    @PostMapping("/add")
    public boolean addAuthorities(@RequestBody List<Authority> authorities, OAuth2Authentication authentication) {
        authorities = authorities.parallelStream().map(authority -> authority.setType(getAuthenticationType(authentication))).collect(Collectors.toList());
        authorityService.saveAuthorities(authorities);
        return true;
    }

    @PostMapping("/delete")
    void deleteAuthorities(@RequestBody List<Authority> authorities) {
        authorityService.deleteAuthorities(authorities);
    }

    @DeleteMapping("/revoke")
    public boolean revokeAuthorities(@RequestBody List<Authority> authorities, OAuth2Authentication authentication) {
        authorities = authorities.parallelStream().map(authority -> authority.setType(getAuthenticationType(authentication))).collect(Collectors.toList());
        authorityService.deleteAuthorities(authorities);
        return true;
    }

    @PostMapping("/checkRoleAuthority")
    boolean checkRoleAuthority(@RequestBody CommonListBean commonListBean, @RequestParam(name = "userName") String userName, @RequestParam(name = "alias") String alias,@RequestParam(name = "authority") String authority) {

        return authorityService.checkAuthority2(commonListBean.getRoleList(), commonListBean.getDepartmentList(), userName, alias,authority);
    }

    @PostMapping("/checkRoleAuthority2")
    boolean checkRoleAuthority2(@RequestParam(name = "userName") String userName, @RequestParam(name = "authority") String authority) {
        return authorityService.checkAuthority(userName, authority);
    }

    @GetMapping("/check/authority")
    public boolean checkUserAuthority(@RequestBody Authority authority) {
        return authorityService.checkAuthority(authority.getUsername(), authority.getAuthority());
    }

    @GetMapping("/check/authorities")
    public List<Boolean> checkUserAuthorities(@RequestBody List<Authority> authorities) {
        return authorityService.checkAuthorities(authorities);
    }


    /**
     * find by authority like
     *
     * @param id
     * @param pageable
     * @return
     */
    @GetMapping("/by/authority/like")
    public Page<Authority> getByAuthorityLike(@RequestParam(name = "id") String id, Pageable pageable) {
        return authorityService.getAuthoritiesByAuthorityLike(id, pageable);
    }

    @GetMapping("/query/getAuthorityById")
    Authority getAuthorityById(@RequestParam(name = "id") String id) {
        return authorityService.getAuthorityById(id);
    }

    /**
     * get authentication type
     *
     * @param authentication
     * @return
     */
    private String getAuthenticationType(OAuth2Authentication authentication) {
        try {
            return authentication.getOAuth2Request().getClientId();
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("OAuth2Authentication error [" + e.getLocalizedMessage() + "]");
        }
    }

    /**
     * 根据authority判断是否已经授权
     *
     * @param authority
     * @return
     */
    @GetMapping("/queryByAuthority")
    Authority queryByAuthority(@RequestParam(name = "authority") String authority){
        return authorityService.queryByAuthority(authority);
    }
}
