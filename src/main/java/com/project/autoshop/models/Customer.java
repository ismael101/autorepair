package com.project.autoshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String first;
    @Column(nullable = false)
    private String last;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private Integer phone;
    @JsonBackReference
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Job job;

}
