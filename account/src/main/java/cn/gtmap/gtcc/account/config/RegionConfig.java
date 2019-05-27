package cn.gtmap.gtcc.account.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by zgl on 2018-01-18.
 */
@ConfigurationProperties(prefix = "regioncodeconfig")
public class RegionConfig {

    /**
     * 获取系统行政区
     */

    private String regioncode;

    /**
     * 默认密码
     */
    private String password;


    /**
     * 获取 resourceUi  的发布路径
     */

    private String resourceUi;

    /**
     * 获取 mapUi 的发布路径
     */
    private String mapUi;

    /**
     * 根据lyg,st来确定首页样式
     * st：省厅演示
     * lyg：连云港样式
     */
    private String stylePage;

    public String getStylePage() {
        return stylePage;
    }

    public void setStylePage(String stylePage) {
        this.stylePage = stylePage;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegioncode() {
        return regioncode;
    }

    public void setRegioncode(String regioncode) {
        this.regioncode = regioncode;
    }

    public String getResourceUi() {
        return resourceUi;
    }

    public void setResourceUi(String resourceUi) {
        this.resourceUi = resourceUi;
    }

    public String getMapUi() {
        return mapUi;
    }

    public void setMapUi(String mapUi) {
        this.mapUi = mapUi;
    }

    /**
     * 获取新版展示中心首页
     */

    private String mapPage;

    public String getMapPage() {
        return mapPage;
    }

    public void setMapPage(String mapPage) {
        this.mapPage = mapPage;
    }

}
