package it.redblue.vinylapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import it.redblue.vinylapi.model.Image;
import it.redblue.vinylapi.service.ImageService;
import it.redblue.vinylapi.utils.Payload;
import it.redblue.vinylapi.utils.UploadFileResponse;

@RestController
@RequestMapping("/vinylcollection/api/images")
public class ImageController {
	
	private static final Logger log = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	ImageService imageService;

	@ResponseBody
	@PostMapping("/upload-image")
    public UploadFileResponse uploadFile(@RequestParam("image") MultipartFile file) {
		log.info("Upload singolo file");
        Image image = imageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(image.getId())
                .toUriString();

        return new UploadFileResponse(image.getId(), image.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

	@ResponseBody
    @PostMapping(value = "/upload-multiple-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("images[]") MultipartFile[] files) {
    	log.info("Upload multiplo");
    	   	
        return Arrays.asList(files)
        		.stream()
        		.map(file -> uploadFile(file))
        		.collect(Collectors.toList());
    }

	@ResponseBody
    @GetMapping("/download-file/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
    	log.info("Carico immagine da DB");
        Image image = imageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(new ByteArrayResource(image.getData()));
    }
	
	@ResponseBody
	@PostMapping(value = "/update-images", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> setAlbumForImages(@RequestBody Payload payload) {
		log.info("Aggiunta delle FK alle immmagini");
		Integer res = imageService.updateImages(payload.getAlbum(), payload.getImmagini());
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
