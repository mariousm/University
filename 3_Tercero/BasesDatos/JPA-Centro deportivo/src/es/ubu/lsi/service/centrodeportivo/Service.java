package es.ubu.lsi.service.centrodeportivo;

import java.util.Date;
import java.util.List;

import es.ubu.lsi.model.centrodeportivo.Pista;
import es.ubu.lsi.model.centrodeportivo.Reserva;
import es.ubu.lsi.service.PersistenceException;

/**
 * Transaction service.
 * 
 * @author <a href="mailto:jmaudes@ubu.es">Jesús Maudes</a>
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @author <a href="mailto:mmabad@ubu.es">Mario Martínez</a>
 * @since 1.0
 *
 */
public interface Service {
		
	/**
	 * Realiza una nueva reserva de la pista en la fecha y hora indicada.
	 * 
	 * @param fecha fecha
	 * @param hora hora
	 * @param pista pista
	 * @throws PersistenceException si se produce un error
	 * @see es.ubu.lsi.service.centrodeportivo.ReservationError
	 */
	public void insertarReserva(Date fecha, int hora, long pista) throws PersistenceException;
		
	/**
	 * Asigna un jugador a una reserva ya existente.
	 * 
	 * @param fecha fecha
	 * @param hora hora
	 * @param pista pista 
	 * @param jugador jugador
	 * @throws PersistenceException si se produce un error
	 * @see es.ubu.lsi.service.centrodeportivo.ReservationError
	 */
	public void asignarJugadorAReserva(Date fecha, int hora, long pista, String jugador) throws PersistenceException;
		
	
	/**
	 * Borra todas las reservas y sus correspondientes asignaciones de jugadores 
	 * en una pista para una fecha concreta.
	 * 
	 * @param fecha fecha 
	 * @param pista pista
	 * @throws PersistenceException si se produce un error
	 * @see es.ubu.lsi.service.centrodeportivo.ReservationError
	 */
	public void borrarReservasEnPista(Date fecha, long pista) throws PersistenceException;
	
	/**
	 * Consulta las pistas. En este caso en particular es importante recuperar 
	 * toda la información vinculada a las pistas de sus reservas y jugadores 
	 * para su visualización posterior.
	 * 
	 * @return pistas
	 * @throws PersistenceException si se produce un error
	 */
	public List<Pista> consultarPistas() throws PersistenceException;
	
	/**
	 * Consulta reservas con hueco libre. Recupera las reservas que tienen
	 * capacidad disponible para añadir como mínimo un jugador más.
	 * 
	 * @return reservas con plaza libre
	 * @throws PersistenceException si se produce un error
	 */
	public List<Reserva> consultarReservaDePistasLibres() throws PersistenceException;
}
