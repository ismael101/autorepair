package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.Customer;
import com.ismael.autorepair.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findCustomerByWork(Work work);
}
