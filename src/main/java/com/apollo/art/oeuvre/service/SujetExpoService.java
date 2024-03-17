package com.apollo.art.oeuvre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.apollo.art.oeuvre.repository.SujetExpoRepository;
import com.apollo.art.oeuvre.entity.SujetExpo;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SujetExpoService {
    @Autowired
    private SujetExpoRepository sujetExpoRepository;

    public List<SujetExpo> getAll() {
        return sujetExpoRepository.findAll();
    }

    public Optional<SujetExpo> getById(@NonNull Long id) {
        return sujetExpoRepository.findById(id);
    }

    public SujetExpo create(@NonNull SujetExpo entity) {
        return sujetExpoRepository.save(entity);
    }

    public Optional<SujetExpo> update(@NonNull Long id, SujetExpo updated) {
        Optional<SujetExpo> existing = sujetExpoRepository.findById(id);

        if (existing.isPresent()) {
            updated.setId(id);
            if (updated.getTheme() == null) {
                updated.setTheme(existing.get().getTheme());
            }
            return Optional.of(sujetExpoRepository.save(updated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        SujetExpo existing = sujetExpoRepository.findById(id).orElse(null);

        if (existing != null) {
            sujetExpoRepository.deleteById(id);
        }
    }

    // public List<SujetExpo> selectWithPagination(int offset, int limit) {
    // return typeRepository.findAll(PageRequest.of(offset, limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
