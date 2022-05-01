package com.project.autoshop.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Status {
    @Id
    @GeneratedValue
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Stage stage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToOne(optional = false)
    private Work work;

    public Status(Stage stage, LocalDateTime createdAt, LocalDateTime updatedAt, Work work) {
        this.stage = stage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.work = work;
    }

    public Status() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }
}
