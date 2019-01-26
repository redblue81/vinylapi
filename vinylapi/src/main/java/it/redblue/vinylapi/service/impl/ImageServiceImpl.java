package it.redblue.vinylapi.service.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import it.redblue.vinylapi.model.Image;
import it.redblue.vinylapi.repository.ImageRepository;
import it.redblue.vinylapi.service.ImageService;
import it.redblue.vinylnapi.exceptions.FileStorageException;
import it.redblue.vinylnapi.exceptions.MyFileNotFoundException;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
	
	
	private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);
	
	@Autowired
    private ImageRepository repository;
	
	@PersistenceContext
	private EntityManager em;

	@Override
    public Image storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Nome del file non valido: " + fileName);
            }

            Image image = new Image(fileName, file.getContentType(), file.getBytes());

            return repository.save(image);
        } catch (IOException ex) {
            throw new FileStorageException("Impossibile salvare il file " + fileName, ex);
        }
    }

	@Override
    public Image getFile(String fileId) {
        return repository.findById(fileId).orElseThrow(() -> new MyFileNotFoundException("File non trovato. ID = " + fileId));
    }

	@Override
	public int updateImages(String albumId, List<String> ids) {
		log.info("Aggancio delle immagini all'album con id = {}", albumId);
		
		int result;
		
		String paraAlbum = "paraAlbum";
		String paraIds = "paraIds";
		
		String sql = "update images set fk_album = :" + paraAlbum + " where id in (:" + paraIds + ")";
		
		Query q = em.createNativeQuery(sql);
		
		q.setParameter(paraAlbum, albumId);
		q.setParameter(paraIds, ids);
		
		result = q.executeUpdate();
		log.info("Modificate {} righe della tabella images", result);
		return result;
		
	}

}
