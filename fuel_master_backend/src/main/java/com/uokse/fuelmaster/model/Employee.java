package com.uokse.fuelmaster.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;


@Entity
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String phone;

    @Column(nullable = false,unique = true)
    private String nic;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @ManyToOne
    @JoinColumn(name = "fuel_station_id", nullable = false)
    private FuelStation fuelStation;

    @Column(updatable = false)
    private String createdAt;

    private String updatedAt;

    public Employee(Long id, String name, String phone, String nic, String password, FuelStation fuelStation, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.nic = nic;
        this.password = password;
        this.fuelStation = fuelStation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FuelStation getFuelStation() {
        return fuelStation;
    }

    public void setFuelStation(FuelStation fuelStation) {
        this.fuelStation = fuelStation;
    }

    public HashMap getCommonData(){
        return new HashMap(){{
            put("id", id);
            put("name", name);
            put("phone", phone);
            put("nic", nic);
            put("fuelStation", fuelStation.getLocation());
        }};
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", nic='" + nic + '\'' +
                ", password='" + password + '\'' +
                ", fuelStation=" + fuelStation +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
