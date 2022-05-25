package com.project.autoshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String first;
    @Column(nullable = false)
    private String last;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @JsonBackReference
    @OneToOne(optional = false)
    private Job job;

}
