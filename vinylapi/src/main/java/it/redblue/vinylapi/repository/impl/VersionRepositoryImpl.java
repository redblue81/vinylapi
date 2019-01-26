package it.redblue.vinylapi.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.redblue.vinylapi.model.Versione;
import it.redblue.vinylapi.repository.VersionRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class VersionRepositoryImpl implements VersionRepository { 
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Versione appInfo() {
		Versione result = null;
		result = (Versione) entityManager.createQuery("select v from Versione v where v.ID = (select max(v1.ID) from Versione v1)").getSingleResult(); 
		return result;
	}

}