package pl.fullstackdeveloper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfiguration corsConfiguration) throws Exception {
        return httpSecurity
                .csrf(config -> config.ignoringRequestMatchers("/api/**"))
                .cors(config -> config.configurationSource(request -> corsConfiguration))
                .authorizeHttpRequests(config -> config
                        .anyRequest().hasRole("ADMIN")
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .logout(withDefaults())
                .build();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        var corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:8080"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);
        return corsConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}