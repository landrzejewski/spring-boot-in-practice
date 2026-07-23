package pl.fullstackdeveloper.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.function.Function;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIX_SEPARATOR = " ";

    private final AuthenticationConfiguration authenticationConfiguration;
    private final String prefix;
    private final Function<String, Authentication> authenticationSupplier;

    public JwtAuthenticationFilter(AuthenticationConfiguration authenticationConfiguration, String prefix, Function<String, Authentication> authenticationSupplier) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.prefix = prefix;
        this.authenticationSupplier = authenticationSupplier;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(prefix)) {
            var credentials = authorizationHeader.substring(prefix.length() + PREFIX_SEPARATOR.length());
            var jwtAuthentication = authenticationSupplier.apply(credentials);
            try {
                var authentication = authenticationConfiguration.getAuthenticationManager()
                        .authenticate(jwtAuthentication);
                var context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            } catch (AuthenticationException authenticationException) {
                response.setStatus(SC_UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request, response);
    }

}
