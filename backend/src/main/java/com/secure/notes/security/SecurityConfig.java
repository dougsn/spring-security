package com.secure.notes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Quando eu declaro um novo filtro de segurança, com o @Bean, ele sobrescreve o filtro padrão do Spring Security

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // Faz com que todas as requisições necessitam de autenticação
        http.authorizeHttpRequests((request) ->
                request
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated());
        //http.formLogin(Customizer.withDefaults());
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // Diz que a autenticação utilizada é a básica do Spring Security
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
