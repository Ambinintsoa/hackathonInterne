package com.apollo.art.oeuvre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "partage", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class PartageEntity {

    @Id
    @GeneratedValue(generator = "partage__seq")
    @SequenceGenerator(name = "partage__seq", sequenceName = "_partage_seq", allocationSize = 1)
    Long id;

    @Basic
    @Column(name = "nom")
    private String nom;

}
