package cn.gtmap.gtcc.account.service.impl;

import cn.gtmap.gtcc.account.service.AuthorityService;
import cn.gtmap.gtcc.domain.sec.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * .AuthorityServiceImplTest
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/30 11:39
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class AuthorityServiceImplTest {

    Logger logger = LoggerFactory.getLogger(AuthorityServiceImplTest.class);

    @Autowired
    AuthorityService authorityService;

    @Test
    public void getUserAuthorities() throws Exception {
    }

    @Test
    public void saveAuthority() throws Exception {

        Authority authority = new Authority().setUsername("test1").setAuthority("HAVE_FUN").setType("demo1");
        authorityService.saveAuthority(authority);
    }

    @Test
    public void getAuth() throws Exception {
        List as = authorityService.getUserSimpleAuthorities("test1","LOGIN");
        logger.info(as.toString());
    }


    @Test
    public void getByAuthorityLike(){
        authorityService.getAuthoritiesByAuthorityLike("ROLE_ADMIN",null);
    }
}