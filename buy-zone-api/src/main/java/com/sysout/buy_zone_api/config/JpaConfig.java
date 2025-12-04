package com.sysout.buy_zone_api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@RequiredArgsConstructor
@Configuration
@EnableJpaAuditing
public class JpaConfig {

//    private final UserService userService;

    @Bean
    public AuditorAware<String> auditorProvider() {
//        return () -> Optional.ofNullable(userService.authenticated())
//                .map(user -> user.getUsername());
        return null;
    }
}
