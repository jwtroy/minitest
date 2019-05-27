package cn.gtmap.gtcc.account.service.impl;

import cn.gtmap.gtcc.account.service.ClientService;
import cn.gtmap.gtcc.domain.sec.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * .ClientServiceImplTest
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/25 11:21
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ClientServiceImplTest {

    @Autowired
    ClientService clientService;

    @Test
    public void getClient() throws Exception {
    }

    @Test
    public void saveClient() throws Exception {
        Client client = new Client().setClientId("demo1").setClientSecret("demo1")
                .setAuthorizedGrantTypes("client_credentials,authorization_code,password,refresh_token")
                .setAutoApproveScopes("*")
                .setScopes("read,write,delete");
        clientService.saveClient(client);
    }

}