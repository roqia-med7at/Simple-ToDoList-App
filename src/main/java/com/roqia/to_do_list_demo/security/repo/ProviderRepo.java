package com.roqia.to_do_list_demo.security.repo;

import com.roqia.to_do_list_demo.security.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepo  extends JpaRepository<Provider,Integer> {
    @Query("SELECT p FROM Provider p WHERE p.provider_id = ?1")
    public Provider findByProvider_id(String provider_id);
}
