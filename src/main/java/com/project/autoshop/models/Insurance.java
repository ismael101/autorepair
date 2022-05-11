package com.project.autoshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Builder
@Entity
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
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JsonBackReference
    private Job job;
}
