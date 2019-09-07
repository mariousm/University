package es.ubu.lsi.service.centrodeportivo;

/**
 * Error code.
 * 
 * Listado de posibles errores que se pueden producir.
 * 
 * @author <a href="mailto:jmaudes@ubu.es">Jesús Maudes</a>
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @author <a href="mailto:mmabad@ubu.es">Mario Martínez</a>
 * @since 1.0
 *
 */
public enum ReservationError {
	
	// If we have more error messages, add new values to the the end of enum type...
	ERROR_IN_DATE("Fecha incorrecta"),
	ERROR_IN_HOUR("Hora incorrecta"),
	NOT_EXIST_RESERVATION("No existe la reserva"),
	NOT_EXIST_COURT("No existe pista"),
	NOT_EXIST_PLAYER("No existe jugador"),
	RESERVATION_EXIST("La reserva de pista ya existe"),
	FULL_RESERVATION("No hay hueco para más jugadores en la pista"),
	PLAYER_IN_RESERVATION("El jugador ya está asignado a ese partido"),	
	PLAYER_IN_OTHER_RESERVATION("El jugador ya está asignado a otro partido en esa fecha y hora");	
	
	/** Text. */
	private String text;
	
	/** Constructor. */
	private ReservationError(String text) {
		this.text = text;
	}

	/**
	 * Gets text.
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}
}
