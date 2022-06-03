package com.project.autorepair.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;


//table for job parts
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Part {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String website;
    @Column(nullable = false)
    private Double cost;
    @Column(nullable = false)
    private Boolean ordered;
    @Column(nullable = false)
    private String notes;
    @ManyToOne(optional = false)
    @JsonBackReference
    private Job job;
}
