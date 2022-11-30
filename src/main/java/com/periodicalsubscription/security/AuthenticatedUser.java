package com.periodicalsubscription.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

/**
 * class to be returned from UserDetailService for Spring Security
 */
public class AuthenticatedUser extends User {
    private final Long id;
    private final String firstName;


    public AuthenticatedUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id, String firstName) {
        super(username, password, authorities);
        this.id = id;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthenticatedUser that = (AuthenticatedUser) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, firstName);
    }
}
