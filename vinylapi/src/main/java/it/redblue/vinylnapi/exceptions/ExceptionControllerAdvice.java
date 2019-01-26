package it.redblue.vinylnapi.exceptions;

import java.util.NoSuchElementException;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	private Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
	
	@ExceptionHandler({NotFoundException.class, NoSuchElementException.class, 
		NoResultException.class, NoHandlerFoundException.class})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	protected ResponseEntity<ErrorResponse> handleNoDataException(Exception ex, WebRequest req) {
		log.info("Eccezione sollevata: {}", ex.getClass());
		ErrorResponse response = new ErrorResponse();
		response.setErrorCode(HttpStatus.NOT_FOUND.value());
		if (ex instanceof NotFoundException || ex instanceof NoResultException || ex instanceof NoSuchElementException)
			response.setMessage("Nessun record trovato.");
		else if (ex instanceof NoHandlerFoundException)
			response.setMessage("La pagina richiesta non esiste.");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({BadHttpRequest.class, IllegalArgumentException.class, MissingServletRequestParameterException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	protected ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex, WebRequest req) {
		log.info("Eccezione sollevata: {}", ex.getClass());
		ErrorResponse response = new ErrorResponse();
		response.setErrorCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage(ex.getMessage() != null && ex.getMessage().isEmpty() ? "Dati mancanti" : ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
}
