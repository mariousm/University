package es.ubu.lsi.model.centrodeportivo;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the PISTA database table.
 * 
 */
@Entity
@NamedQuery(name="Pista.findAll", query="SELECT p FROM Pista p")
@NamedEntityGraph(
		name = "Pista.findAllGraph",
		attributeNodes = {
				@NamedAttributeNode (value = "reservas", subgraph = "Reserva.findAllGraph")
		},
		subgraphs = {
				@NamedSubgraph (
						name = "Reserva.findAllGraph",
						attributeNodes = {
								@NamedAttributeNode ("jugadors")
						}
						)
		}
		)
public class Pista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PISTA_NRO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PISTA_NRO_GENERATOR")
	private long nro;

	private int capacidad;

	private String descripcion;

	//bi-directional many-to-one association to Reserva
	@OneToMany(mappedBy="pistaBean")
	private Set<Reserva> reservas;

	public Pista() {
	}

	public long getNro() {
		return this.nro;
	}

	public void setNro(long nro) {
		this.nro = nro;
	}

	public int getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Reserva> getReservas() {
		return this.reservas;
	}

	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}

	public Reserva addReserva(Reserva reserva) {
		getReservas().add(reserva);
		reserva.setPistaBean(this);

		return reserva;
	}

	public Reserva removeReserva(Reserva reserva) {
		getReservas().remove(reserva);
		reserva.setPistaBean(null);

		return reserva;
	}
	
	public String toString() {
		return "Pista [nro="+this.getNro()+", capacidad="+this.getCapacidad()+", descripcion="+this.getDescripcion()+"]";
	}

}