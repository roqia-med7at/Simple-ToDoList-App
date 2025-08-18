package com.roqia.to_do_list_demo.security.repo;

import com.roqia.to_do_list_demo.security.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<RefreshToken,Integer> {
   public RefreshToken findByToken(String token);
}
