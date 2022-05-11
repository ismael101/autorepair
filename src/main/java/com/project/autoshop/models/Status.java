package com.project.autoshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
@Builder
public class Status {
    @Id
    @GeneratedValue
    private Integer id;
    private Boolean approved = false;
    private Boolean rejected =  false;
    private Boolean ordered = false;
    private Boolean progress = false;
    private Boolean complete = false;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JsonBackReference
    private Job job;
}
