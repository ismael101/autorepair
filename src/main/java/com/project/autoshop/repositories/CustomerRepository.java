package com.project.autoshop.repositories;

import com.project.autoshop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    //query for fetching user by email
    @Query("SELECT c FROM Client c WHERE c.email = ?1")
    Optional<Customer> findClientByEmail(String email);
}
