package com.project.autoshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String make;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private Integer year;
    private String vin;
    @OneToOne(optional = false)
    @JsonBackReference
    private Job job;
}
