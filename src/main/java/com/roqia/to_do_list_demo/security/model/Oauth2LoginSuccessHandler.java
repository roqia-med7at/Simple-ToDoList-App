package com.roqia.to_do_list_demo.security.model;

import com.roqia.to_do_list_demo.dto.TokenDto;
import com.roqia.to_do_list_demo.mapper.TokenMapper;
import com.roqia.to_do_list_demo.security.service.JwtService;
import com.roqia.to_do_list_demo.security.service.Oauth2UserService;
import com.roqia.to_do_list_demo.security.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final Oauth2UserService oauth2UserService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenMapper tokenMapper;
    public Oauth2LoginSuccessHandler(JwtService jwtService, Oauth2UserService oauth2UserService) {
        this.jwtService = jwtService;
        this.oauth2UserService = oauth2UserService;

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();

        CustomOauth2User oAuth2User;
        if (principal instanceof CustomOauth2User) {
            // لو فعلاً راجع CustomOauth2User
            oAuth2User = (CustomOauth2User) principal;
        } else {
            // لو جوجل رجع DefaultOidcUser
            String providerName = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
            oAuth2User = new CustomOauth2User(principal, providerName);
        }
        User user = oauth2UserService.findOrCreateUser(oAuth2User);
       int user_id= user.getId();;
       String token = jwtService.generate_token(user_id);
       String refresh_token = jwtService.generate_refresh_token(user_id);
        int token_userId = Integer.parseInt(jwtService.extract_userId(refresh_token));
        tokenService.addToken(refresh_token,token_userId);
        response.setContentType("application/json");
        response.getWriter().write("{\"token\":\""+token+"\"}"+ "\n{\"refresh-token\":\"\""+refresh_token+"\"}");
        response.getWriter().flush();
    }
}
