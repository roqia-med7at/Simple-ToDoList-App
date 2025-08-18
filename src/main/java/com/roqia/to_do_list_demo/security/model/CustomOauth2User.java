package com.roqia.to_do_list_demo.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
public class CustomOauth2User implements OAuth2User {
private final OAuth2User oAuth2User;
private final String provider_name;

    public CustomOauth2User(OAuth2User oAuth2User, String providerName) {
        this.oAuth2User = oAuth2User;
        this.provider_name = providerName;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }
    public String getProvider_id(){
        if("google".equals(provider_name)){
            return oAuth2User.getAttribute("sub");
        }
        if("github".equals(provider_name)){
            return String.valueOf( oAuth2User.getAttribute("id"));
        }
        else
            return  oAuth2User.getName();
    }
    public String getProvider_name(){
        return  provider_name;
    }
    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }
    public String getUserName(){
        return oAuth2User.getAttribute("name");
    }
}
