package com.project.autoshop.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Work {
    @Id
    @GeneratedValue
    private Integer id;
    @Size(min = 1, max = 15, message = "make has to be between {min} and {max} length")
    @NotNull(message = "make cannot be null")
    private String make;
    @Size(min = 1, max = 10, message = "modal has to be between {min} and {max} length")
    @NotNull(message = "model cannot be null")
    private String model;
    @Min(value = 1950, message = "year can't be less then 1950")
    @Max(value = 2050, message = "year can't be greater than 2050")
    @NotNull(message = "year cannot be null")
    private Integer year;
    @Size(min = 5, max = 100, message = "description has to be between {min} and {max} length")
    @NotNull(message = "description cannot be null")
    private String description;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Client client;


    public Work(String make, String model, Integer year, String description, Client client) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.description = description;
        this.client = client;
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
