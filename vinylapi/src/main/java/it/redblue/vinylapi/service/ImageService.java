package it.redblue.vinylapi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.redblue.vinylapi.model.Image;

public interface ImageService {
	
	public Image storeFile(MultipartFile file);
	public Image getFile(String fileId);
	public int updateImages(String albumId, List<String> ids);

}
