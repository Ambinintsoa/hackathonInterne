package com.apollo.art.oeuvre.service;

import com.apollo.art.oeuvre.entity.GenreEntity;
import com.apollo.art.oeuvre.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<GenreEntity> getAll() {
        return genreRepository.findAll();
    }

    public Optional<GenreEntity> getById(@NonNull Long id) {
        return genreRepository.findById(id);
    }

    public GenreEntity create(@NonNull GenreEntity entity) {
        return genreRepository.save(entity);
    }

    public Optional<GenreEntity> update(@NonNull Long id, GenreEntity updated) {
        Optional<GenreEntity> existing = genreRepository.findById(id);

        if (existing.isPresent()) {
            updated.setId(id);
            if (updated.getNom() == null) {
                updated.setNom(existing.get().getNom());
            }
            return Optional.of(genreRepository.save(updated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        GenreEntity existing = genreRepository.findById(id).orElse(null);

        if (existing != null) {
            genreRepository.deleteById(id);
        }
    }

    // public List<GenreEntity> selectWithPagination(int offset, int limit) {
    // return genreRepository.findAll(PageRequest.of(offset, limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
