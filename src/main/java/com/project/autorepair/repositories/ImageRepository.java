package com.project.autorepair.repositories;

import com.project.autorepair.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//repository for fetching image from table
@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    //query for fetching images by job
    @Query("SELECT i FROM Image i WHERE i.job.id = ?1")
    List<Image> findImagesByJob(Integer id);
}
