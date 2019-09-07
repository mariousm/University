package es.ubu.inf.edat.pr05;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clase Cola Mixta.
 * 
 * @author Mario Ubierna San Mames
 * @author Jorge Navarro Gonzalez
 *
 * @param <E>
 *            tipo generico
 */
public class ColaMixta<E> extends AbstractQueue {
	/**
	 * Inicio del nodo.
	 */
	private NodoMixto<E> inicio;
	/**
	 * Fin del nodo.
	 */
	private NodoMixto<E> fin;
	/**
	 * Numero de elementos.
	 */
	private int nelementos = 0;
	/**
	 * Tamaño del bloque.
	 */
	private int tambloque = 0;

	/**
	 * Constructor de la ColaMixta.
	 * 
	 * @param tambloque
	 *            tamaño del bloque
	 */
	public ColaMixta(int tambloque) {
		this.tambloque = tambloque;
		inicio = new NodoMixto<E>();
		fin = inicio;
	}

	/**
	 * Elimina todos los elementos de la cola.
	 */
	@Override
	public void clear() {
		inicio = new NodoMixto<E>();
		fin = inicio;
		this.nelementos = 0;
	}

	/**
	 * Inserta un elemento en la cola.
	 * 
	 * @return true
	 */
	@Override
	public boolean offer(Object arg0) {
		fin.getContenido().add((E) arg0);
		++nelementos;

		if (fin.getContenido().size() == this.tambloque) {
			NodoMixto<E> nodo = new NodoMixto<>();
			this.fin.set(nodo);
			this.fin = nodo;
		}
		return true;
	}

	/**
	 * Nos devuelve un elemento de la cabeza de la cola sin eliminarlo.
	 */
	@Override
	public Object peek() {
		E valorDevuelto;
		if (!inicio.getContenido().isEmpty()) {
			valorDevuelto = inicio.getContenido().get(0);
		} else {
			valorDevuelto = null;
		}
		return valorDevuelto;
	}

	/**
	 * Nos devuelve el elemento que le indicamos.
	 * 
	 * @param index
	 *            posicion del elemento que queremos que nos devuelva
	 * @return devuelto
	 */
	public E peek(int index) {

		if (index >= size()) {
			return null;
		}
		Iterator<E> it = this.iterator();
		int puntero = 0;
		E devuelto = null;

		while (it.hasNext()) {
			if (puntero == index) {
				devuelto = it.next();
				break;
			} else {
				puntero++;
				it.next();
			}
		}
		return devuelto;
	}

	/**
	 * Elimina el elemento de la cabecera de la cola y nos lo devuelve.
	 */
	@Override
	public Object poll() {
		E valorDevuelto;
		if (!inicio.getContenido().isEmpty()) {
			valorDevuelto = inicio.getContenido().get(0);
			inicio.getContenido().remove(0);
			--nelementos;
			if (inicio.getContenido().size() == 0) {
				inicio = inicio.getSiguiente();
			}
		} else {
			return null;
		}
		return valorDevuelto;
	}

	@Override
	/**
	 * Nos devuelve un nuevo iterador.
	 */
	public Iterator<E> iterator() {
		return new Iterador<E>();
	}

	/**
	 * Nos devuelve el numero de elementos.
	 */
	@Override
	public int size() {
		return nelementos;
	}

	/**
	 * Clase de NodoMixto.
	 * 
	 * @author Mario Ubierna San Mames
	 * @author Jorge Navarro Gonzalez
	 *
	 * @param <E>
	 */
	private class NodoMixto<E> {
		/**
		 * Nodo siguiente.
		 */
		private NodoMixto<E> siguiente = null;
		/**
		 * Contenido del nodo.
		 */
		private List<E> contenido;

		/**
		 * Constructor de NodoMixto.
		 */
		public NodoMixto() {
			contenido = new ArrayList<E>(tambloque);
		}

		/**
		 * Nos devuelve el contenido.
		 * 
		 * @return contenido
		 */
		public List<E> getContenido() {
			return contenido;
		}

		/**
		 * Establecemos el siguiente nodo.
		 * 
		 * @param nodo
		 */
		public void set(NodoMixto<E> nodo) {
			siguiente = nodo;
		}

		/**
		 * Nos devuelve el siguiente nodo.
		 * 
		 * @return siguiente
		 */
		public NodoMixto<E> getSiguiente() {
			return siguiente;
		}
	}

	/**
	 * Clase del Iterador.
	 * 
	 * @author Mario Ubierna San Mames
	 * @author Jorge Navarro Gonzalez
	 *
	 * @param <E>
	 */
	private class Iterador<E> implements Iterator<E> {

		/**
		 * Puntero del elemento.
		 */
		private int puntero;
		/**
		 * Elementos examinados.
		 */
		private int e_examinados;
		/**
		 * Numero de elementos.
		 */
		private int nelementos;
		/**
		 * Nodo actual.
		 */
		private ColaMixta<E>.NodoMixto<E> nodoMixto;

		/**
		 * Constructor de Iterador.
		 */
		public Iterador() {
			e_examinados = 0;
			puntero = -1;
			nelementos = size();
			nodoMixto = (ColaMixta<E>.NodoMixto<E>) inicio;

		}

		/**
		 * Devuelve verdadero si tiene mas elementos.
		 */
		@Override
		public boolean hasNext() {
			if (nelementos > e_examinados) {
				return true;
			}
			return false;
		}

		/**
		 * Nos devuelve el siguiente.
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			puntero++;

			E devuelto = nodoMixto.getContenido().get(puntero);

			if (puntero == nodoMixto.getContenido().size() - 1) {
				puntero = -1;
				nodoMixto = nodoMixto.getSiguiente();
			}
			e_examinados++;
			return devuelto;
		}

	}

}
