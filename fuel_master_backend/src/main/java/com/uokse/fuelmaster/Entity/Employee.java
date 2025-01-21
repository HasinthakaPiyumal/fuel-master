package com.uokse.fuelmaster.Entity;

import jakarta.persistence.*;


@Entity

public class Employee {
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
    private String password;

    @ManyToOne
    @JoinColumn(name = "fuel_station_id", nullable = false)
    private fuelStation fuelStation;

    @Column(updatable = false)
    private String createdAt;

    private String updatedAt;

    public Employee(Long id, String name, String phone, String nic, String password, com.uokse.fuelmaster.Entity.fuelStation fuelStation, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.nic = nic;
        this.password = password;
        this.fuelStation = fuelStation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public com.uokse.fuelmaster.Entity.fuelStation getFuelStation() {
        return fuelStation;
    }

    public void setFuelStation(com.uokse.fuelmaster.Entity.fuelStation fuelStation) {
        this.fuelStation = fuelStation;
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
