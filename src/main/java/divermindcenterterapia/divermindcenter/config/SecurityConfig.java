package divermindcenterterapia.divermindcenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración de seguridad para la aplicación.
 * Define políticas de acceso, deshabilita CSRF para APIs REST
 * y configura el encriptador de contraseñas.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configura las reglas de seguridad HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Deshabilita CSRF (necesario para APIs REST)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users",          // Permite acceso público a la paginación
                                "/api/users/**",       // Permite acceso público a otros endpoints
                                "/v3/api-docs/**",
                                "/swagger-ui/**"
                        ).permitAll()
                        .anyRequest().authenticated()  // Todas las demás rutas requieren autenticación
                );
        return http.build();
    }

    /**
     * Provee el encriptador de contraseñas (BCrypt).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}