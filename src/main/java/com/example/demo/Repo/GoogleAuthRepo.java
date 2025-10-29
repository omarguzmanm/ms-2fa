package com.example.demo.Repo;

import com.example.demo.DTO.UserRegisterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoogleAuthRepo extends JpaRepository<UserRegisterData, Long> {
    Optional<UserRegisterData> findByUsername(String username);
}
