package com.secure.notes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Quando eu declaro um novo filtro de segurança, com o @Bean, ele sobrescreve o filtro padrão do Spring Security

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // Faz com que todas as requisições necessitam de autenticação
        http.authorizeHttpRequests((request) ->
                request
                        // Permitindo requisições sem estar autenticado.
//                        .requestMatchers("/contact").permitAll()
//                        .requestMatchers("/public/**").permitAll()
                        // O .denyAll() nega todas as requições para o endpoint especificado.
//                        .requestMatchers("/admin/**").denyAll()
                        .anyRequest().authenticated());
        //http.formLogin(Customizer.withDefaults());
        // Desativando o token csrf para as requisições.
        http.csrf(AbstractHttpConfigurer::disable);
        // Diz que a autenticação utilizada é a básica do Spring Security
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // Criando diversos usuários na memória do backend, para poderem ser utilizados na aplicação.

    // O {noop} diz para o Spring Security que a senha não será criptografada, será "salva" como texto simples.

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager manager =
                new JdbcUserDetailsManager(dataSource);
        if (!manager.userExists("user1")) {
            manager.createUser(
                    User.withUsername("user1")
                            .password("{noop}password1")
                            .roles("USER")
                            .build()
            );
        }
        if (!manager.userExists("admin")) {
            manager.createUser(
                    User.withUsername("admin")
                            .password("{noop}adminPass")
                            .roles("ADMIN")
                            .build()
            );
        }
        return manager;
    }

}
