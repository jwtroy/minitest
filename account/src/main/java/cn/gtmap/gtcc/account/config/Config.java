package cn.gtmap.gtcc.account.config;

import cn.gtmap.gtcc.domain.resource.dto.resource.DepartmentViewBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zgl on 2018-06-26.
 */
@Configuration
public class Config {
    @Bean
    public DepartmentViewBuilder departmentViewBuilder() {
        return new DepartmentViewBuilder();
    }
}
