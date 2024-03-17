package com.apollo.art.oeuvre.entity;

import com.apollo.art.UserAuth.Models.Profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "description", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class DescriptionEntity {

    @Id
    @GeneratedValue(generator = "description__seq")
    @SequenceGenerator(name = "description__seq", sequenceName = "_description_seq", allocationSize = 1)
    Long id;

    @ManyToOne
    @JoinColumn(name = "idOeuvre", referencedColumnName = "id")
    OeuvreEntity oeuvre;
    @Basic
    @Column(name = "debut")
    private int debut;

    @Basic
    @Column(name = "fin")
    private int fin;

    @Basic
    @Column(name = "contenu")
    private String contenu;

    @Basic
    @Column(name = "genre")
    private int genre;

    @ManyToOne
    @JoinColumn(name = "idprofile", referencedColumnName = "id")
    Profile profile;
}
