package com.example.backend.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class UserEntityDetails implements UserDetails {
    private final UserEntity entity;

    public UserEntityDetails(UserEntity entity) {
        this.entity = entity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (entity.getRole() == UserEntity.Role.MANAGER) {
            return Stream.of("ROLE_MANAGER")
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }

        return Stream.of("ROLE_WORKER")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return entity.getPassword();
    }

    @Override
    public String getUsername() {
        return null;
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