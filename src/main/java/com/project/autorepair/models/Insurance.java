package com.project.autorepair.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

//table for job insurance
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String policy;
    @Column(nullable = false)
    private String provider;
    @Column(nullable = false)
    private String vin;
    @OneToOne(optional = false)
    @JsonBackReference
    private Job job;

    @Override
    public String toString() {
        return "Insurance{" +
                "id=" + id +
                ", policy='" + policy + '\'' +
                ", provider='" + provider + '\'' +
                ", vin='" + vin + '\'' +
                '}';
    }
}
