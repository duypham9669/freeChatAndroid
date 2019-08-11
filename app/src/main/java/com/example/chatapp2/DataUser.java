package com.example.chatapp2;

public class DataUser {
    public String email, name;
    public DataUser(String email, String name){
        this.email=email;
        this.name=name;
    }
    public DataUser(){

    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
