package com.quanlybenhvien.models;

public class BenhNhan extends Nguoi {
    private static final long serialVersionUID = 1L;

    private String insuranceNumber;

    public BenhNhan(String id, String fullName, int age, String gender, String address, String insuranceNumber) {
        super(id, fullName, age, gender, address);
        this.insuranceNumber = insuranceNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    @Override
    public String getRoleDescription() {
        return "Bệnh nhân";
    }
}
