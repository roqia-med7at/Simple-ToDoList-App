package com.roqia.to_do_list_demo.security.service;

import com.roqia.to_do_list_demo.dto.TokenDto;
import com.roqia.to_do_list_demo.exceptionHandlers.RecordNotFoundException;
import com.roqia.to_do_list_demo.mapper.TokenMapper;
import com.roqia.to_do_list_demo.security.model.RefreshToken;
import com.roqia.to_do_list_demo.security.repo.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    private TokenRepo tokenRepo;
    @Autowired
    private TokenMapper tokenMapper;
    public void addToken(String token,int user_id){
        RefreshToken refreshTokentoken = new RefreshToken();
        refreshTokentoken.setToken(token);
        refreshTokentoken.setUser_id(user_id);
        tokenRepo.save(refreshTokentoken);
    }
    public void revokeToken(String token){
      RefreshToken token1 = getRefreshTokenByToken(token);
          token1.setRevoked(true);
          tokenRepo.save(token1);
    }
    public RefreshToken getRefreshTokenByToken(String token){
        RefreshToken token1 = tokenRepo.findByToken(token);
        if(token1==null){
            throw new RecordNotFoundException("No such token found");
        }
        else {
            return token1;
        }
    }
}
