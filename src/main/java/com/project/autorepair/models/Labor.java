package com.project.autorepair.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;


//table for job labors
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Labor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String task;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Double cost;
    @Column()
    private String notes;
    @JsonBackReference
    @ManyToOne(optional = false)
    private Job job;

    @Override
    public String toString() {
        return "Labor{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", location='" + location + '\'' +
                ", cost=" + cost +
                ", notes='" + notes + '\'' +
                '}';
    }
}
