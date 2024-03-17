package com.apollo.art.oeuvre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.apollo.art.oeuvre.entity.PaletteEntity;
import com.apollo.art.oeuvre.repository.PaletteRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaletteService {
    @Autowired
    private PaletteRepository paletteRepository;

    public List<PaletteEntity> getAll() {
        return paletteRepository.findAll();
    }

    public Optional<PaletteEntity> getById(@NonNull Long id) {
        return paletteRepository.findById(id);
    }

    public PaletteEntity create(@NonNull PaletteEntity entity) {
        return paletteRepository.save(entity);
    }

    public Optional<PaletteEntity> update(@NonNull Long id, PaletteEntity updated) {
        Optional<PaletteEntity> existing = paletteRepository.findById(id);

        if (existing.isPresent()) {
            updated.setId(id);
            if (updated.getNom() == null) {
                updated.setNom(existing.get().getNom());
            }
            return Optional.of(paletteRepository.save(updated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        PaletteEntity existing = paletteRepository.findById(id).orElse(null);

        if (existing != null) {
            paletteRepository.deleteById(id);
        }
    }

    // public List<PaletteEntity> selectWithPagination(int offset, int limit) {
    // return paletteRepository.findAll(PageRequest.of(offset, limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
