package com.uokse.fuelmaster.model;

import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "verification_code")
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isVerified;

    @Column(nullable = false)
    private Long createdDate;

    public VerificationCode() {
    }

    public VerificationCode(Long id, User user, String code, boolean isVerified, Long createdDate) {
        this.id = id;
        this.user = user;
        this.code = code;
        this.isVerified = isVerified;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "VerificationCode{" +
                "id=" + id +
                ", user=" + user +
                ", code=" + code +
                ", isVerified=" + isVerified +
                ", createdDate=" + createdDate +
                '}';
    }
}