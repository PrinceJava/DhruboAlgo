package com.project2.dhrubosalgorithms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

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

    public Role() {}

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;

    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}
}
