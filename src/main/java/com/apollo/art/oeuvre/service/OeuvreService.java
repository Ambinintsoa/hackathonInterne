package com.apollo.art.oeuvre.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.apollo.art.oeuvre.repository.OeuvreRepository;
import com.apollo.art.UserAuth.Models.User;
import com.apollo.art.chat.service.FileHelper;
import com.apollo.art.oeuvre.entity.OeuvreEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OeuvreService extends com.apollo.art.oeuvre.service.Service {
    @Autowired
    private OeuvreRepository oeuvreRepository;

    public List<OeuvreEntity> getAll() {
        return oeuvreRepository.findAll();
    }

    public List<OeuvreEntity> selectMyPublication(int offset, int limit, User user) {
        return oeuvreRepository.findByUserId(user.getId(), PageRequest.of(offset, limit)).getContent();
    }

    public Optional<OeuvreEntity> getById(@NonNull Long id) {
        return oeuvreRepository.findById(id);
    }

    public OeuvreEntity create(@NonNull OeuvreEntity entity) throws Exception {

        System.out.println("aa");
        if (entity.getPhoto() != null) {
            for (int i = 0; i < entity.getPhoto().length; i++) {
                FileHelper file = new FileHelper();
                entity.getPhoto()[i] = file.upload(entity.getPhoto()[i]);
                System.out.println(entity.getPhoto()[i]);
            }

        }
        System.out.println("aa");
        return oeuvreRepository.save(entity);
    }

    public Optional<OeuvreEntity> update(@NonNull Long id, OeuvreEntity updated) {
        Optional<OeuvreEntity> existing = oeuvreRepository.findById(id);

        if (existing.isPresent()) {
            BeanUtils.copyProperties(updated, existing.get(), getNullPropertyNames(updated));
            return Optional.of(oeuvreRepository.save(existing.get()));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        OeuvreEntity existing = oeuvreRepository.findById(id).orElse(null);

        if (existing != null) {
            oeuvreRepository.deleteById(id);
        }
    }

    public List<OeuvreEntity> selectWithPagination(int offset, int limit) {
        return oeuvreRepository.findByStatus(1, PageRequest.of(offset, limit)).getContent();
    }

    public List<OeuvreEntity> selectNotValidWithPagination(int offset, int limit) {
        return oeuvreRepository.findByStatus(0, PageRequest.of(offset, limit)).getContent();
    }

    public long paginationByStatus(int limit, int status) {
        long number = oeuvreRepository.countByStatus(status);
        return (number + limit - 1) / limit;
    }
}
