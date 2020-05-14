package com.example.demo.message.request;


import java.util.Date;
import java.util.Set;

import javax.validation.constraints.*;

public class SignUpForm {
   @NotBlank
   @Size(min = 3, max = 50)
   private String nom;

   @NotBlank
   @Size(min = 3, max = 50)
   private String username;

   @NotBlank
   @Size(max = 60)
   @Email
   private String mail;
	private String prenom;
	private float solde_conge;
	private Date date_entree;
	private String grade;
	private String num_tel;
	private String nom_responsable;
	private String groupe;
	private String manager;
	
   public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public float getSolde_conge() {
		return solde_conge;
	}

	public void setSolde_conge(float solde_conge) {
		this.solde_conge = solde_conge;
	}

	public Date getDate_entree() {
		return date_entree;
	}

	public void setDate_entree(Date date_entree) {
		this.date_entree = date_entree;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getNum_tel() {
		return num_tel;
	}

	public void setNum_tel(String num_tel) {
		this.num_tel = num_tel;
	}

	public String getNom_responsable() {
		return nom_responsable;
	}

	public void setNom_responsable(String nom_responsable) {
		this.nom_responsable = nom_responsable;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	private Set<String> roles;
   
   @NotBlank
   @Size(min = 6, max = 40)
   private String password;

   public String getNom() {
       return nom;
   }

   public void setNom(String nom) {
       this.nom = nom;
   }

   public String getUsername() {
       return username;
   }

   public void setUsername(String username) {
       this.username = username;
   }

   public String getMail() {
       return mail;
   }

   public void setMail(String mail) {
       this.mail = mail;
   }

   public String getPassword() {
       return password;
   }

   public void setPassword(String password) {
       this.password = password;
   }
   
   public Set<String> getRoles() {
     return this.roles;
   }
   
   public void setRoles(Set<String> roles) {
     this.roles = roles;
   }
}