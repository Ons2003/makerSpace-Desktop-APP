package com.example.testing;

public class Account {
    public enum UserRole {
        Student,
        Professor,
        SuperUser
    }

    public String name;
    public String SID = "";
    public UserRole role;

    public Account(String name, String universityID, UserRole role){
        this.name = name;
        SID = universityID;
        this.role = role;
    }
}
