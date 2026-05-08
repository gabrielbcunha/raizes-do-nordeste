package br.com.gabrielbcunha.sistemaraizesdonordeste.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter filter;

    public SecurityConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csfr -> csfr.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/cadastrar").permitAll()
                        .requestMatchers(HttpMethod.POST,"/funcionarios/atendentes").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.POST,"/funcionarios/cozinheiros").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.POST,"/funcionarios/administrativos").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.POST,"/funcionarios/gerentes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/unidade").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.POST,"/itens").hasAnyRole("ADMIN", "GERENTE", "ADMINISTRATIVO")
                        .requestMatchers(HttpMethod.POST,"/menu").hasAnyRole("ADMIN", "GERENTE", "ADMINISTRATIVO")
                        //...
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}