package es.ubu.lsi.service.centrodeportivo;

import es.ubu.lsi.service.PersistenceException;

/**
 * Reservation exception.
 * 
 * @author <a href="mailto:jmaudes@ubu.es">Jesús Maudes</a>
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @author <a href="mailto:mmabad@ubu.es">Mario Martínez</a>
 * @since 1.0
 *
 */
public class ReservationException extends PersistenceException {

	/** Error code. */
	private ReservationError error;

	/** Default. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param text
	 *            text
	 */
	public ReservationException(String text) {
		super(text);
	}

	/**
	 * Constructor.
	 * 
	 * @param error
	 *            error code
	 */
	public ReservationException(ReservationError error) {
		super(error.getText());
		setError(error);
	}

	/**
	 * Constructor.
	 * 
	 * @param text
	 *            text
	 * @param ex
	 *            exception
	 */
	public ReservationException(String text, Exception ex) {
		super(text, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param text
	 *            text
	 * @param error
	 *            error code
	 * @param ex
	 *            exception
	 */
	public ReservationException(String text, ReservationError error, Exception ex) {
		super(text, ex);
		setError(error);
	}

	/**
	 * Constructor.
	 * 
	 * @param text
	 *            text
	 * @param error
	 *            error code
	 */
	public ReservationException(String text, ReservationError error) {
		super(text);
		setError(error);
	}

	/**
	 * Constructor.
	 * 
	 * @param error
	 *            error code
	 * @param ex
	 *            exception
	 */
	public ReservationException(ReservationError error, Exception ex) {
		super(ex);
		setError(error);
	}

	/**
	 * Gets error code.
	 * 
	 * @return error code
	 * @return
	 */
	public ReservationError getError() {
		return error;
	}

	/**
	 * Sets error code.
	 * 
	 * @param error
	 *            error
	 */
	private void setError(ReservationError error) {
		this.error = error;
	}
}
