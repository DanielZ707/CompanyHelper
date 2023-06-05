package com.example.backend.models.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final User entity;

    public UserDetails(User entity) {
        this.entity = entity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (entity.getRole() == User.Role.MANAGER) {
            return Stream.of("MANAGER")
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        } else if (entity.getRole() == User.Role.ADMIN) {
            return Stream.of("ADMIN")
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        } else {
            return Stream.of("WORKER")
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public String getPassword() {
        return entity.getPassword();
    }

    @Override
    public String getUsername() {
        return entity.getEmail();
    }

    public String getName() {
        return entity.getName();
    }

    public String getSurName() {
        return entity.getSurname();
    }

    public String getEmail() {
        return entity.getEmail();
    }

    public Long getId() {
        return entity.getIdUser();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}