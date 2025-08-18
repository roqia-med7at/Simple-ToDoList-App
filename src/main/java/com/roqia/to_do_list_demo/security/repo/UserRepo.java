package com.roqia.to_do_list_demo.security.repo;

import com.roqia.to_do_list_demo.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    public User findByName(String username);

   public Optional<User> findByEmail(String email);
}
