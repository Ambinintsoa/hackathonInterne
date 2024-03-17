package com.apollo.art.oeuvre.repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apollo.art.oeuvre.entity.ExpoEntity;

@Repository
public interface ExpoRepository extends JpaRepository<ExpoEntity, Long> {
    List<ExpoEntity> findByDateAfterOrderByDate(Date dateDebut);
}