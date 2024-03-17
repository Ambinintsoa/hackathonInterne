package com.apollo.art.oeuvre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.apollo.art.oeuvre.repository.TypeRepository;
import com.apollo.art.oeuvre.entity.TypeEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public List<TypeEntity> getAll() {
        return typeRepository.findAll();
    }

    public Optional<TypeEntity> getById(@NonNull Long id) {
        return typeRepository.findById(id);
    }

    public TypeEntity create(@NonNull TypeEntity entity) {
        return typeRepository.save(entity);
    }

    public Optional<TypeEntity> update(@NonNull Long id, TypeEntity updated) {
        Optional<TypeEntity> existing = typeRepository.findById(id);

        if (existing.isPresent()) {
            updated.setId(id);
            if (updated.getNom() == null) {
                updated.setNom(existing.get().getNom());
            }
            return Optional.of(typeRepository.save(updated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        TypeEntity existing = typeRepository.findById(id).orElse(null);

        if (existing != null) {
            typeRepository.deleteById(id);
        }
    }

    // public List<TypeEntity> selectWithPagination(int offset, int limit) {
    // return typeRepository.findAll(PageRequest.of(offset, limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
