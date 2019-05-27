package cn.gtmap.gtcc.account.service;

import cn.gtmap.gtcc.domain.sec.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * .ClientService
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/25 10:27
 */
public interface ClientService extends ClientDetailsService {

    /**
     * get client by id
     *
     * @param id
     * @return
     */
    Client getClient(String id);

    /**
     * get client by client id
     *
     * @param clientId
     * @return
     */
    Client getClientByClientId(String clientId);

    /**
     * get clients
     *
     * @param pageable
     * @return
     */
    Page<Client> getClients(Pageable pageable);

    /**
     * save client
     *
     * @param client
     * @return
     */
    Client saveClient(Client client);

    /**
     * save clients
     *
     * @param clients
     * @return
     */
    Iterable<Client> saveClients(Iterable<Client> clients);

    /**
     * delete client
     *
     * @param client
     */
    void deleteClient(Client client);

    /**
     * delete clinet by id
     *
     * @param id
     */
    void deleteClient(String id);

}
