package com.project.autorepair.repositories;

import com.project.autorepair.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//repository for fetching part from table
@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
    //query for fetching parts by job
    @Query("SELECT p FROM Part p WHERE p.job.id = ?1")
    List<Part> findPartsByJob(Integer id);
}
