package com.shinhan.connector.config.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shinhan.connector.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Getter
public class UserDetailsImpl implements UserDetails {
    private Integer id;
    private String userId;
    @JsonIgnore
    private String password;

    public UserDetailsImpl(Integer id, String userId, String password) {
        this.id = id;
        this.userId = userId;
        this.password = password;
    }

    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encoding(String userId) {
        return passwordEncoder.encode(userId);
    }

    public static UserDetailsImpl buildFromMember(Member member){

        return new UserDetailsImpl(
                member.getNo(),
                member.getId(),
                encoding(member.getName())
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("MEMBER"));
    }

    @Override
    public String getUsername() {
        return userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}