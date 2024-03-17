package com.apollo.art.oeuvre.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apollo.art.oeuvre.entity.OeuvreEntity;

@Repository
public interface OeuvreRepository extends JpaRepository<OeuvreEntity, Long> {
    Page<OeuvreEntity> findByUserId(Long user, Pageable pageable);

    Page<OeuvreEntity> findByStatus(int status, Pageable pageable);

    int countByStatus(int status);
}