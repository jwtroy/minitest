package cn.gtmap.gtcc.account.dao;

import cn.gtmap.gtcc.domain.sec.Operation;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * .OperationRepo
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/22 19:24
 */
public interface OperationRepo<O extends Operation> extends PagingAndSortingRepository<O, String> {

    Optional<O> findByName(String name);
}
