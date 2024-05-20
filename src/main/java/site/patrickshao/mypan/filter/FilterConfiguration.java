package site.patrickshao.mypan.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/18
 */
@Configuration
public class FilterConfiguration {
    @Bean
    FilterRegistrationBean<AccessRateFilter> accessRateFilter() {
        FilterRegistrationBean<AccessRateFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AccessRateFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
