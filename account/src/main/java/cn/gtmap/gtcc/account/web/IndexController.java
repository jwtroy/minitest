package cn.gtmap.gtcc.account.web;

import cn.gtmap.gtcc.account.config.RegionConfig;
import cn.gtmap.gtcc.clients.resource.DictClient;
import cn.gtmap.gtcc.domain.resource.metadata.Dict;
import cn.gtmap.gtcc.properties.AppBrand;
import cn.gtmap.gtcc.utils.Utils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * .IndexController
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/29 18:42
 */
@Controller
public class IndexController {

    @Autowired
    AppBrand appBrand;
    @Autowired
    private RegionConfig regionConfig;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    DictClient dictClient;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 正常走登录页面的登录
     *
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String login(Model model) {
        String regioncode = "";
        String titleEn = "portalEnName";
        String titleZn = "portalZnName";
        String copyright = "copyright";
        String logoEnable = "logoEnable";
        try {
            regioncode = dictClient.findValueByKey("regioncodeconfig").getValue();
            titleEn = dictClient.findDictByType(titleEn).getValue();
            titleZn = dictClient.findDictByType(titleZn).getValue();
            copyright = dictClient.findDictByType(copyright).getValue();
            logoEnable = dictClient.findDictByType(logoEnable).getValue();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        model.addAttribute("titleEn", titleEn);
        model.addAttribute("titleZn", titleZn);
        model.addAttribute("copyright", copyright);
        model.addAttribute("logoEnable", logoEnable);
        model.addAttribute("regionCode", regionConfig.getRegioncode());
        if(regionConfig.getStylePage() == null){
            model.addAttribute("stylePage","st");
        }else {
            model.addAttribute("stylePage", regionConfig.getStylePage());
        }
        model.addAttribute(" ", appBrand);
        return "/login";
    }

    /**
     * 模拟登录    在ftl自动提交form登录
     *
     * @param model
     * @param request
     * @param loginName
     * @param map
     * @return
     */
    @GetMapping("/loginOtherSys")
    public String loginOtherSys(Model model, HttpServletRequest request, @RequestParam(name = "loginName", required = true) String loginName, @RequestParam(name = "map", required = false) String map) {
        String regioncode = "";
        try {
            regioncode = dictClient.findValueByKey("regioncodeconfig").getValue();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        model.addAttribute("regionCode", regioncode);
        model.addAttribute(" ", appBrand);
        model.addAttribute("loginName", loginName);
        model.addAttribute("password", regionConfig.getPassword());
        request.getSession().setAttribute("map", map);
        request.getSession().setAttribute("loginName", loginName);
        return "/loginOtherSys";
    }

    /**
     * 自动跳转到对应首页
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @GetMapping("/")
    public void goHomePage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Object map = request.getSession().getAttribute("map");
        Object loginName = request.getSession().getAttribute("loginName");
        if (map == null && regionConfig.getResourceUi() != null && !"".equals(regionConfig.getResourceUi())) {
            String ui = getIpPort(regionConfig.getResourceUi());
            response.sendRedirect(ui + "/dc/display#/portal");
        } else if (map != null) {
            request.getSession().removeAttribute("map");
            String mapui = getIpPort(regionConfig.getMapUi());
            response.sendRedirect(mapui + "/map/index?loginName=" + loginName);
        }
    }

    /**
     * 获取ui 的微服务地址
     *
     * @param appName
     * @return
     */
    private String getIpPort(String appName) {
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(appName);
        List<Dict> dictList = new ArrayList<>();
        try {
            dictList = dictClient.findDefaultDicts(dictClient.findValueByKey("serverMapping").getId());
        } catch (Exception e) {
            logger.error("请确认serverMapping字典项是否配置正确");
        }
        if (serviceInstanceList != null && serviceInstanceList.size() > 0) {
            URI uri = serviceInstanceList.get(0).getUri();
            return mappingUrl(uri.toString(), dictList);
        } else {
            return "";
        }
    }

    /**
     * url 转换 从内网真实地址换成防火墙映射地址。
     *
     * @param mappingUrl
     * @param dictList
     * @return
     */
    private static String mappingUrl(String mappingUrl, List<Dict> dictList) {
        List<String> urlList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dictList)) {
            for (Dict dict : dictList) {
                urlList.add(dict.getValue());
            }
        }
        return Utils.urlMapping(mappingUrl, urlList);
    }
}
