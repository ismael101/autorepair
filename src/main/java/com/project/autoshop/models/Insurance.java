package com.project.autoshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private Integer policy;
    @Column(nullable = false)
    private String provider;
    private LocalDateTime effective;
    private LocalDateTime expiration;
    @Column(nullable = false)
    private String vin;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JsonBackReference
    private Job job;
}
