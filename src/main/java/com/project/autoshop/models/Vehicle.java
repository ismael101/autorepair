package com.project.autoshop.models;

import javax.persistence.*;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String make;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private Integer year;
    @ManyToOne
    private Client client;

    public Vehicle(String make, String model, Integer year, Client client) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.client = client;
    }

    public Vehicle() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
