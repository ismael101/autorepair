package com.project.autoshop.repositories;

import com.project.autoshop.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    //query for fetching status by work
    @Query("SELECT s FROM Status s WHERE s.work = ?1")
    Optional<Status> findStatusByWork(Integer id);
}
