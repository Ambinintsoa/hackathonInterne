package com.apollo.art.oeuvre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "provenance", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class ProvenanceEntity {

    @Id
    @GeneratedValue(generator = "provenance__seq")
    @SequenceGenerator(name = "provenance__seq", sequenceName = "_provenance_seq", allocationSize = 1)
    Long id;

    @Basic
    @Column(name = "nom")
    private String nom;

}
