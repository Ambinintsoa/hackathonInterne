package com.apollo.art.oeuvre.entity;

import java.sql.Date;

import com.apollo.art.UserAuth.Models.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reaction", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class ReactionEntity {

    @Id
    @GeneratedValue(generator = "reaction__seq")
    @SequenceGenerator(name = "reaction__seq", sequenceName = "_reaction_seq", allocationSize = 1)
    Long id;

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    User user;

    @ManyToOne
    @JoinColumn(name = "idoeuvre", referencedColumnName = "id")
    OeuvreEntity oeuvre;

    @Basic
    @Column(name = "date")
    private Date date;
}
