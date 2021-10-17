package com.project2.dhrubosalgorithms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "algorithms")
public class Algorithm {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String difficulty;

    @Column
    private String hints;

    @Column
    private String timeComplexity;

    @Column
    private String spaceComplexity;

    @Column
    private String constraints;

    @Column
    private boolean isPublic;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "algorithm")
    private List<Submissions> submissions;



    public Algorithm() {
    }

    public Algorithm(Long id, String name, String description, String difficulty,
                     String hints, String timeComplexity, String spaceComplexity,
                     String constraints, boolean isPublic, User user,
                     Category category, List<Submissions> submissions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.hints = hints;
        this.timeComplexity = timeComplexity;
        this.spaceComplexity = spaceComplexity;
        this.constraints = constraints;
        this.isPublic = isPublic;
        this.user = user;
        this.category = category;
        this.submissions = submissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getHints() {
        return hints;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }

    public String getTimeComplexity() {
        return timeComplexity;
    }

    public void setTimeComplexity(String timeComplexity) {
        this.timeComplexity = timeComplexity;
    }

    public String getSpaceComplexity() {
        return spaceComplexity;
    }

    public void setSpaceComplexity(String spaceComplexity) {
        this.spaceComplexity = spaceComplexity;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Submissions> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submissions> submissions) {
        this.submissions = submissions;
    }
}