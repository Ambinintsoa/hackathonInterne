package com.apollo.art.oeuvre.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apollo.art.oeuvre.entity.ChatEntity;
import com.apollo.art.oeuvre.entity.OeuvreEntity;
import com.google.protobuf.Option;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findByUserId(Long id);
}