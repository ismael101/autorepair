package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.Insurance;
import com.ismael.autorepair.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, UUID> {
    Optional<Insurance> findInsuranceByWork(Work work);
}
