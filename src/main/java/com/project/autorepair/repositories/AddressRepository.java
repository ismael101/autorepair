package com.project.autorepair.repositories;

import com.project.autorepair.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//repository for fetching address from table
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    //query for fetching address by job
    @Query("SELECT a FROM Address a WHERE a.job.id = ?1")
    Optional<Address> findAddressByJob(Integer id);
}
