package com.uokse.fuelmaster.Repo;

import com.uokse.fuelmaster.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepo extends JpaRepository<User,Long> {
 User findByPhone(String phone);
 Optional<User> findOneByPhoneAndPassword(String phone, String password);

}
