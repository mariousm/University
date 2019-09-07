package es.ubu.lsi.dao.centrodeportivo;

import es.ubu.lsi.dao.JpaDAO;

import java.util.List;

import javax.persistence.EntityManager;

import es.ubu.lsi.model.centrodeportivo.Reserva;
import es.ubu.lsi.model.centrodeportivo.ReservaPK;

public class ReservaDAO extends JpaDAO<Reserva, ReservaPK> {

	public ReservaDAO(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}
	
	public List<Reserva> findAll(){
		return entityManager.createNamedQuery("Reserva.findAll",Reserva.class).getResultList();
	}
	
	public List<Reserva> pistasLibres(){
		return entityManager.createNamedQuery("Reserva.pistasLibres",Reserva.class).getResultList();
	}
}
