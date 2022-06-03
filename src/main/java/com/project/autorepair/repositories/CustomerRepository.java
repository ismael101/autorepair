package com.project.autorepair.repositories;

import com.project.autorepair.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//repository for fetching customer from table
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    //query for fetching customer by job
    @Query("SELECT c FROM Customer c WHERE c.job.id = ?1")
    Optional<Customer> findCustomerByJob(Integer id);
}
