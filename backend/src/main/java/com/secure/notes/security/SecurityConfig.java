package com.secure.notes.security;

import com.secure.notes.models.AppRole;
import com.secure.notes.models.Role;
import com.secure.notes.models.User;
import com.secure.notes.repositories.RoleRepository;
import com.secure.notes.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDate;

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
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

            if (!userRepository.existsByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com", "{noop}password1");
                user1.setAccountNonLocked(false);
                user1.setAccountNonExpired(true);
                user1.setCredentialsNonExpired(true);
                user1.setEnabled(true);
                user1.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                user1.setAccountExpiryDate(LocalDate.now().plusYears(1));
                user1.setTwoFactorEnabled(false);
                user1.setSignUpMethod("email");
                user1.setRole(userRole);
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin", "admin@example.com", "{noop}adminPass");
                admin.setAccountNonLocked(true);
                admin.setAccountNonExpired(true);
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
                admin.setTwoFactorEnabled(false);
                admin.setSignUpMethod("email");
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }

}
