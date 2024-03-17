package com.apollo.art.oeuvre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "palette", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class PaletteEntity {

    @Id
    @GeneratedValue(generator = "palette__seq")
    @SequenceGenerator(name = "palette__seq", sequenceName = "_palette_seq", allocationSize = 1)
    Long id;

    @Basic
    @Column(name = "nom")
    private String nom;

}
