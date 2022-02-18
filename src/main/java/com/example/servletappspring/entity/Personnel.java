package com.example.servletappspring.entity;

import javax.persistence.*;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "personnel_id"))
public class Personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "PERSONNEL_NAME")
    private String name;
    @Column(name = "PERSONNEL_PASSWORD")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "PERSONNEL_ACCESS_LEVEL")
    private AccessLevel accessLevel;

    public enum AccessLevel {
        ADMIN,
        MODERATOR,
        USER,
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}
