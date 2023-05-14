package com.example.myquizapp.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String u_id,name,email;
    private String pass,address;

    public User(String u_id, String name, String email) {
        this.u_id = u_id;
        this.name = name;
        this.email = email;
    }

    public User(String u_id, String name, String email, String pass, String address) {
        this.u_id = u_id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.address = address;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
