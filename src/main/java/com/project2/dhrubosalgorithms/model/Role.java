package com.project2.dhrubosalgorithms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Role() {}

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
/*
    public User getUser() {return user;}

    public void setUser(User user) {
        this.user = user;
    }*/

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}
}
