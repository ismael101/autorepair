package com.project.autoshop.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
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
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Jobs job;
}
