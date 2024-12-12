package com.example.SpringRedisJWTDocker_demo3.Security;

import com.example.SpringRedisJWTDocker_demo3.Model.Book_Author;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Book_AuthorDetails implements UserDetails {

    private Book_Author bookAuthor;

    public Book_AuthorDetails(Book_Author bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(bookAuthor.getRole().name()));
    }

    @Override
    public String getPassword() {
        return bookAuthor.getPassword();
    }

    @Override
    public String getUsername() {
        return bookAuthor.getUsername();
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

    public Book_Author getBookAuthor() {
        return bookAuthor;
    }
}
