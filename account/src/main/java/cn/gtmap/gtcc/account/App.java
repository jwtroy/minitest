package cn.gtmap.gtcc.account;

import cn.gtmap.gtcc.account.config.RegionConfig;
import cn.gtmap.gtcc.properties.AppBrand;
import cn.gtmap.gtcc.starter.gcas.GTMapCloudApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

/**
 * .App
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/8/14 15:00
 */

@GTMapCloudApplication(feignClientsPackages = {"cn.gtmap.gtcc.clients.resource"})
@EntityScan(basePackages = {"cn.gtmap.gtcc.domain.sec"})
@EnableCaching
@EnableConfigurationProperties({AppBrand.class,RegionConfig.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
