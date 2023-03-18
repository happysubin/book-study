package study.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.PostConstruct;
import java.util.List;


@EnableWebSecurity
public class ProjectConfig {


    /**
     * 커맨드 라인 옵션 인수를 이용해 실습을 진행함.
     */

//    @Value("${client_id}")
//    private String clientId;
//
//    @Value("${secret}")
//    private String secretKey;
//
//
//    private ClientRegistration clientRegistration(){
//        return CommonOAuth2Provider.GITHUB
//                .getBuilder("github")
//                .clientId(clientId)
//                .clientSecret(secretKey)
//                .build();
//    }
//
//    @Bean
//    ClientRegistrationRepository clientRegistrationRepository(){
//        return new InMemoryClientRegistrationRepository(clientRegistration());
//    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /**
         * oauth2Login 메서드를 사용해 새로운 인증 필터를 필터체인에 추가한다.
         */
//        http.oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
//            httpSecurityOAuth2LoginConfigurer.clientRegistrationRepository(clientRegistrationRepository());
//        });

        http.authorizeRequests(authRequest ->
                authRequest
                        .anyRequest().authenticated());


        http.oauth2Login(Customizer.withDefaults());
        return http.build();
    }


}
