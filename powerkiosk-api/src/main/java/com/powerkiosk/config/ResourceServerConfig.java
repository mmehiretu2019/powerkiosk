package com.powerkiosk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().and()//check cors first
                .authorizeRequests()
                .antMatchers("/oauth/authorize**", "/login**",
                        "/offline/**", "/webjars/**", "/index.html**", "/css/**", "/js/**", "/img/**", "/error**")
                .permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .formLogin().permitAll();
    }
}