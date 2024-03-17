package com.apollo.art.oeuvre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.apollo.art.oeuvre.repository.ProvenanceRepository;
import com.apollo.art.oeuvre.entity.ProvenanceEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProvenanceService {
    @Autowired
    private ProvenanceRepository provenanceRepository;

    public List<ProvenanceEntity> getAll() {
        return provenanceRepository.findAll();
    }

    public Optional<ProvenanceEntity> getById(@NonNull Long id) {
        return provenanceRepository.findById(id);
    }

    public ProvenanceEntity create(@NonNull ProvenanceEntity entity) {
        return provenanceRepository.save(entity);
    }

    public Optional<ProvenanceEntity> update(@NonNull Long id, ProvenanceEntity updated) {
        Optional<ProvenanceEntity> existing = provenanceRepository.findById(id);

        if (existing.isPresent()) {
            updated.setId(id);
            if (updated.getNom() == null) {
                updated.setNom(existing.get().getNom());
            }
            return Optional.of(provenanceRepository.save(updated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        ProvenanceEntity existing = provenanceRepository.findById(id).orElse(null);

        if (existing != null) {
            provenanceRepository.deleteById(id);
        }
    }

    // public List<ProvenanceEntity> selectWithPagination(int offset, int limit) {
    // return provenanceRepository.findAll(PageRequest.of(offset,
    // limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
