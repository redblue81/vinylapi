package it.redblue.vinylapi.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.redblue.vinylapi.model.Album;
import it.redblue.vinylapi.model.Versione;
import it.redblue.vinylapi.repository.impl.VersionRepositoryImpl;
import it.redblue.vinylapi.service.AlbumService;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;

@RestController
@RequestMapping("/vinylcollection/api")
public class AlbumController {

	Logger log = LoggerFactory.getLogger(AlbumController.class);
	
	@Autowired
	private AlbumService service;
	
	@Autowired
	private VersionRepositoryImpl versionRepo;

	@PostMapping(value = "/create", produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Album> createAlbum(@RequestBody Album album) throws BadHttpRequest {
		log.info("Creazione nuovo album: {} - {}", album.getTitolo(), album.getArtista());
		if (album.getTitolo().isEmpty() || album.getArtista().isEmpty() || album.getAnno() == 0)
			throw new BadHttpRequest();
		service.saveAlbum(album);
		return new ResponseEntity<>(album, HttpStatus.OK);
	}

	@GetMapping(value = "/album/{id}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Album> getAlbumById(@PathVariable("id") long id) throws NotFoundException {
		log.info("Ricerca album con id = {}", id);
		Album result = service.findAlbum(id);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			throw new NotFoundException("Album non trovato");
	}

	@GetMapping(value = "/albums", produces = "application/json")
	@ResponseBody
	public List<Album> getAllAlbums() {
		log.info("Chiamata a getAllAlbums()");
		List<Album> result = service.findAllAlbums();
		if (result == null || result.isEmpty())
			throw new NoSuchElementException();
		return result;
	}
	
	@GetMapping(value = "/paged-albums", produces = "application/json")
	@ResponseBody
	public Page<Album> getAllAlbumsPaginated(Pageable pageable) {
		log.info("Chiamata a getAllAlbumsPaginated()");
		Page<Album> result = service.findAllAlbumsPaginated(pageable);
		if (result == null || !result.hasContent())
			throw new NoSuchElementException();
		return result;
	}

	@PutMapping(value = "/update", produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Album> updateAlbum(@RequestBody Album album) throws Exception {
		log.info("Modifica album con id = {}", album.getId());
		if ((Long) album.getId() == null || (Long) album.getId() == 0)
			throw new BadHttpRequest();
		Album result = service.updateAlbum(album);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Album> deleteAlbum(@PathVariable("id") long id) throws BadHttpRequest {
		log.info("Cancello album con id = {}", id);
		if ((Long) id == null)
			throw new BadHttpRequest();
		service.deleteAlbum(id);
		Album result = new Album();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/search", produces = "application/json")
	@ResponseBody
	public Page<Album> searchAlbum(@RequestParam(name = "titolo", defaultValue = "%") String titolo, 
			@RequestParam(name = "artista", defaultValue = "%") String artista, 
			@RequestParam(name = "anno", defaultValue = "%") String anno, 
			Pageable pageable) throws BadHttpRequest {
		log.info("Search album");
		if (titolo == null && artista == null && anno == null)
			throw new BadHttpRequest();
		return service.searchAlbumsPaginated(titolo, artista, anno, pageable);
	}

	@GetMapping(value = "/search-title", produces = "application/json")
	@ResponseBody
	public Page<Album> searchAlbum(@RequestParam(name = "titolo", defaultValue = "%") String titolo, 
			Pageable pageable) throws BadHttpRequest {
		log.info("Search album by title");
		if (titolo == null)
			throw new BadHttpRequest();
		titolo = "%" + titolo + "%";
		return service.searchAlbumByTitoloPaginated(titolo, pageable);
	}

	
	@GetMapping(value = "/", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Versione> getAPIVersion() {
		log.info("Application Info");
		return new ResponseEntity<>(versionRepo.appInfo(), HttpStatus.OK);
	}

}
