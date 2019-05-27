package cn.gtmap.gtcc.account.dao;

import cn.gtmap.gtcc.domain.sec.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * .ClientRepo
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/25 10:26
 */
public interface ClientRepo extends PagingAndSortingRepository<Client, String> {

    Optional<Client> findByClientId(String clientId);
}
