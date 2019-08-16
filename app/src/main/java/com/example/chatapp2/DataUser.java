package com.example.chatapp2;

public class DataUser {
    public String key, email, name;
    public DataUser(String key,String email, String name){
        this.key=key;
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
    public  String getKey(){return key;}
}
