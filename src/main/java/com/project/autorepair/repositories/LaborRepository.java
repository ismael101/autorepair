package com.project.autorepair.repositories;

import com.project.autorepair.models.Labor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//repository for fetching labor from table
@Repository
public interface LaborRepository extends JpaRepository<Labor, Integer> {
    //query for fetching labors by job
    @Query("SELECT l FROM Labor l WHERE l.job.id = ?1")
    List<Labor> findLaborsByJob(Integer id);
}
