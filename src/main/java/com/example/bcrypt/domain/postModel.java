package com.example.bcrypt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@JsonIgnoreProperties({"usermodel"})
@Entity
public class postModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    String textContent;

    public postModel(String textContent) {
        this.textContent = textContent;
    }

    public Long getId() {
        return id;
    }

    @ManyToOne
    userModel usermodel;

}
