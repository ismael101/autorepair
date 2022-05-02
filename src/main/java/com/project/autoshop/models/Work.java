package com.project.autoshop.models;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
public class Work {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull(message = "make cannot be null")
    @NotBlank(message = "make cannot be empty")
    private String make;
    @NotNull(message = "model cannot be null")
    @NotBlank(message = "model cannot be null")
    private String model;
    @Min(value = 1950, message = "year cannot be less then 1950")
    @Max(value = 2050, message = "year cannot be greater than 2050")
    @NotNull(message = "year cannot be null")
    private Integer year;
    @NotNull(message = "description cannot be null")
    @NotBlank(message = "description cannot be empty")
    private String description;
    @NotNull(message = "labor cannot be null")
    @Min(value = 0, message = "labor cannot be less then 1")
    private Double labor;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Client client;


    public Work(String make, String model, Integer year, String description, Client client, Double labor) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.description = description;
        this.client = client;
        this.labor = labor;
    }

    public Work() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getLabor() {
        return labor;
    }

    public void setLabor(Double labor) {
        this.labor = labor;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", client=" + client +
                '}';
    }
}
