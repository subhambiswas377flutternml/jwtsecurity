package com.security.auth.authsecurity.entity;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "user_table") // table name can't be "user" in PostGre as "user" table in PostGre is proprietary

// @Table(name = "user_table")
// works same as name in @Entity, used for giving custom name to the table

public class UserEntity implements UserDetails { // UserDetails is needed to be implements as this class is internally used by Spring security
    public UserEntity(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    } // This is very important as this determined whether a user is authorized or not

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
