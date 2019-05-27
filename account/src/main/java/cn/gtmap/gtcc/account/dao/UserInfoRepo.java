package cn.gtmap.gtcc.account.dao;

import cn.gtmap.gtcc.domain.sec.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * .UserInfoRepo
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/23 12:34
 */
public interface UserInfoRepo<I extends UserInfo> extends PagingAndSortingRepository<I, String> {
}
