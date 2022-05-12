package com.project.autoshop.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Job {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double labor;
    @Transient
    private Double total;
    @OneToOne(mappedBy = "job")
    @JsonManagedReference
    private Customer customer;
    @OneToOne(mappedBy = "job")
    @JsonManagedReference
    private Insurance insurance;
    @OneToOne(mappedBy = "job")
    @JsonManagedReference
    private Address address;
    @OneToOne(mappedBy = "job")
    @JsonManagedReference
    private Vehicle vehicle;
    @OneToOne(mappedBy = "job")
    @JsonManagedReference
    private Status status;
    @OneToMany(mappedBy = "job")
    @JsonManagedReference
    private List<Part> parts;
    @OneToMany(mappedBy = "job")
    @JsonManagedReference
    private List<Image> images;

    public Double getTotal() {
        Double sum = this.labor;
        for(Part part: this.parts){
            sum = sum + part.getPrice();
        }
        return sum;
    }
}
