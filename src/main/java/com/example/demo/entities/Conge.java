package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Conge {
	@Id 
	@GeneratedValue
	private Long num;
	private Date date_debut;
	private Date date_fin;
	private String statut;
	
	@ManyToOne
	@JoinColumn
	
	private Salarie salarie;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	public Conge(Long num, Date date_debut, Date date_fin, String statut, Long id, Salarie salarie) {
		super();
		this.num = num;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.statut = statut;
		this.id = id;
		this.salarie = salarie;
	}

	public Conge(Date date_debut, Date date_fin, String statut) {
		super();
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.statut = statut;
	}
	public Salarie getSalarie() {
		return salarie;
	}
	public void setSalarie(Salarie salarie) {
		this.salarie = salarie;
	}
	public Date getDate_debut() {
		return date_debut;
	}
	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}
	public Date getDate_fin() {
		return date_fin;
	}
	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	
	public Conge() {
		super();
	}



}
