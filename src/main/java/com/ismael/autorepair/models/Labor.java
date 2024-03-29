package com.ismael.autorepair.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Labor {
    @Id
    private UUID id = UUID.randomUUID();
    @Column(nullable = false)
    private String task;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Double cost;
    @Column(nullable = false)
    private Boolean complete = false;
    @JsonIgnore
    @ManyToOne
    private Work work;
    @Transient
    private UUID workId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Labor(String task, String location, Double cost, Boolean complete, Work work) {
        this.task = task;
        this.location = location;
        this.work = work;
        this.cost = cost;
        this.complete = complete;
    }

    public Labor() {
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public UUID getWorkId() {
        return work.getId();
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
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

    @Override
    public String toString() {
        return "Labor{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", location=" + location +
                ", cost=" + cost +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
