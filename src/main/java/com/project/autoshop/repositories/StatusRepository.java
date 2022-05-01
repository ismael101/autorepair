package com.project.autoshop.repositories;

import com.project.autoshop.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    @Query("SELECT s FROM Status WHERE s.work = ?1")
    Optional findStatusByWork(Integer id);
}
