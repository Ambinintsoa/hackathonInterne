package com.apollo.art.oeuvre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categorie", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class CategorieEntity {

    @Id
    @GeneratedValue(generator = "categorie__seq")
    @SequenceGenerator(name = "categorie__seq", sequenceName = "_categorie_seq", allocationSize = 1)
    Long id;

    @Basic
    @Column(name = "nom")
    private String nom;

}
