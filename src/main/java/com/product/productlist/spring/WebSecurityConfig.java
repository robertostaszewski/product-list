package com.product.productlist.spring;

import com.product.productlist.repository.ProductListRepository;
import com.product.productlist.repository.SharingEntryRepository;
import com.product.productlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/gs-guide-websocket/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/rest/lists/{listId}/**").access("@securityGuard.isOwnerOfTheList(authentication,#listId)")
                .antMatchers("/rest/lists/{listId}/**").access("@securityGuard.isOwnerOfTheList(authentication,#listId) or @securityGuard.isListSharedWith(authentication,#listId)")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .cors();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new MyUserDetailsService(userRepository);
    }

    @Bean("securityGuard")
    public SecurityGuard securityGuard(ProductListRepository productListRepository, SharingEntryRepository sharingEntryRepository) {
        return new SecurityGuard(productListRepository, sharingEntryRepository);
    }
}
