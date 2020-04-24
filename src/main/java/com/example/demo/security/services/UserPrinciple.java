package com.example.demo.security.services;

import com.example.demo.entities.Salarie;
import com.fasterxml.jackson.annotation.JsonIgnore;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class UserPrinciple implements UserDetails {
 private static final long serialVersionUID = 1L;

 private Long id;

   private String nom;

   private String username;

   private String mail;

   @JsonIgnore
   private String password;

   private Collection<? extends GrantedAuthority> authorities;

   public UserPrinciple(Long id, String nom, 
             String username, String mail, String password, 
             Collection<? extends GrantedAuthority> authorities) {
       this.id = id;
       this.nom = nom;
       this.username = username;
       this.mail = mail;
       this.password = password;
       this.authorities = authorities;
   }

   public static UserPrinciple build(Salarie salarie) {
       List<GrantedAuthority> authorities = salarie.getRoles().stream().map(role ->
               new SimpleGrantedAuthority(role.getName().name())
       ).collect(Collectors.toList());

       return new UserPrinciple(
       		salarie.getId(),
       		salarie.getNom(),
       		salarie.getUsername(),
       		salarie.getMail(),
       		salarie.getPassword(),
               authorities
       );
   }

   public Long getId() {
       return id;
   }

   public String getNom() {
       return nom;
   }

   public String getMail() {
       return mail;
   }

   @Override
   public String getUsername() {
       return username;
   }

   @Override
   public String getPassword() {
       return password;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
       return authorities;
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
   public boolean equals(Object o) {
       if (this == o) return true;
       if (o == null || getClass() != o.getClass()) return false;
       
       UserPrinciple salarie = (UserPrinciple) o;
       return Objects.equals(id, salarie.id);
   }
}