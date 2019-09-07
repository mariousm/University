package es.ubu.lsi.model.centrodeportivo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the RESERVA database table.
 * 
 */
@Embeddable
public class ReservaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private java.util.Date fecha;

	private int hora;

	@Column(insertable=false, updatable=false)
	private long pista;
	
	public ReservaPK() {
		
	}

	public ReservaPK(java.util.Date fecha, int hora, long pista) {
		this.fecha = fecha;
		this.hora = hora;
		this.pista = pista;
	}
	public java.util.Date getFecha() {
		return this.fecha;
	}
	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}
	public long getHora() {
		return this.hora;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
	public long getPista() {
		return this.pista;
	}
	public void setPista(long pista) {
		this.pista = pista;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ReservaPK)) {
			return false;
		}
		ReservaPK castOther = (ReservaPK)other;
		return 
			this.fecha.equals(castOther.fecha)
			&& (this.hora == castOther.hora)
			&& (this.pista == castOther.pista);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fecha.hashCode();
		hash = hash * prime + ((int) (this.hora ^ (this.hora >>> 32)));
		hash = hash * prime + ((int) (this.pista ^ (this.pista >>> 32)));
		
		return hash;
	}
}