package com.apollo.art.oeuvre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.sql.*;
import java.time.LocalDate;

import com.apollo.art.UserAuth.Models.User;

@Entity
@Table(name = "oeuvre", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class OeuvreEntity {

    @Id
    @GeneratedValue(generator = "oeuvre__seq")
    @SequenceGenerator(name = "oeuvre__seq", sequenceName = "_oeuvre_seq", allocationSize = 1)
    Long id;

    @Basic
    @Column(name = "titre")
    private String titre;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "datecreation")
    private int datecreation;

    @Basic
    @Column(name = "datepublication")
    private Date datepublication;

    @Basic
    @Column(name = "largeur")
    private float largeur;

    @Basic
    @Column(name = "longueur")
    private float longueur;

    @Basic
    @Column(name = "status")
    private int status;

    @Basic
    @Column(name = "photo")
    private String[] photo;

    @ManyToOne
    @JoinColumn(name = "idProvenance", referencedColumnName = "id")
    ProvenanceEntity provenance;

    @ManyToOne
    @JoinColumn(name = "idcategorie", referencedColumnName = "id")
    CategorieEntity categorie;

    @ManyToOne
    @JoinColumn(name = "idtype", referencedColumnName = "id")
    TypeEntity type;

    @ManyToOne
    @JoinColumn(name = "idgenre", referencedColumnName = "id")
    GenreEntity genre;

    @ManyToOne
    @JoinColumn(name = "idauteur", referencedColumnName = "id")
    AuteurEntity auteur;

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    User user;

    @ManyToOne
    @JoinColumn(name = "palette", referencedColumnName = "id")
    PaletteEntity palette;

    public void setLongueur(int longueur) {
        if (longueur > 0) {
            this.longueur = longueur;
        }
    }

    public void setLargeur(int largeur) {
        if (largeur > 0) {
            this.largeur = largeur;
        }
    }

    public void setDateCreation(int datecreation) {
        if (datecreation > 1990 && datecreation < LocalDate.now().getYear()) {
            this.datecreation = datecreation;
        }
    }
}
