package com.apollo.art.oeuvre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.apollo.art.oeuvre.repository.DescriptionRepository;
import com.apollo.art.oeuvre.entity.DescriptionEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DescriptionService {
    @Autowired
    private DescriptionRepository descriptionRepository;

    public List<DescriptionEntity> getAll() {
        return descriptionRepository.findAll();
    }

    public Optional<DescriptionEntity> getById(@NonNull Long id) {
        return descriptionRepository.findById(id);
    }

    public DescriptionEntity create(@NonNull DescriptionEntity entity) {
        return descriptionRepository.save(entity);
    }

    // public Optional<DescriptionEntity> update(@NonNull Long id, DescriptionEntity
    // updated) {
    // Optional<DescriptionEntity> existing = descriptionRepository.findById(id);

    // if (existing.isPresent()) {
    // updated.setId(id);
    // return Optional.of(descriptionRepository.save(updated));
    // } else {
    // return Optional.empty();
    // }
    // }

    public void delete(@NonNull Long id) {
        DescriptionEntity existing = descriptionRepository.findById(id).orElse(null);

        if (existing != null) {
            descriptionRepository.deleteById(id);
        }
    }

    // public List<DescriptionEntity> selectWithPagination(int offset, int limit) {
    // return typeRepository.findAll(PageRequest.of(offset, limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
