package com.softwareproject.backend.repository;

import com.softwareproject.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT * FROM image ORDER BY times_shown ASC", nativeQuery = true)
    List<Image> getImagesForVoting();

    @Modifying
    @Transactional
    @Query(value = "UPDATE image SET times_shown = times_shown +1 WHERE id = :id", nativeQuery = true)
    void increaseTimesShownForVoting(@Param("id") int id);
}
