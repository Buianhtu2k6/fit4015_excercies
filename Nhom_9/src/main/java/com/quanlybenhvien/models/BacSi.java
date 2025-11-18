package com.quanlybenhvien.models;

public class BacSi extends Nguoi {
    private static final long serialVersionUID = 1L;

    private String specialty;
    private String phone;

    public BacSi(String id, String fullName, int age, String gender, String address, String specialty, String phone) {
        super(id, fullName, age, gender, address);
        this.specialty = specialty;
        this.phone = phone;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getRoleDescription() {
        return "Bác sĩ " + specialty;
    }
}
