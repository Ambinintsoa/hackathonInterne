package com.apollo.art.oeuvre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apollo.art.oeuvre.entity.ReactionEntity;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {

}