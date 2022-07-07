package com.project.autorepair.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

//table for job customers
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
