package com.apollo.art.UserAuth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apollo.art.UserAuth.Models.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}