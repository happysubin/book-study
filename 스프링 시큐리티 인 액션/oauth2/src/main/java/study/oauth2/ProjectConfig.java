package study.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
public class ProjectConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /**
         * oauth2Login 메서드를 사용해 새로운 인증 필터를 필터체인에 추가한다.
         */
        http.oauth2Login();

        http.authorizeRequests()
                .anyRequest()
                .authenticated();
        return http.build();
    }


}
