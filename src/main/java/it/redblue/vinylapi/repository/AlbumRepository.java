package it.redblue.vinylapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.redblue.vinylapi.model.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>{
	
	public Page<Album> findByTitoloLikeAndArtistaLikeAndAnnoLike(String titolo, String artista, String anno, Pageable pageable);
	
	public Page<Album> findByTitoloLike(String titolo, Pageable pageable);

}
