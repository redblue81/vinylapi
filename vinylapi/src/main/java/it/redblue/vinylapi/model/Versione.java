package it.redblue.vinylapi.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_versions")
public class Versione implements Serializable {

	private static final long serialVersionUID = 1452565274404782174L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long ID;
	
	@Column(name = "data")
	private Date data;
	
	@Column(name = "versione")
	private String versione;
	
	public Versione() {
		super();
	}

	public Versione(long id, Date data, String versione) {
		super();
		this.ID = id;
		this.data = data;
		this.versione = versione;
	}

	public long getId() {
		return ID;
	}

	public void setId(long id) {
		this.ID = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getVersione() {
		return versione;
	}

	public void setVersione(String versione) {
		this.versione = versione;
	}

}
