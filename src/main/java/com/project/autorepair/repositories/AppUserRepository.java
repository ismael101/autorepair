package com.project.autorepair.repositories;

import com.project.autorepair.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//repository for fetching user from table
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    //query for fetching user by username
    @Query("SELECT u FROM AppUser u WHERE u.username = ?1")
    Optional<AppUser> findByUsername(String username);
}
