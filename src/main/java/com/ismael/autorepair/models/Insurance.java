package com.ismael.autorepair.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Insurance {
    @Id
    private UUID id = UUID.randomUUID();
    @Column(nullable = false)
    private String provider;
    @Column(nullable = false)
    private String policy;
    @Column(nullable = false)
    private String vin;
    @JsonIgnore
    @OneToOne
    private Work work;
    @Transient
    private UUID workId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Insurance(String provider, String policy, String vin, Work work) {
        this.provider = provider;
        this.policy = policy;
        this.vin = vin;
        this.work = work;
    }

    public Insurance() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getWorkId() {
        return work.getId();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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
        return "Insurance{" +
                "id=" + id +
                ", provider='" + provider + '\'' +
                ", policy='" + policy + '\'' +
                ", vin='" + vin + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
