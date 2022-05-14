package com.project.autoshop.repositories;

import com.project.autoshop.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartsRepository extends JpaRepository<Part, Integer> {
    //query for fetching parts by job
    @Query("SELECT p FROM Part p WHERE p.job.id = ?1")
    List<Part> findPartsByJob(Integer id);
}
