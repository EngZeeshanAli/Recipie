package com.bootcamp.recipie;

public class User {
    String email,pass,uid;

    public User() {
    }

    public User(String email, String pass, String uid) {
        this.email = email;
        this.pass = pass;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
