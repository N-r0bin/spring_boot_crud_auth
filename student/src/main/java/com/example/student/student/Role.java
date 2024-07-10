package com.example.student.student;


import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    TA(Set.of("ta:read", "ta:create", "ta:update", "ta:delete")),
    RA(Set.of("ra:read"));

    private final Set<String> authorities;

    Role(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roleAuthorities = this.authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        roleAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return roleAuthorities;
    }
}

