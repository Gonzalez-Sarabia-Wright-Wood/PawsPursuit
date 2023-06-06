package com.codeup.pawspursuit.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "species")
    private String species;

    @Column(name = "breed")
    private String breed;

    @Column(name = "size")
    private String size;

    @Column(name = "description")
    private String description;

    @Column(name = "photo")
    private String photo;

    @Column(name = "is_lost")
    private boolean isLost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    public Pet() {
    }

    public Pet(String name, String species, String breed, String size, String description, User user) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.size = size;
        this.description = description;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }
}
