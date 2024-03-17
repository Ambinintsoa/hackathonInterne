package com.apollo.art.oeuvre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sujetExpo", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class SujetExpo {

    @Id
    @GeneratedValue(generator = "sujetExpo__seq")
    @SequenceGenerator(name = "sujetExpo__seq", sequenceName = "_sujetExpo_seq", allocationSize = 1)
    Long id;

    @Basic
    @Column(name = "theme")
    private String theme;

}
