package com.example.unitrack;

public class UserInformation {
    public String id, address, name, document;

    public UserInformation(String id, String address, String name, String document) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.document = document;
    }


    public UserInformation(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
