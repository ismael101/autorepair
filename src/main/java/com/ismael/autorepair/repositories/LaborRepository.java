package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.Labor;
import com.ismael.autorepair.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface LaborRepository extends JpaRepository<Labor, UUID> {
    List<Labor> findLaborByWork(Work work);
}
