package it.redblue.vinylapi.utils;

import java.util.List;

public class Payload {
	
	private String album;
	private List<String> immagini;
	
	public Payload(String album, List<String> immagini) {
		super();
		this.album = album;
		this.immagini = immagini;
	}

	public Payload() {
		super();
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public List<String> getImmagini() {
		return immagini;
	}

	public void setImmagini(List<String> immagini) {
		this.immagini = immagini;
	}

}
