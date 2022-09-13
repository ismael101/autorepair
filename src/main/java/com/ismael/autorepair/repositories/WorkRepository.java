package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkRepository extends JpaRepository<Work, UUID> {
    List<Work> findWorkByUser(User user);
}
