package com.apollo.art.oeuvre.service;

import com.apollo.art.oeuvre.entity.CategorieEntity;
import com.apollo.art.oeuvre.repository.CategorieRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    public List<CategorieEntity> getAll() {
        return categorieRepository.findAll();
    }

    public Optional<CategorieEntity> getById(@NonNull Long id) {
        return categorieRepository.findById(id);
    }

    public CategorieEntity create(@NonNull CategorieEntity entity) {
        return categorieRepository.save(entity);
    }

    public Optional<CategorieEntity> update(@NonNull Long id, CategorieEntity updated) {
        Optional<CategorieEntity> existing = categorieRepository.findById(id);

        if (existing.isPresent()) {
            updated.setId(id);
            if (updated.getNom() == null) {
                updated.setNom(existing.get().getNom());
            }
            return Optional.of(categorieRepository.save(updated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        CategorieEntity existing = categorieRepository.findById(id).orElse(null);

        if (existing != null) {
            categorieRepository.deleteById(id);
        }
    }

    // public List<CategorieEntity> selectWithPagination(int offset, int limit) {
    // return categorieRepository.findAll(PageRequest.of(offset,
    // limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
