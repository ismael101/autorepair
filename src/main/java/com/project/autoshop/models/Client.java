package com.project.autoshop.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Client {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String first;
    @Column(nullable = false)
    private String last;
    @Column(nullable = false, unique = true)
    private String email;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
