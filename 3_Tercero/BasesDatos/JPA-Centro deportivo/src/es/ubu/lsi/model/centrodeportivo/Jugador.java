package es.ubu.lsi.model.centrodeportivo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the JUGADOR database table.
 * 
 */
@Entity
@NamedQuery(name="Jugador.findAll", query="SELECT j FROM Jugador j")
public class Jugador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="JUGADOR_NIF_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JUGADOR_NIF_GENERATOR")
	private String nif;

	private String apellido;

	private String nombre;

	//bi-directional many-to-many association to Reserva
	@ManyToMany(mappedBy="jugadors")
	private Set<Reserva> reservas;

	public Jugador() {
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Reserva> getReservas() {
		return this.reservas;
	}

	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public String toString() {
		return "Jugador "+"[nif="+this.getNif()+", apellido="+this.getApellido()+", nombre="+this.getNombre()+"]";
		
	}

}