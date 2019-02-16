package it.redblue.vinylapi.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.redblue.vinylapi.model.Album;
import it.redblue.vinylapi.repository.AlbumRepository;
import it.redblue.vinylapi.service.AlbumService;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
	
	Logger log = LoggerFactory.getLogger(AlbumServiceImpl.class); 

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AlbumRepository repository;
	
	@Override
	public Album updateAlbum(Album album) {
		log.info("Album service :: updateAlbum");
		return em.merge(album);
	}

	@Override
	public Page<Album> findAllAlbumsPaginated(Pageable pageable) {
		log.info("Album service :: findAllAlbumsPaginated");
		return repository.findAll(pageable);
	}
	
	@Override
	public List<Album> findAllAlbums() {
		log.info("Album service :: findAllAlbums");
		return repository.findAll();
	}

	@Override
	public Album findAlbum(Long id) {
		log.info("Album service :: findAlbum");
		String paraId = "paraId";
		String query = "select a from Album a where id = :" + paraId;
		Query q = em.createQuery(query);
		q.setParameter(paraId, id);
		return (Album) q.getSingleResult();
	}

	@Override
	public void saveAlbum(Album album) {
		log.info("Album service :: saveAlbum");
		repository.save(album);
	}

	@Override
	public void deleteAlbum(Long id) {
		log.info("Album service :: deleteAlbum");
		repository.deleteById(id);
	}

	@Override
	public Page<Album> searchAlbumsPaginated(String titolo, String artista, String anno, Pageable pageable) {
		log.info("Album service :: searchAlbums");
		return repository.findByTitoloLikeAndArtistaLikeAndAnnoLike(titolo, artista, anno, pageable);
	}

	@Override
	public Page<Album> searchAlbumByTitoloPaginated(String titolo, Pageable pageable) {
		log.info("Album service :: searchAlbumsByTitolo");
		return repository.findByTitoloLike(titolo, pageable);
	}

}
