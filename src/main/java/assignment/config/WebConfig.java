package assignment.config;

import assignment.security.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hbilici on 2017-03-01.
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean customFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("customFilter");
        CustomFilter customFilter = new CustomFilter();
        registrationBean.setFilter(customFilter);
//        registrationBean.setOrder(1);
        return registrationBean;
    }

}
