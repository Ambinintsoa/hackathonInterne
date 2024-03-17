package com.apollo.art.oeuvre.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.apollo.art.oeuvre.repository.ReactionRepository;
import com.apollo.art.oeuvre.entity.ReactionEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionService extends com.apollo.art.oeuvre.service.Service {
    @Autowired
    private ReactionRepository reactionRepository;

    public List<ReactionEntity> getAll() {
        return reactionRepository.findAll();
    }

    public Optional<ReactionEntity> getById(@NonNull Long id) {
        return reactionRepository.findById(id);
    }

    public ReactionEntity create(@NonNull ReactionEntity entity) {
        return reactionRepository.save(entity);
    }

    public Optional<ReactionEntity> update(@NonNull Long id, ReactionEntity updated) {
        Optional<ReactionEntity> existing = reactionRepository.findById(id);

        if (existing.isPresent()) {
            BeanUtils.copyProperties(updated, existing.get(), getNullPropertyNames(updated));
            return Optional.of(reactionRepository.save(existing.get()));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        ReactionEntity existing = reactionRepository.findById(id).orElse(null);

        if (existing != null) {
            reactionRepository.deleteById(id);
        }
    }

    // public List<ReactionEntity> selectWithPagination(int offset, int limit) {
    // return typeRepository.findAll(PageRequest.of(offset, limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
