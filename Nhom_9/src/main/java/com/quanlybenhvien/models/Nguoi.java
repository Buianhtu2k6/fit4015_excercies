package com.quanlybenhvien.models;

import java.io.Serializable;

public abstract class Nguoi implements DinhDanh, Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String fullName;
    private int age;
    private String gender;
    private String address;

    protected Nguoi(String id, String fullName, int age, String gender, String address) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract String getRoleDescription();

    public String basicInfo() {
        return id + " - " + fullName;
    }
}

