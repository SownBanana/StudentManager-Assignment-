package com.example.studentmanager;

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private int Sid;
    private String name;
    private String dob;
    private String email;
    private String address;

    public Student() {
    }

    public Student(int id, int sid, String name, String dob, String email, String address) {
        this.id = id;
        Sid = sid;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.address = address;
    }

    public Student(int sid, String name, String dob, String email, String address) {
        Sid = sid;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSid() {
        return Sid;
    }

    public void setSid(int sid) {
        Sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
