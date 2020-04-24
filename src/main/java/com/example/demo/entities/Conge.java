package com.example.demo.entities;




import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
public class Conge {
	@Id 
	@GeneratedValue
	private Long num;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_debut;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_fin;
	private String statut;
	@ManyToOne
	@JoinColumn
	private TypeConge typeconge;
	
	@ManyToOne
	@JoinColumn
	private Salarie salarie;

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
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

	public TypeConge getTypeconge() {
		return typeconge;
	}

	public void setTypeconge(TypeConge typeconge) {
		this.typeconge = typeconge;
	}

	public Salarie getSalarie() {
		return salarie;
	}

	public void setSalarie(Salarie salarie) {
		this.salarie = salarie;
	}

	public Conge(Long num, Date date_debut, Date date_fin, String statut, TypeConge typeconge, Salarie salarie) {
		super();
		this.num = num;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.statut = statut;
		this.typeconge = typeconge;
		this.salarie = salarie;
	}

	public Conge() {
		super();
	}

	public Conge(Date date_debut, Date date_fin, String statut, TypeConge typeconge, Salarie salarie) {
		super();
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.statut = statut;
		this.typeconge = typeconge;
		this.salarie = salarie;
	}

	public Conge(Long num, Date date_debut, Date date_fin, String statut, TypeConge typeconge) {
		super();
		this.num = num;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.statut = statut;
		this.typeconge = typeconge;
	}

	public Conge(Date date_debut, Date date_fin, String statut, TypeConge typeconge) {
		super();
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.statut = statut;
		this.typeconge = typeconge;
	}

	


}
