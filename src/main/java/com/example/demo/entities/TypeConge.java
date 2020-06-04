package com.example.demo.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TypeConge {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_type;
	private String type_conge;
	private double max_permis;
//	private double cong_deja_pris;
//	private double cong_restant;
	@OneToMany(mappedBy="typeconge",cascade=CascadeType.ALL)
	@JsonIgnore
	private List <Conge>conges;
	
	
	public String getType_conge() {
		return type_conge;
	}
	public void setType_conge(String type_conge) {
		this.type_conge = type_conge;
	}

	public List<Conge> getConges() {
		return conges;
	}
	public void setConges(List<Conge> conges) {
		this.conges = conges;
	}
	public Long getId_type() {
		return id_type;
	}
	public void setId_type(Long id_type) {
		this.id_type = id_type;
	}
	public double getMax_permis() {
		return max_permis;
	}
	public void setMax_permis(double max_permis) {
		this.max_permis = max_permis;
	}
//	public double getCong_deja_pris() {
//		return cong_deja_pris;
//	}
//	public void setCong_deja_pris(double cong_deja_pris) {
//		this.cong_deja_pris = cong_deja_pris;
//	}
//	public double getCong_restant() {
//		return cong_restant;
//	}
//	public void setCong_restant(double cong_restant) {
//		this.cong_restant = cong_restant;
//	}


	public TypeConge(Long id_type, String type_Conge, double max_permis
			) {
//		, double cong_deja_pris, double cong_restant
//		, List<Conge> conges
		super();
		this.id_type = id_type;
		this.type_conge = type_Conge;
		this.max_permis = max_permis;
//		this.cong_deja_pris = cong_deja_pris;
//		this.cong_restant = cong_restant;
//		this.conges = conges;
	}
	public TypeConge(String type_Conge, double max_permis) {
//		, List<Conge> conges
//		, double cong_deja_pris, double cong_restant
		super();
		this.type_conge = type_Conge;
		this.max_permis = max_permis;
//		this.cong_deja_pris = cong_deja_pris;
//		this.cong_restant = cong_restant;
//		this.conges = conges;
	}
	public TypeConge() {
		super();
	}

}
