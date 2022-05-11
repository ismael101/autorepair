package com.project.autoshop.repositories;

import com.project.autoshop.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    //query for fetching work by client
    @Query("SELECT j FROM Jobs j WHERE j.client = ?1")
    List<Job> findJobsByClient(Integer id);
}
