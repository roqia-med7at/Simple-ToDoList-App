package com.roqia.to_do_list_demo.security.service;

import com.roqia.to_do_list_demo.exceptionHandlers.RecordNotFoundException;
import com.roqia.to_do_list_demo.security.model.CustomOauth2User;
import com.roqia.to_do_list_demo.security.model.Provider;
import com.roqia.to_do_list_demo.security.model.User;
import com.roqia.to_do_list_demo.security.repo.ProviderRepo;
import com.roqia.to_do_list_demo.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Oauth2UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProviderRepo providerRepo;

    public User findOrCreateUser(CustomOauth2User oauth2User){
        String provider_id = oauth2User.getProvider_id();
        String oauth2User_name = oauth2User.getUserName();
        String oauth2User_email = oauth2User.getEmail();
        String provider_name= oauth2User.getProvider_name();

           Provider provider = providerRepo.findByProvider_id(provider_id);
           if (provider==null){
            User user  =new User();
            user.setName(oauth2User_name);
            user.setEmail(oauth2User_email);
            user.setRole("ROLE_USER");
           User saved_user = userRepo.save(user);
             int saved_id = saved_user.getId();
             provider = new Provider();
            provider.setUser_id(saved_id);
            provider.setProvider_id(provider_id);
             provider.setProvider_name(provider_name);
             providerRepo.save(provider);
             return saved_user;
           }
           int user_id = provider.getUser_id();
           return userRepo.findById(user_id).orElseThrow(()-> new RecordNotFoundException("No such provider_id found"));


    }

}
