package com.example.demo.entities;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_SALARIE")
@Table (name="Salarie")
public class Salarie  {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nom;
	private String prenom;
	private float solde_conge;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_entree;
	private String grade;
	private String mail;
	private String num_tel;
	private String nom_responsable;
	private String groupe;
	private String username;
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    joinColumns = @JoinColumn(name = "user_id"), 
  	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	
	public Salarie(Long id, String nom, String prenom, float solde_conge, Date date_entree, String grade, String mail,
			String num_tel, String nom_responsable, String groupe, String username, String password, 
			Set<Role> roles, List<Conge> conge) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.solde_conge = solde_conge;
		this.date_entree = date_entree;
		this.grade = grade;
		this.mail = mail;
		this.num_tel = num_tel;
		this.nom_responsable = nom_responsable;
		this.groupe = groupe;
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.conge = conge;
	}

	public Set<Role> getRoles() {
        return roles;
    }
 
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
	    
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@OneToMany(mappedBy="salarie",cascade=CascadeType.ALL)
	@JsonIgnore
	private List <Conge>conge;
	
	public List<Conge> getConge() {
		return conge;
	}
	
	public void setConge(List<Conge> conge) {
		this.conge = conge;
	}
	
	public Salarie(Long id, String nom,  String prenom, float solde_conge,String username,
			Date date_entree, String grade, String mail, String num_tel, String nom_responsable,
			String groupe, List<Conge> conge) {
		super();
		this.id = id;
		this.nom = nom;
		this.username=username;
		this.prenom = prenom;
		this.solde_conge = solde_conge;
		this.date_entree = date_entree;
		this.grade = grade;
		this.mail = mail;
		this.num_tel = num_tel;
		this.nom_responsable = nom_responsable;
		this.groupe = groupe;
		this.conge = conge;
	}
	
	public Salarie(@NotBlank @Size(min = 3, max = 50) String nom, String mail,
			@NotBlank @Size(min = 3, max = 50) String username,
			@NotBlank @Size(min = 6, max = 100) String password) {
		super();
		this.nom = nom;
		this.mail = mail;
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
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
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
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
	
	public Salarie(@NotBlank @Size(min = 3, max = 50) String nom, String prenom, float solde_conge, Date date_entree,
			String grade, String mail, String num_tel, String nom_responsable, String groupe,
			@NotBlank @Size(min = 3, max = 50) String username, @NotBlank @Size(min = 6, max = 100) String password) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.solde_conge = solde_conge;
		this.date_entree = date_entree;
		this.grade = grade;
		this.mail = mail;
		this.num_tel = num_tel;
		this.nom_responsable = nom_responsable;
		this.groupe = groupe;
		this.username = username;
		this.password = password;
	}
	
	public Salarie(@NotBlank @Size(min = 3, max = 50) String nom, String prenom, float solde_conge, Date date_entree,
			String grade, String mail, String num_tel, String nom_responsable, String groupe,
			@NotBlank @Size(min = 3, max = 50) String username, @NotBlank @Size(min = 6, max = 100) String password,
			Set<Role> roles) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.solde_conge = solde_conge;
		this.date_entree = date_entree;
		this.grade = grade;
		this.mail = mail;
		this.num_tel = num_tel;
		this.nom_responsable = nom_responsable;
		this.groupe = groupe;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
	public Salarie(Long id, String nom,  String prenom, float solde_conge,String username,
			Date date_entree, String grade, String mail, String num_tel, String nom_responsable, String groupe,String password
			) {
		super();
		this.id = id;
		this.nom = nom;
		this.username=username;
		this.prenom = prenom;
		this.solde_conge = solde_conge;
		this.date_entree = date_entree;
		this.grade = grade;
		this.mail = mail;
		this.num_tel = num_tel;
		this.nom_responsable = nom_responsable;
		this.groupe = groupe;
	    this.password=password;
	
	}
	
	public Salarie() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	
}




