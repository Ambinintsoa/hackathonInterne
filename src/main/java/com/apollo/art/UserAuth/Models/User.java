package com.apollo.art.UserAuth.Models;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.apollo.art.UserAuth.Enum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "user")
@Table(name = "_user")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "user__seq")
    @SequenceGenerator(name = "user__seq", sequenceName = "_user_seq", allocationSize = 1)
    Long id;

    @Column(name = "email", unique = true, nullable = false)
    String email;
    @Column(name = "telephone", unique = true, nullable = false)
    String telephone;

    @Column(name = "pwd", nullable = false)
    String password;

    @Column(name = "nom")
    String nom;

    @Column(name = "dtn")
    Date dtn;

    @Column(name = "gender")
    Integer genre;

    @Column(name = "pdp")
    String pdp;

    @Enumerated(EnumType.STRING)
    Role roles;

    @ManyToOne
    @JoinColumn(name = "idprofile", referencedColumnName = "id")
    Profile profile;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

}
