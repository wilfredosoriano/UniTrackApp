package com.example.unitrack;

public class User {
    public String name, email, address, Uid;
    public int usertype;

    public User () {

    }
    public User(String name, String email, String address, String uid, int usertype) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.Uid = uid;
        this.usertype = usertype;
    }
}
