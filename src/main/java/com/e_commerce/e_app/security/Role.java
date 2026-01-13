package com.e_commerce.e_app.security;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Role {
    ADMIN,
    USER,
    UNKNOWN;

    private static final String PREFIX = "ROLE_";
    private static final Map<String,Role> ROLES = buildRoles() ;

    private static Map<String, Role> buildRoles() {
        return Stream.of(values()).collect(Collectors.toUnmodifiableMap(Role::key, Function.identity()));
    }

    private String key() {
        return PREFIX + name();
    }

    public static Role from(String role) {
        return ROLES.getOrDefault(role, UNKNOWN);
    }
}
