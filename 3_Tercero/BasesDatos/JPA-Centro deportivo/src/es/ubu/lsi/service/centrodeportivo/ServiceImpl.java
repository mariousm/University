package es.ubu.lsi.service.centrodeportivo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import es.ubu.lsi.dao.centrodeportivo.JugadorDAO;
import es.ubu.lsi.dao.centrodeportivo.PistaDAO;
import es.ubu.lsi.dao.centrodeportivo.ReservaDAO;
import es.ubu.lsi.model.centrodeportivo.Jugador;
import es.ubu.lsi.model.centrodeportivo.Pista;
import es.ubu.lsi.model.centrodeportivo.Reserva;
import es.ubu.lsi.model.centrodeportivo.ReservaPK;
import es.ubu.lsi.service.PersistenceException;
import es.ubu.lsi.service.PersistenceService;

public class ServiceImpl extends PersistenceService implements Service {

	@Override
	public void insertarReserva(Date fecha, int hora, long pista) throws PersistenceException {
		EntityManager entityManager = null;

		try {
			entityManager = createSession();
			this.beginTransaction(entityManager);

			PistaDAO pistaDAO = new PistaDAO(entityManager);
			Pista pista_dos = pistaDAO.findById(pista);

			ReservaDAO reservaDAO = new ReservaDAO(entityManager);

			Reserva reserva = new Reserva();
			ReservaPK reservaPK = null;

			if (pista_dos != null) {
				if (fecha == null) {
					throw new ReservationException(ReservationError.ERROR_IN_DATE);
				}
				if (hora < 0 || hora > 23) {
					throw new ReservationException(ReservationError.ERROR_IN_HOUR);
				}
				reservaPK = new ReservaPK(fecha, hora, pista);
				if (reservaDAO.findById(reservaPK) == null) {
					reserva.setId(reservaPK);
					reserva.setPistaBean(pista_dos);

					reservaDAO.persist(reserva);
				} else {
					throw new ReservationException(ReservationError.RESERVATION_EXIST);
				}

			} else {
				throw new ReservationException(ReservationError.NOT_EXIST_COURT);
			}
			this.commitTransaction(entityManager);
		} catch (Exception e) {
			this.rollbackTransaction(entityManager);
			throw e;
		} finally {
			if (entityManager != null) {
				this.close(entityManager);
			}
		}
	}

	@Override
	public void asignarJugadorAReserva(Date fecha, int hora, long pista, String jugador) throws PersistenceException {

		EntityManager entityManager = null;
		try {
			entityManager = this.createSession();
			this.beginTransaction(entityManager);

			JugadorDAO jugadorDAO = new JugadorDAO(entityManager);
			Jugador jugador_dos = jugadorDAO.findById(jugador);

			ReservaDAO reservaDAO = new ReservaDAO(entityManager);
			ReservaPK reservaPK = null;

			if (fecha == null) {
				throw new ReservationException(ReservationError.ERROR_IN_DATE);
			}
			if (hora < 0 || hora > 24) {
				throw new ReservationException(ReservationError.ERROR_IN_HOUR);
			}
			if (jugador_dos == null) {
				throw new ReservationException(ReservationError.NOT_EXIST_PLAYER);
			}

			// Existe la Reserva
			reservaPK = new ReservaPK(fecha, hora, pista);
			Reserva reserva = reservaDAO.findById(reservaPK);

			if (reserva == null) {
				throw new ReservationException(ReservationError.NOT_EXIST_RESERVATION);
			}

			// El jugador ya esta asignado a ese partido
			Set<Jugador> jugadores = reserva.getJugadores();
			if (jugadores.contains(jugador_dos)) {
				throw new ReservationException(ReservationError.PLAYER_IN_RESERVATION);
			}

			// Jugador esta asignado a otro partido en esa fecha y hora
			Set<Reserva> reservasJugador = jugador_dos.getReservas();
			for (Reserva r : reservasJugador) {
				if (r.getId().getFecha() == fecha && r.getId().getHora() == hora) {
					throw new ReservationException(ReservationError.PLAYER_IN_OTHER_RESERVATION);
				}
			}

			// No hay hueco en la pista
			if (reserva.getJugadores().size() >= reserva.getPistaBean().getCapacidad()) {
				throw new ReservationException(ReservationError.FULL_RESERVATION);
			}

			// HACEMOS LA ASIGNACION
			jugadores.add(jugador_dos);
			reserva.setJugadors(jugadores);

			reservasJugador.add(reserva);
			jugador_dos.setReservas(reservasJugador);

			this.commitTransaction(entityManager);
		} catch (Exception e) {
			this.rollbackTransaction(entityManager);
			throw e;
		} finally {
			if (entityManager != null) {
				this.close(entityManager);
			}
		}
	}

