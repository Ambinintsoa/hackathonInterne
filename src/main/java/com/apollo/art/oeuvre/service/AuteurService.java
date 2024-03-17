package com.apollo.art.oeuvre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.apollo.art.oeuvre.repository.AuteurRepository;
import com.apollo.art.oeuvre.entity.AuteurEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuteurService {
    @Autowired
    private AuteurRepository auteurRepository;

    public List<AuteurEntity> getAll() {
        return auteurRepository.findAll();
    }

    public Optional<AuteurEntity> getById(@NonNull Long id) {
        return auteurRepository.findById(id);
    }

    public AuteurEntity create(@NonNull AuteurEntity entity) {
        return auteurRepository.save(entity);
    }

    public Optional<AuteurEntity> update(@NonNull Long id, AuteurEntity updated) {
        Optional<AuteurEntity> existing = auteurRepository.findById(id);

        if (existing.isPresent()) {
            updated.setId(id);
            if (updated.getNom() == null) {
                updated.setNom(existing.get().getNom());
            }
            return Optional.of(auteurRepository.save(updated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        AuteurEntity existing = auteurRepository.findById(id).orElse(null);

        if (existing != null) {
            auteurRepository.deleteById(id);
        }
    }

    // public List<AuteurEntity> selectWithPagination(int offset, int limit) {
    // return typeRepository.findAll(PageRequest.of(offset, limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
