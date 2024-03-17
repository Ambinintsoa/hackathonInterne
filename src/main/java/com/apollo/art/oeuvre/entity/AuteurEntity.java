package com.apollo.art.oeuvre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "auteur", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class AuteurEntity {

    @Id
    @GeneratedValue(generator = "auteur__seq")
    @SequenceGenerator(name = "auteur__seq", sequenceName = "_auteur_seq", allocationSize = 1)
    Long id;

    @Basic
    @Column(name = "nom")
    private String nom;

}
