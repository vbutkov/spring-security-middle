package ru.selmag.springsecurityapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class MyConfigurer extends AbstractHttpConfigurer<MyConfigurer, HttpSecurity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyConfigurer.class);

    private String realmName = "My realm";

    @Override
    public void init(HttpSecurity builder) throws Exception {
        LOGGER.info("AM in init {}", builder.getSharedObject(AuthenticationManager.class));
        builder.httpBasic(httpBasic -> httpBasic.realmName(this.realmName))
                .authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest.anyRequest().authenticated());
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        LOGGER.info("AM in configure {}", builder.getSharedObject(AuthenticationManager.class));
    }

    public MyConfigurer realmName(String realmName) {
        this.realmName = realmName;
        return this;
    }
}
