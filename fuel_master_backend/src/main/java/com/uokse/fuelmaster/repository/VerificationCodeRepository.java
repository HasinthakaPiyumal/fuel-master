package com.uokse.fuelmaster.repository;
import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByCodeAndUser(String code, User obj);
}
