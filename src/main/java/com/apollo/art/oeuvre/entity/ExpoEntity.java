package com.apollo.art.oeuvre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.sql.*;

@Entity
@Table(name = "expo", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class ExpoEntity {

    @Id
    @GeneratedValue(generator = "expo__seq")
    @SequenceGenerator(name = "expo__seq", sequenceName = "_expo_seq", allocationSize = 1)
    Long id;

    @Basic
    @Column(name = "nom")
    private String nom;

    @Basic
    @Column(name = "date")
    private Date date;

    @Basic
    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "idSujetExpo", referencedColumnName = "id")
    SujetExpo sujetExpo;
}
