package com.roqia.to_do_list_demo.controller;

import com.roqia.to_do_list_demo.dto.UserDto;
import com.roqia.to_do_list_demo.dto.UserProfileDto;
import com.roqia.to_do_list_demo.mapper.TokenMapper;
import com.roqia.to_do_list_demo.mapper.UserProfileMapper;
import com.roqia.to_do_list_demo.security.model.RefreshToken;
import com.roqia.to_do_list_demo.security.model.User;
import com.roqia.to_do_list_demo.security.model.UserPrincipal;
import com.roqia.to_do_list_demo.security.service.JwtService;
import com.roqia.to_do_list_demo.security.service.TokenService;
import com.roqia.to_do_list_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TokenMapper tokenMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto dto) {
        userService.add(dto);
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email ,@RequestParam String password) {
        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authorized = authenticationManager.authenticate(auth);
            UserPrincipal principal = (UserPrincipal) authorized.getPrincipal();
            int user_id = principal.getUserId();
            String userName = principal.getUsername();
            if (authorized.isAuthenticated()) {
                String token = jwtService.generate_token(user_id);
                String refresh_token = jwtService.generate_refresh_token(user_id);
                int token_userId = Integer.parseInt(jwtService.extract_userId(refresh_token));
                tokenService.addToken(refresh_token,token_userId);
                return ResponseEntity.ok("Login successful for user: " + authorized.getName()+"\n"+"Token : "+token+"\nRefresh-Token : "+refresh_token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
            }
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials: " + ex.getMessage());
        }
    }
    @PostMapping("/refresh")
        public ResponseEntity<?> refresh_token(@RequestBody Map<String, String> refresh_token){
        String refresh = refresh_token.get("refresh_token");
        RefreshToken refreshToken = tokenService.getRefreshTokenByToken(refresh);
        if (refreshToken.isRevoked()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token revoked,Login again");
        }
        if(jwtService.token_expired(refresh)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired,Login again");
        }
        else {
            String userId = jwtService.extract_userId(refresh);
            String new_token = jwtService.generate_token(Integer.parseInt(userId));
            return ResponseEntity.ok("Token : "+ new_token+"\nRefresh-Token : "+refresh_token);
        }

        }
        @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String,String> refreshToken){
          tokenService.revokeToken(refreshToken.get("refresh_token"));
          return ResponseEntity.ok("Logout successful");
        }
        @GetMapping("/me")
    public UserProfileDto get_userInfo(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userService.get_user(userPrincipal.getUserId());
        UserProfileDto dto = userProfileMapper.mapToDto(user);
        return dto;


        }
}
