package com.project.autoshop.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Job {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Boolean complete;
    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Customer customer;
    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Insurance insurance;
    @OneToOne(mappedBy = "job", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Address address;
    @OneToOne(mappedBy = "job", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Vehicle vehicle;
    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Labor> labors;
    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Part> parts;
    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Image> images;
}
