package com.sm.expose.global.security.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class UserPrincipal implements OAuth2User, UserDetails {

    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserPrincipal(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrinciple = UserPrincipal.create(user);
        userPrinciple.setAttributes(attributes);
        return userPrinciple;
    }

    @Override
    public String getUsername() {
        return email;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
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
    public String getName() {
        return String.valueOf(id);
    }
}

///*
//https://yelimkim98.tistory.com/48
// */
///**
// * 원하는 형태로 CustomUserDetails를 세팅해준 후 리턴해주면
// * Spring Security에서는 해당 유저의 정보를 조회할 때에는 CustomUserDetails에 세팅된 값으로 조회를 한 후 로직을 처리
// */
//@Getter
//public class UserPrincipal implements OAuth2User, UserDetails {
//
//    private User user;
//    private List<GrantedAuthority> authorities;
//    private Map<String, Object> oauthUserAttributes;
//
//    private UserPrincipal(User user, List<GrantedAuthority> authorities,
//                          Map<String, Object> oauthUserAttributes) {
//        this.user = user;
//        this.authorities = authorities;
//        this.oauthUserAttributes = oauthUserAttributes;
//    }
//
//    /**
//     * OAuth2 로그인시 사용
//     */
//    public static UserPrincipal create(User user, Map<String, Object> oauthUserAttributes) {
//        return new UserPrincipal(user, List.of(() -> "ROLE_USER"), oauthUserAttributes);
//    }
//
//    public static UserPrincipal create(User user) {
//        return new UserPrincipal(user, List.of(() -> "ROLE_USER"), new HashMap<>());
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return String.valueOf(user.getEmail());
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return Collections.unmodifiableMap(oauthUserAttributes);
//    }
//
//    @Override
//    @Nullable
//    @SuppressWarnings("unchecked")
//    public <A> A getAttribute(String name) {
//        return (A) oauthUserAttributes.get(name);
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.unmodifiableList(authorities);
//    }
//
//    @Override
//    public String getName() {
//        return String.valueOf(user.getEmail());
//    }
//
//    public long getId() {
//        return user.getUserId();
//    }
//}