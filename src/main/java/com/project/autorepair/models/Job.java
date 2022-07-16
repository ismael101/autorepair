package com.project.autorepair.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//table for job
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    private Boolean complete;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Customer customer;
    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Insurance insurance;
    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Address address;
    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Vehicle vehicle;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Labor> labors;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Part> parts;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Image> images;

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", complete=" + complete +
                '}';
    }
}
