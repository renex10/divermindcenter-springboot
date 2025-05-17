package divermindcenterterapia.divermindcenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/register",    // Permite registro de usuarios
                                "/api/parents/**",       // Todos los endpoints de padres
                                "/v3/api-docs/**",       // Documentación OpenAPI
                                "/swagger-ui/**"         // Interfaz Swagger UI
                        ).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}