package com.project.autoshop.repositories;

import com.project.autoshop.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkRepository extends JpaRepository<Work, Integer> {
    //query for fetching work by client
    @Query("SELECT w FROM Work w WHERE w.client = ?1")
    Optional<Work> findByClient(Integer id);
}
