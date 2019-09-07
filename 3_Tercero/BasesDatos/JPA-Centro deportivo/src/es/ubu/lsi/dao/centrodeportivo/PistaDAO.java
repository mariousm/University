package es.ubu.lsi.dao.centrodeportivo;

import es.ubu.lsi.dao.JpaDAO;

import java.util.List;

import javax.persistence.EntityManager;

import es.ubu.lsi.model.centrodeportivo.Pista;

public class PistaDAO extends JpaDAO<Pista, Long> {

	public PistaDAO(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}
	
	public List<Pista> findAll(){
		return entityManager.createNamedQuery("Pista.findAll",Pista.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Pista> findAllGraph(){
		return entityManager.createNamedQuery("Pista.findAll").setHint("javax.persistence.fetchgraph", entityManager.getEntityGraph("Pista.findAllGraph")).getResultList();
	}
}
