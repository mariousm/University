package es.ubu.lsi.model.centrodeportivo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the RESERVA database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Reserva.findAll", query="SELECT r FROM Reserva r"),
	@NamedQuery(name="Reserva.pistasLibres", query="SELECT r FROM Reserva r WHERE size(r.jugadors)<r.pistaBean.capacidad")
})
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReservaPK id;

	//bi-directional many-to-many association to Jugador
	@ManyToMany
	@JoinTable(
		name="RESERVAJUGADOR"
		, joinColumns={
			@JoinColumn(name="FECHA", referencedColumnName="FECHA"),
			@JoinColumn(name="HORA", referencedColumnName="HORA"),
			@JoinColumn(name="PISTA", referencedColumnName="PISTA")
			}
		, inverseJoinColumns={
			@JoinColumn(name="NIF")
			}
		)
	private Set<Jugador> jugadors;

	//bi-directional many-to-one association to Pista
	@MapsId("pista")
	@ManyToOne
	@JoinColumn(name="PISTA")
	private Pista pistaBean;

	public Reserva() {
	}

	public ReservaPK getId() {
		return this.id;
	}

	public void setId(ReservaPK id) {
		this.id = id;
	}

	public Set<Jugador> getJugadores() {
		return this.jugadors;
	}

	public void setJugadors(Set<Jugador> jugadors) {
		this.jugadors = jugadors;
	}

	public Pista getPistaBean() {
		return this.pistaBean;
	}

	public void setPistaBean(Pista pistaBean) {
		this.pistaBean = pistaBean;
	}
	
	public String toString() {
		return "Reserva [id=ReservaPK [fecha="+this.getId().getFecha().toString()+", hora="+this.getId().getHora()+", pista="+this.getPistaBean()+"]";      
	}

}