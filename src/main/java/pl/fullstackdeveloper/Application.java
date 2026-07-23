package pl.fullstackdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.fullstackdeveloper.security.apikey.ApiKeyAuthenticationProvider;
import pl.fullstackdeveloper.security.jwt.JwtAuthenticationProvider;
import pl.fullstackdeveloper.security.jwt.JwtService;

import java.util.Set;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*@Autowired
    public void providers(AuthenticationManagerBuilder managerBuilder, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder,
                          JwtService jwtService, @Value("${api-keys}") Set<String> apiKeys) {
        var daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        managerBuilder.authenticationProvider(daoAuthenticationProvider);
        managerBuilder.authenticationProvider(new JwtAuthenticationProvider(jwtService));
        managerBuilder.authenticationProvider(new ApiKeyAuthenticationProvider(apiKeys));
    }*/

}
