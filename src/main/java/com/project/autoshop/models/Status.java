package com.project.autoshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Status {
    @Id
    @GeneratedValue
    private Integer id;
    private Boolean approved;
    private Boolean rejected;
    private Boolean ordered;
    private Boolean progress;
    private Boolean complete;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JsonBackReference
    private Job job;
}
