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

                        //=================================================LOGIN=================================================
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        //=================================================CLIENTES=================================================
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/cadastrar").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/cliente/{id}").hasRole("CLIENTE")
                        //=================================================FUNCIONARIOS=================================================
                        .requestMatchers(HttpMethod.GET, "/funcionarios").hasAnyRole("ADMIN","GERENTE")
                        .requestMatchers(HttpMethod.POST,"/funcionarios/atendentes").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.POST,"/funcionarios/cozinheiros").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.POST,"/funcionarios/administrativos").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.POST,"/funcionarios/gerentes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/funcionarios/{id}").hasAnyRole("ADMIN", "GERENTE")
                        //=================================================UNIDADE=================================================
                        .requestMatchers(HttpMethod.POST,"/unidade").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.GET, "/unidade").hasAnyRole("ADMIN","GERENTE", "ADMINISTRATIVO")
                        //=================================================ITENS=================================================
                        .requestMatchers(HttpMethod.POST,"/itens").hasAnyRole("ADMIN", "GERENTE", "ADMINISTRATIVO")
                        .requestMatchers(HttpMethod.GET, "/itens").hasAnyRole("ADMIN","GERENTE", "ADMINISTRATIVO")
                        //=================================================MENU=================================================
                        .requestMatchers(HttpMethod.POST,"/menu").hasAnyRole("ADMIN", "GERENTE", "ADMINISTRATIVO")
                        .requestMatchers(HttpMethod.GET, "/menu").hasAnyRole("ADMIN","GERENTE", "ADMINISTRATIVO", "ATENDENTE", "CLIENTE")
                        //=================================================ESTOQUE=================================================
                        .requestMatchers(HttpMethod.POST,"/estoque").hasAnyRole("ADMIN", "GERENTE", "ADMINISTRATIVO")
                        .requestMatchers(HttpMethod.GET, "/estoque").hasAnyRole("ADMIN","GERENTE", "ADMINISTRATIVO")
                        //=================================================PROMOCAO=================================================
                        .requestMatchers(HttpMethod.POST,"/unidade/promocao").hasAnyRole("ADMIN", "GERENTE", "ADMINISTRATIVO")
                        //=================================================PEDIDO=================================================
                        .requestMatchers(HttpMethod.POST,"/pedido").hasAnyRole("ADMIN", "GERENTE", "ATENDENTE", "CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/pedido").hasAnyRole("ADMIN","GERENTE", "ATENDENTE")
                        .requestMatchers(HttpMethod.POST,"/pedido/cancelar/**").hasAnyRole("ADMIN", "GERENTE", "ATENDENTE", "CLIENTE")
                        .requestMatchers(HttpMethod.PATCH,"/pedido/status/**").hasAnyRole("ADMIN", "GERENTE", "ATENDENTE", "COZINHEIRO")
                        //=================================================Swagger=================================================
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        //========================================================================================================
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