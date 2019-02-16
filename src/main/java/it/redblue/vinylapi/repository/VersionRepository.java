package it.redblue.vinylapi.repository;

import org.springframework.stereotype.Repository;

import it.redblue.vinylapi.model.Versione;

@Repository
public interface VersionRepository {
	
	public Versione appInfo();

}
