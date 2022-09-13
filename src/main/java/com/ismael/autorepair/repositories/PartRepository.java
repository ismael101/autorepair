package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.Part;
import com.ismael.autorepair.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PartRepository extends JpaRepository<Part, UUID> {
    List<Part> findPartByWork(Work work);
}
