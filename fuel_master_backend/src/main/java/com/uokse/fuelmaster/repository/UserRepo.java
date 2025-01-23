package com.uokse.fuelmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.uokse.fuelmaster.model.User;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);

    Optional<User> findOneByPhoneAndPassword(String phone, String password);
}
