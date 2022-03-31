package com.example.bcrypt.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class userModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String username;
    String password;

    public String getPassword() {
        return password;
    }

    protected userModel()
    {

    }

    public Long getId() {
        return id;
    }

    public userModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