	@Override
	public void borrarReservasEnPista(Date fecha, long pista) throws PersistenceException {
		
		EntityManager entityManager = null;
		try {
			entityManager = createSession();
			this.beginTransaction(entityManager);
			
			PistaDAO pistaDAO = new PistaDAO(entityManager);
			Pista pista_dos = pistaDAO.findById(pista);
			if (pista_dos == null) {
				throw new ReservationException(ReservationError.NOT_EXIST_COURT);
			}
			
			ReservaDAO reservaDAO = new ReservaDAO(entityManager);
			ReservaPK reservaPK = null;

			List<Reserva> listaR = new ArrayList<Reserva>();
			
			if (fecha == null) {
				throw new ReservationException(ReservationError.ERROR_IN_DATE);
			}
			
			for (int i = 0; i<24; ++i) {
				reservaPK = new ReservaPK(fecha,i,pista);
				Reserva reserva = reservaDAO.findById(reservaPK);				
				if (reserva != null) {
					listaR.add(reserva);
				}
			}
			
			for (Reserva r : listaR) {
				r.setJugadors(null);
				reservaDAO.remove(r);
			}

			this.commitTransaction(entityManager);
		} catch (Exception e) {
			this.rollbackTransaction(entityManager);
			throw e;
		} finally {
			if (entityManager != null) {
				this.close(entityManager);
			}
		}
	}

	@Override
	public List<Pista> consultarPistas() throws PersistenceException {
		EntityManager entityManager = null;
		List<Pista> pistas = new ArrayList<Pista>();
		try {
			entityManager = createSession();
			this.beginTransaction(entityManager);
			
			PistaDAO pistaDAO = new PistaDAO(entityManager);
			pistas = pistaDAO.findAllGraph();

			this.commitTransaction(entityManager);
			return pistas;
		} catch (Exception e) {
			this.rollbackTransaction(entityManager);
			throw e;
		} finally {
			if (entityManager != null) {
				this.close(entityManager);
			}
		}
	}
	
	@Override
	public List<Reserva> consultarReservaDePistasLibres() throws PersistenceException {
		EntityManager entityManager = null;
		
		try {
			entityManager = createSession();
			this.beginTransaction(entityManager);
			
			List<Reserva> reservasLibres = new ArrayList<Reserva>();
			ReservaDAO reservasDAO = new ReservaDAO(entityManager);
			List<Reserva> reservas = reservasDAO.findAll();
			
			for (Reserva r : reservas) {
				if (r.getJugadores().size() < r.getPistaBean().getCapacidad()) {
					reservasLibres.add(r);
				}
			}

			this.commitTransaction(entityManager);
			return reservasLibres;
		} catch (Exception e) {
			this.rollbackTransaction(entityManager);
			throw e;
		} finally {
			if (entityManager != null) {
				this.close(entityManager);
			}
		}
	}

	public List<Reserva> consultarReservaDePistasLibres2() throws PersistenceException {
		EntityManager entityManager = null;
		
		try {
			entityManager = createSession();
			this.beginTransaction(entityManager);
			
			List<Reserva> reservasLibres = new ArrayList<Reserva>();
			ReservaDAO reservasDAO = new ReservaDAO(entityManager);
			reservasLibres = reservasDAO.pistasLibres();

			this.commitTransaction(entityManager);
			return reservasLibres;
		} catch (Exception e) {
			this.rollbackTransaction(entityManager);
			throw e;
		} finally {
			if (entityManager != null) {
				this.close(entityManager);
			}
		}
	}

}
