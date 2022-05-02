package com.project.autoshop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first has to be more than 1 word")
    private String first;
    @NotNull(message = "last name cannot be null")
    @NotBlank(message = "last has to be more than 1 word")
    private String last;
    @Email(regexp = "^(.+)@(\\S+)$", message = "email is invalid")
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email has to be more than 1 word")
    @Column(unique = true)
    private String email;

    public Client(Integer id, String first, String last, String email) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.email = email;
    }

    public Client(String first, String last, String email) {
        this.first = first;
        this.last = last;
        this.email = email;
    }

    public Client() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
