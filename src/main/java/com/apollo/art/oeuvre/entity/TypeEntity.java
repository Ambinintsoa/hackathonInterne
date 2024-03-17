package com.apollo.art.oeuvre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "type", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class TypeEntity {

    @Id
    @GeneratedValue(generator = "type__seq")
    @SequenceGenerator(name = "type__seq", sequenceName = "_type_seq", allocationSize = 1)
    Long id;

    @Basic
    @Column(name = "nom")
    private String nom;

}
