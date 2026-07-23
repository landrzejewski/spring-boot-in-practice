package pl.fullstackdeveloper.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.function.Function;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class GenericAuthenticationFilter implements Filter /*extends OncePerRequestFilter*/ {

    private static final String PREFIX_SEPARATOR = " ";

    private final AuthenticationConfiguration authenticationConfiguration;
    private final String prefix;
    private final Function<String, Authentication> authenticationSupplier;

    public GenericAuthenticationFilter(AuthenticationConfiguration authenticationConfiguration, String prefix, Function<String, Authentication> authenticationSupplier) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.prefix = prefix;
        this.authenticationSupplier = authenticationSupplier;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(prefix)) {
            var credentials = authorizationHeader.substring(prefix.length() + PREFIX_SEPARATOR.length());
            var authenticationRequest = authenticationSupplier.apply(credentials);
            try {
                var authentication = authenticationConfiguration.getAuthenticationManager()
                        .authenticate(authenticationRequest);
                var context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            } catch (AuthenticationException authenticationException) {
                response.setStatus(SC_UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {
            doFilterInternal(httpRequest, httpResponse, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

}
