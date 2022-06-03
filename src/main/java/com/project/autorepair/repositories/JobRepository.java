package com.project.autorepair.repositories;

import com.project.autorepair.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//repository for fetching job from table
@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

}
