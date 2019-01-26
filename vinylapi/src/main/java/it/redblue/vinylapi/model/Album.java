package it.redblue.vinylapi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "album")
public class Album implements Serializable {

	private static final long serialVersionUID = 5593122541317214780L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	@Column(name = "titolo")
	private String titolo;
	
	@Column(name = "artista")
	private String artista;
	
	@Column(name = "anno")
	private int anno;
	
	@Column(name = "note", nullable = true)
	private String note;
	
	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Image> immagini;
	
	public Album() {
		super();
	}

	public Album(long id, String titolo, String artista, int anno, String note, List<Image> immagini) {
		super();
		this.id = id;
		this.titolo = titolo;
		this.artista = artista;
		this.anno = anno;
		this.note = note;
		this.immagini = immagini;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Image> getImmagini() {
		return immagini;
	}

	public void setImmagini(List<Image> immagini) {
		this.immagini = immagini;
	}

}
