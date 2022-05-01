package com.project.autoshop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private Integer id;
    @Size(min = 1, max = 15, message = "first name must be between {min} and {max} length")
    @NotNull(message = "first name cannot be null")
    private String first;
    @Size(min = 1, max = 15, message = "last name must be between {min} and {max} length")
    @NotNull(message = "last name cannot be null")
    private String last;
    @Email(regexp = "^(.+)@(\\S+)$", message = "email is invalid")
    @Size(min = 1, max = 30, message = "email must be between {min} and {max} length")
    @NotNull(message = "email cannot be null")
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

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
