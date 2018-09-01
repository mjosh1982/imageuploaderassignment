package com.upgrad.ImageHoster.model;


import javax.persistence.*;

//@Entity
//@Table(name = "comments")
public class Comment {
    // These annotations auto-increments the id column for us whenever
    // a new Image object is stored into the database
    //@Id
    //@Column(columnDefinition = "serial")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    private Image image;

}
