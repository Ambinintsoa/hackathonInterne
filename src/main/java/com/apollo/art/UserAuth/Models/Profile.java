package com.apollo.art.UserAuth.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity(name = "profile")
@Table(name = "profile")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(generator = "profile__seq")
    @SequenceGenerator(name = "profile__seq", sequenceName = "_profile_seq", allocationSize = 1)
    Long id;

    @Column(name = "nom")
    String nom;
}
