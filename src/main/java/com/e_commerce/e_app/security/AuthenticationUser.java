package com.e_commerce.e_app.security;

import com.e_commerce.e_app.dto.role.Username;
import com.e_commerce.e_app.exception.NotAuthenticationUserException;
import com.e_commerce.e_app.exception.UnknownAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public final class AuthenticationUser {

    public static final String PREFERRED_USERNAME = "email";

    private AuthenticationUser() {}

    public static Username username(){
        return optionalUsername()
                .orElseThrow(()-> new NotAuthenticationUserException("Not authenticated username"));
    }

    public static Optional<Username> optionalUsername() {
        return authentication()
                .map(AuthenticationUser::readPrincipal)
                .flatMap(Username::of);
    }

    public static String readPrincipal(Authentication authentication) {
        if(authentication.getPrincipal() instanceof UserDetails details) {
            return details.getUsername();
        }
        else if(authentication instanceof JwtAuthenticationToken token) {
            return (String) token.getToken().getClaims().get(PREFERRED_USERNAME);
        }
        else if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser){
            return (String) oidcUser.getAttributes().get(PREFERRED_USERNAME);
        }
        else if (authentication.getPrincipal() instanceof String principal ){
            return principal;
        }
        throw new UnknownAuthenticationException("Not principal found");
    }

    public static Optional<Authentication> authentication() {
        return Optional
                .ofNullable(SecurityContextHolder
                        .getContext()
                        .getAuthentication());
    }

    public static List<String> extractRolesFromToken(Jwt jwt) {
        if(jwt == null) {
            throw new NotAuthenticationUserException("Not authenticated user");
        }
        var resourceAccessObj =  jwt.getClaim("realm_access");
        if(!(resourceAccessObj instanceof Map<?,?> resourceAccess)) return Collections.emptyList();
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) resourceAccess.get("roles");
        if(roles == null) return Collections.emptyList();
        return roles.stream()
                .map(role->role.replace("-","_"))
                .toList();
    }
}
