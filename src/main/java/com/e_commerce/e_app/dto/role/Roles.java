package com.e_commerce.e_app.dto.role;

import com.e_commerce.e_app.security.Role;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public record Roles(Set<Role> roles) {
    public static final Roles EMPTY = new Roles(null);

    public Roles(Set<Role> roles) {
        this.roles = Collections.unmodifiableSet(roles);
    }

    public Stream<Role> stream() {
        return get().stream();
    }

    private Set<Role> get() {
        return roles();
    }
}
