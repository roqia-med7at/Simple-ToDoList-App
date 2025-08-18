package com.roqia.to_do_list_demo.service;

import com.roqia.to_do_list_demo.dto.UserDto;
import com.roqia.to_do_list_demo.dto.UserProfileDto;
import com.roqia.to_do_list_demo.exceptionHandlers.IncorrectPasswordException;
import com.roqia.to_do_list_demo.exceptionHandlers.RecordNotFoundException;
import com.roqia.to_do_list_demo.mapper.UserMapper;
import com.roqia.to_do_list_demo.mapper.UserProfileMapper;
import com.roqia.to_do_list_demo.security.model.User;
import com.roqia.to_do_list_demo.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;

    public User add(UserDto dto) {
    User user = userMapper.mapTouser(dto);
   String pass =passwordEncoder.encode(user.getPassword());
   user.setPassword(pass);
        return userRepo.save(user);
    }
    public UserProfileDto get_user_profile(int user_id){
        User user = userRepo.findById(user_id).get();
        if (user==null){
            throw  new RecordNotFoundException("No such user found with id"+user_id);
        }
        else {
            UserProfileDto userDto= userProfileMapper.mapToDto(user);
            return userDto;
        }
    }
    public User get_user(int user_id){
        User user = userRepo.findById(user_id).get();
        if (user==null){
            throw  new RecordNotFoundException("No such user found with id"+user_id);
        }
        else {
            return user;
        }
    }
    public void update_user_profile(User user,User updated_user){
        user.setName(updated_user.getName());
        user.setEmail(updated_user.getEmail());
        user.setRole(updated_user.getRole());
        userRepo.save(user);

    }
    public void delete_user(User user){
        userRepo.delete(user);

    }
//    private boolean validate_user_password( int user_id,String password){
//        User user = get_user(user_id);
//        return passwordEncoder.matches(password, user.getPassword());
//    }
    public void change_password(int  user_id,String old_pass,String new_pass){
        User user = get_user(user_id);
        boolean flag = passwordEncoder.matches(old_pass, user.getPassword());

        if(flag){
            user.setPassword(passwordEncoder.encode(new_pass));
            userRepo.save(user);
            // مش لازم save لو user managed
        } else {
            throw new IncorrectPasswordException("Wrong password");
        }


    }
}
