package com.apollo.art.UserAuth.Service;

import com.apollo.art.UserAuth.Models.Profile;
import com.apollo.art.UserAuth.Repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> getAll() {
        return profileRepository.findAll();
    }

    public Optional<Profile> getById(@NonNull Long id) {
        return profileRepository.findById(id);
    }

    public Profile create(@NonNull Profile entity) {
        return profileRepository.save(entity);
    }

    public Optional<Profile> update(@NonNull Long id, Profile updated) {
        Optional<Profile> existing = profileRepository.findById(id);

        if (existing.isPresent()) {
            updated.setId(id);
            if (updated.getNom() == null) {
                updated.setNom(existing.get().getNom());
            }
            return Optional.of(profileRepository.save(updated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(@NonNull Long id) {
        Profile existing = profileRepository.findById(id).orElse(null);

        if (existing != null) {
            profileRepository.deleteById(id);
        }
    }

    // public List<Profile> selectWithPagination(int offset, int limit) {
    // return categorieRepository.findAll(PageRequest.of(offset,
    // limit)).getContent();
    // }

    // public long pagination(int limit) {
    // long number = gem.count();
    // return (number + limit - 1) / limit;
    // }
}
