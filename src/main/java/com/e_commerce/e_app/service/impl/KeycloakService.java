package com.e_commerce.e_app.service.impl;
import com.e_commerce.e_app.exception.NotAuthenticationUserException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KeycloakService {
    public Map<String,Object> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        if(jwt== null ) {
            throw new NotAuthenticationUserException("No keycloak token found");
        }
        return jwt.getClaims();

    }
}
