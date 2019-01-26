package it.redblue.vinylapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.redblue.vinylapi.model.Album;

public interface AlbumService {
	
	public Page<Album> findAllAlbumsPaginated(Pageable pageable);
	
	public List<Album> findAllAlbums();
	
	public Album findAlbum(Long id);
	
	public void saveAlbum(Album album);
	
	public void deleteAlbum(Long id);
	
	public Album updateAlbum(Album album);
	
	public Page<Album> searchAlbumsPaginated(String titolo, String artista, String anno, Pageable pageable);
	
	public Page<Album> searchAlbumByTitoloPaginated(String titolo, Pageable pageable);

}
