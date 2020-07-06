package com.example.robotics;

public class person {
    public String fullname,email,number;
    public person(){
    }

    public person(String fullname, String email,String number) {
        this.fullname = fullname;
        this.email = email;
        this.number=number;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
