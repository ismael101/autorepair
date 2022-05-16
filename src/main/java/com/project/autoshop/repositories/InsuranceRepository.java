package com.project.autoshop.repositories;

import com.project.autoshop.models.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    //query for fetching images by job
    @Query("SELECT i FROM Insurance i WHERE i.job.id = ?1")
    Optional<Insurance> findInsuranceByJob(Integer id);
}
