package com.mysql.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cours {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String titre;

	private String description;

	public Cours() {
		// TODO Auto-generated constructor stub
	}

	public Cours(String titre, String description) {
		super();
		this.titre = titre;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
