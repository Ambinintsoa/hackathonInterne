package com.apollo.art.oeuvre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.apollo.art.oeuvre.repository.AuteurRepository;
import com.apollo.art.oeuvre.repository.ChatRepository;
import com.apollo.art.oeuvre.entity.ChatEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    @Autowired
    private ChatRepository auteurRepository;

    public List<ChatEntity> getAll() {
        return auteurRepository.findAll();
    }

    public Optional<ChatEntity> getById(@NonNull Long id) {
        return auteurRepository.findById(id);
    }

    public List<ChatEntity> getByUserId(@NonNull Long id) {
        return auteurRepository.findByUserId(id);
    }

    public ChatEntity create(@NonNull ChatEntity entity) {
        return auteurRepository.save(entity);
    }

    public Optional<ChatEntity> update(@NonNull Long id, ChatEntity updated) {
        Optional<ChatEntity> existing = auteurRepository.findById(id);

        if (existing.isPresent()) {
            updated.setId(id);
            // if (updated.getNom() == null) {
            // updated.setNom(existing.get().getNom());
            // }
            return Optional.of(auteurRepository.save(updated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        ChatEntity existing = auteurRepository.findById(id).orElse(null);

        if (existing != null) {
            auteurRepository.deleteById(id);
        }
    }

    // public List<ChatEntity> selectWithPagination(int offset, int limit) {
    // return typeRepository.findAll(PageRequest.of(offset, limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
