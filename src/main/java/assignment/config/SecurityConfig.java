package assignment.config;

import assignment.security.BasicAuthEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by hbilici on 2017-03-05.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling().authenticationEntryPoint(new BasicAuthEntryPoint())
//                authorizeRequests()
//            .antMatchers("/register").permitAll()
//            .antMatchers("/login").permitAll()
//            .antMatchers("/profile").permitAll()
//            .anyRequest().authenticated()
            .and()
            .csrf().disable();
    }

}
