package com.project.autoshop.repositories;

import com.project.autoshop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    //query for fetching customer by job
    @Query("SELECT c FROM Customer c WHERE c.job.id = ?1")
    Optional<Customer> findClientByJob(Integer id);
}
