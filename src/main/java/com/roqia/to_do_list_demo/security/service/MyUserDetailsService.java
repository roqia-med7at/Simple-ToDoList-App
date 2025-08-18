package com.roqia.to_do_list_demo.security.service;

import com.roqia.to_do_list_demo.exceptionHandlers.RecordNotFoundException;
import com.roqia.to_do_list_demo.security.repo.UserRepo;
import com.roqia.to_do_list_demo.security.model.User;
import com.roqia.to_do_list_demo.security.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).get();
        if (user==null){
            throw new RecordNotFoundException("User with this email is not found");
        }
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }
}
