package cn.gtmap.gtcc.account.web.rest;

import cn.gtmap.gtcc.account.service.UserService;
import cn.gtmap.gtcc.domain.sec.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 开放毋须权限即可访问接口
 *
 * @author shanhuashuiqing11@163.com
 * @version 1.0 2018/9/25
 */
@RestController
@RequestMapping("/noOauth")
public class NoAuthorityController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @GetMapping("/loginName")
    public User getUserByName(@RequestParam("loginName") String loginName) {
        return userService.getUserByUsername(loginName);
    }
}
