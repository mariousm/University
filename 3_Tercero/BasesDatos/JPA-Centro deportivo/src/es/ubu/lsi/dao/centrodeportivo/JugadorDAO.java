package es.ubu.lsi.dao.centrodeportivo;

import es.ubu.lsi.dao.JpaDAO;

import java.util.List;

import javax.persistence.EntityManager;

import es.ubu.lsi.model.centrodeportivo.Jugador;

public class JugadorDAO extends JpaDAO<Jugador, String> {

	public JugadorDAO(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

	public List<Jugador> findAll(){
		return entityManager.createNamedQuery("Jugador.findAll",Jugador.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Jugador> findAllGraph(){
		return entityManager.createNamedQuery("Jugador.findAll").setHint("javax.persistence.fetchgraph"
				, entityManager.getEntityGraph("Jugador.findAllGraph")).getResultList();
	}
}
