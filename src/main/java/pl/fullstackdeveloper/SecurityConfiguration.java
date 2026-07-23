package pl.fullstackdeveloper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.fullstackdeveloper.security.TimeBasedAuthorizationManager;
import pl.fullstackdeveloper.security.apikey.ApiKeyAuthentication;
import pl.fullstackdeveloper.security.jwt.JwtAuthentication;
import pl.fullstackdeveloper.security.GenericAuthenticationFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

//@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

    /*AuthenticationManager authenticationManager; // Interfejs/kontrakt dla procesu uwierzytelnienia użytkownika
        ProviderManager providerManager; // Podstawowa implementacja AuthenticationManager, deleguje proces uwierzytelnienia do jednego z obiektów AuthenticationProvider
            AuthenticationProvider authenticationProvider; // Interfejs/kontrakt dla obiektów realizujących uwierzytelnianie z wykorzystaniem konkretnego mechanizmu/implementacji
                DaoAuthenticationProvider daoAuthenticationProvider; // Jedna z implementacji AuthenticationProvider, ładuje dane o użytkowniku wykorzystując UserDetailsService i porównuje je z tymi podanymi w czasie logowani
                    UserDetailsService userDetailsService; // Interfejs/kontrakt usługi ładującej dane dotyczące użytkownika

    UserDetailsManager userDetailsManager; Interfejs/kontrakt pochodny UserDetailsService, pozwalający na zarządzanie użytkownikami
        InMemoryUserDetailsManager inMemoryUserDetailsManager; // Jedna z implementacji UsersDetailsManager, przechowuje informacje w pamięci

    PasswordEncoder passwordEncoder; //Interfejs/kontrakt pozwalający na hashowanie i porównywanie haseł
        BCryptPasswordEncoder bCryptPasswordEncoder; //Jedna z implementacji PasswordEncoder

    SecurityContextHolder securityContextHolder; // Przechowuje/udostępnia SecurityContext
        SecurityContext securityContext; // Kontener przechowujący Authentication
            Authentication authentication; // Reprezentuje dane uwierzytelniające jak i uwierzytelnionego użytkownika/system
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken; // Jedna z implementacji Authentication, zawiera login i hasło jako credentials
                    UserDetails userDetails; // Interfejs/kontrakt opisujący użytkownika
                    GrantedAuthority grantedAuthority; // Interfejs/kontrakt opisujący role/uprawnienia
                        SimpleGrantedAuthority simpleGrantedAuthority; // Jedna z implementacji SimpleGrantedAuthority

    AuthorizationManager authorizationManager; // Interfejs/kontrakt dla procesu autoryzacji
        AuthoritiesAuthorizationManager authoritiesAuthorizationManager; // Jedna z implementacji AuthorizationManager (role)*/


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*public UserDetails defaultUser() {
        return User.withUsername("jan")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN")
                .build();
    }*/

   /* @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        *//*return username -> {
            if (!username.equalsIgnoreCase("jan")) {
                throw new UsernameNotFoundException("User not found");
            }
            return defaultUser();
        };*//*

        // return new InMemoryUserDetailsManager(defaultUser());

        var manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("select username, password, enabled from users where username = ?");
        manager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username = ?");
        return manager;
    }*/

    @Bean
    public CorsConfiguration corsConfiguration() {
        var corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("https://training.pl"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);
        return corsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationConfiguration authenticationConfiguration) {
        var jwtAuthenticationFilter = new GenericAuthenticationFilter(authenticationConfiguration, "bearer", JwtAuthentication::new);
        var apiKeyAuthenticationFilter = new GenericAuthenticationFilter(authenticationConfiguration, "API_KEY", ApiKeyAuthentication::new);
        return httpSecurity
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(apiKeyAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(config -> config.ignoringRequestMatchers("/api/**"))
                .cors(config -> config.configurationSource(_ -> corsConfiguration()))
                .httpBasic(withDefaults())
                /*.httpBasic(config -> config
                        .realmName("Training")
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )*/
                //.formLogin(withDefaults())
                .formLogin(config -> config
                        .loginPage("/login.html")
                        .defaultSuccessUrl("/index.html")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        //.failureHandler((request, response, exception) -> {})
                        //.successHandler((request, response, authentication) -> {})
                )
                .authorizeHttpRequests(config -> config
                        .requestMatchers("/login.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/**").authenticated()
                        //.anyRequest().hasRole("ADMIN")
                        .anyRequest().access(new TimeBasedAuthorizationManager())
                )
                .logout(config -> config
                        .logoutRequestMatcher(requestMatcherBuilder().matcher("/logout.html"))
                        .logoutSuccessUrl("/login.html")
                        .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public PathPatternRequestMatcher.Builder requestMatcherBuilder() {
        return PathPatternRequestMatcher.withDefaults();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login.html").setViewName("login-form");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/").setViewName("index");
    }

}