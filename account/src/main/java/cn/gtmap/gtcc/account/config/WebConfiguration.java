package cn.gtmap.gtcc.account.config;

import cn.gtmap.gtcc.account.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * .WebConfiguration
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/13 15:49
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    public static final String REDIRECT_URI_PARAM = "redirect_uri";

    public static final String SECRET_CODE = "GTMAP_NELX";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder(SECRET_CODE);
    }


    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    public static class SSOConfiguration extends WebSecurityConfigurerAdapter {

        UserDetailsService userDetailsService;
        PasswordEncoder passwordEncoder;


        public SSOConfiguration(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
            this.userDetailsService = userDetailsService;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {


            http
                    .csrf().disable()
                    //http.authorizeRequests()方法有多个子节点，每个macher按照他们的声明顺序执行
                    .authorizeRequests()
                    //以 "/login/" "/oauth/authorize", "/oauth/confirm_access", "/error", "/matp/**" 所有用户都能访问
                    .antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access", "/error", "/matp/**", "/noOauth/**", "/rest/**", "/loginOtherSys/**").permitAll()
                    //尚未匹配的任何URL都要求用户进行身份验证
                    .anyRequest().authenticated().and()
                    .formLogin()
                    //指定登录界面的 login，也有默认页面，现在是指定的登录界面   .permitAll() 是允许所有用户访问 /login
                    .loginPage("/login")
                    //这个formLogin().permitAll()方法允许所有用户基于表单登录访问/login这个page。
                    .permitAll().and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessHandler(logoutSuccessHandler())
                    .permitAll();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        /**
         * Override this method to configure {@link WebSecurity}. For example, if you wish to
         * ignore certain requests.
         *
         * @param web
         */
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/image/**", "/css/**", "/js/**", "/webjars/**","/lib/**");
        }

        /**
         * logout success handler
         *
         * @return
         */
        SimpleUrlLogoutSuccessHandler logoutSuccessHandler() {
            SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
            logoutSuccessHandler.setTargetUrlParameter(REDIRECT_URI_PARAM);
            return logoutSuccessHandler;
        }




    }

    @Configuration
    @EnableAuthorizationServer
    public static class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

        AuthenticationManager authenticationManager;
        ClientService clientService;

        public OAuth2Configuration(AuthenticationManager authenticationManager, ClientService clientService) {
            this.authenticationManager = authenticationManager;
            this.clientService = clientService;
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientService);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
        }
    }

    @Configuration
    @EnableResourceServer
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    class ResourceServerConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.requestMatchers()
                    .antMatchers("/rest/**").and()
                    .authorizeRequests()
                    .anyRequest().access("#oauth2.hasScope('read')");
        }
    }
}
