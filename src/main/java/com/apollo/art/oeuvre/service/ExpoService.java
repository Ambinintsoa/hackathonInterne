package com.apollo.art.oeuvre.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.apollo.art.oeuvre.repository.ExpoRepository;
import com.apollo.art.chat.service.FileHelper;
import com.apollo.art.oeuvre.entity.ExpoEntity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpoService extends com.apollo.art.oeuvre.service.Service {
    @Autowired
    private ExpoRepository expoRepository;

    public List<ExpoEntity> getAll() {
        return expoRepository.findAll();
    }

    public Optional<ExpoEntity> getById(@NonNull Long id) {
        return expoRepository.findById(id);
    }

    public ExpoEntity create(@NonNull ExpoEntity entity) throws Exception {
        if (entity.getPhoto() != null) {
            FileHelper file = new FileHelper();
            entity.setPhoto(file.upload(entity.getPhoto()));
        }
        return expoRepository.save(entity);
    }

    public Optional<ExpoEntity> update(@NonNull Long id, ExpoEntity updated) {
        Optional<ExpoEntity> existing = expoRepository.findById(id);

        if (existing.isPresent()) {
            updated.setId(id);
            BeanUtils.copyProperties(updated, existing.get(), getNullPropertyNames(updated));
            return Optional.of(expoRepository.save(existing.get()));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        ExpoEntity existing = expoRepository.findById(id).orElse(null);

        if (existing != null) {
            expoRepository.deleteById(id);
        }
    }

    public List<ExpoEntity> getProchainesExpositions() {
        // Récupérer la date actuelle
        Date maintenant = new Date(System.currentTimeMillis());

        // Récupérer les expositions dont la date de début est postérieure à la date
        // actuelle
        List<ExpoEntity> expositionsAVenir = expoRepository.findByDateAfterOrderByDate(maintenant);

        // Trier les expositions par date de début
        expositionsAVenir.sort(Comparator.comparing(ExpoEntity::getDate));

        // Limiter le nombre d'expositions retournées à 3
        int nombreMaxExpositions = 3;
        if (expositionsAVenir.size() > nombreMaxExpositions) {
            expositionsAVenir = expositionsAVenir.subList(0, nombreMaxExpositions);
        }

        return expositionsAVenir;
    }
    // public List<ExpoEntity> selectWithPagination(int offset, int limit) {
    // return typeRepository.findAll(PageRequest.of(offset, limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
