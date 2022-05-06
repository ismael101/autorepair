package com.project.autoshop.repositories;

import com.project.autoshop.models.Parts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartsRepository extends JpaRepository<Parts, Integer> {
    //query for finding parts by job
    @Query("SELECT p FROM Parts p WHERE p.job = ?1")
    List<Parts> findPartsByJob(Integer id);
}
