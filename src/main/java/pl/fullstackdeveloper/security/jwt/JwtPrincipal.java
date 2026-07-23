package pl.fullstackdeveloper.security.jwt;

import java.util.Set;

public record JwtPrincipal(String username, Set<String> roles) {
}
