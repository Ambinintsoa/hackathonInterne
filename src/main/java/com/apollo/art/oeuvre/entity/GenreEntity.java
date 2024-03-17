package com.apollo.art.oeuvre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "genre", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class GenreEntity {

    @Id
    @GeneratedValue(generator = "genre__seq")
    @SequenceGenerator(name = "genre__seq", sequenceName = "_genre_seq", allocationSize = 1)
    Long id;

    @Basic
    @Column(name = "nom")
    private String nom;

}
