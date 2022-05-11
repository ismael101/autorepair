package com.project.autoshop.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
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
    @OneToOne
    @JsonManagedReference
    private Customer customer;
    @OneToOne
    @JsonManagedReference
    private Insurance insurance;
    @OneToOne
    @JsonManagedReference
    private Address address;
    @OneToOne
    @JsonManagedReference
    private Vehicle vehicle;
    @OneToOne
    @JsonManagedReference
    private Status status;
    @OneToMany
    @JsonManagedReference
    private List<Part> parts;
    @ElementCollection
    private List<String> images;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;


    public Double getTotal() {
        Double sum = this.labor;
        for(Part part: this.parts){
            sum = sum + part.getPrice();
        }
        return sum;
    }
}
