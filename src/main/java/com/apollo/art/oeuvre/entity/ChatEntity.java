package com.apollo.art.oeuvre.entity;

import com.apollo.art.UserAuth.Models.User;
import java.sql.*;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class ChatEntity {

    @Id
    @GeneratedValue(generator = "chat__seq")
    @SequenceGenerator(name = "chat__seq", sequenceName = "_chat_seq", allocationSize = 1)
    Long id;

    @Column(columnDefinition = "TEXT", name = "question")
    private String question;

    @Basic
    @Column(name = "photo")
    private String photo;

    @Basic
    @Column(name = "date")
    private Timestamp date;

    @Basic
    @Column(name = "dateBot")
    private Timestamp dateBot;

    @Column(columnDefinition = "TEXT", name = "reponse")
    private String reponse;

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    User user;
}
