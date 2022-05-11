package com.project.autoshop.repositories;

import com.project.autoshop.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE a.job_id = ?1")
    Optional<Address> findAddressByClient(Integer id);
}
