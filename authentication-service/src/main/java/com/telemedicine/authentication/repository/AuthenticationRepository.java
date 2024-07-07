package com.telemedicine.authentication.repository;

import java.util.Optional;
import com.telemedicine.authentication.entity.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, Integer> {
    Optional<AuthenticationEntity> findByEmail(String email);
    Optional<AuthenticationEntity> findByUserName(String userName);
    boolean existsByUserId(String userId);
    Optional<AuthenticationEntity> findByUserId(String userId);
}

