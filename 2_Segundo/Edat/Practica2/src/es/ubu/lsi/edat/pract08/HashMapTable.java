package es.ubu.lsi.edat.pract08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Clase HashMapTable.
 * 
 * @author Mario Ubierna San Mames
 * @author Jorge Navarro Gonzalez
 *
 * @param <R>
 *            tipo clave fila
 * @param <C>
 *            tipo clave columna
 * @param <V>
 *            tipo valor
 */
public class HashMapTable<R, C, V> implements Table<R, C, V> {
	/**
	 * Mapa de mapas.
	 */
	Map<R, Map<C, V>> mapa;
	/**
	 * Numero de elementos que hay en el mapa.
	 */
	int elementos;

	/**
	 * Constructor de la clase.
	 */
	public HashMapTable() {
		mapa = new HashMap<R, Map<C, V>>();
		this.elementos = 0;
	}

	/**
	 * Establecemos un elemento en una fila y en una columna.
	 * 
	 * @return el mapa actualizado con el valor
	 */
	@Override
	public V put(R row, C column, V value) {

		if (mapa.containsKey(row)) {
			if (!mapa.get(row).containsKey(column)) {
				elementos++;
			}
		} else if (!mapa.containsKey(row)) {
			mapa.put(row, new HashMap<C, V>());
			elementos++;
		}
		return mapa.get(row).put(column, value);
	}

	/**
	 * Elimina un elemento.
	 * 
	 * @return el mapa actulizado sin el valor
	 */
	@Override
	public V remove(R row, C column) {

		elementos--;
		return mapa.get(row).remove(column);
	}

	/**
	 * Nos devuelve un valor.
	 * 
	 * @return el valor que se encuentra en la fila y en la columna
	 */
	@Override
	public V get(Object row, Object column) {

		if (mapa.get(row) == null) {
			return null;
		} else {
			return mapa.get(row).get(column);
		}
	}

	/**
	 * Nos indica si la fila y columna se encuentran en el mapa.
	 * 
	 * @return boolean
	 */
	@Override
	public boolean containsKeys(Object row, Object column) {

		if (mapa.containsKey(row) && mapa.get(row).containsKey(column)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Nos indica si se encuentra el valor en el mapa.
	 * 
	 * @return boolean
	 */
	@Override
	public boolean containsValue(V value) {

		for (R a : mapa.keySet()) {
			if (mapa.get(a).containsValue(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Nos devuelve el contenido de la fila.
	 * 
	 * @return el contenido de la fila.
	 */
	@Override
	public Map<C, V> row(R rowKey) {

		return mapa.get(rowKey);
	}

	/**
	 * Nos devuelve el contenido de la columna.
	 * 
	 * @return el contenido de la columna en el mapa
	 */
	@Override
	public Map<R, V> column(C columnKey) {

		Map<R, V> map = new HashMap<R, V>();
		for (R a : mapa.keySet()) {
			if (mapa.get(a).containsKey(columnKey)) {
				map.put(a, mapa.get(a).get(columnKey));
			}
		}
		return map;
	}

	/**
	 * Clase Celda.
	 * 
	 * @author Mario Ubierna San Mames
	 * @author Jorge Navarro Gonzalez
	 *
	 * @param <R>
	 * @param <C>
	 * @param <V>
	 */
	protected class Celda<R, C, V> implements Cell<R, C, V> {
		/**
		 * Fila.
		 */
		R row;
		/**
		 * Columna
		 */
		C column;
		/**
		 * Valor.
		 */
		V value;

		/**
		 * Constructor de la clase Celda.
		 * 
		 * @param row
		 * @param column
		 * @param value
		 */
		public Celda(R row, C column, V value) {
			this.row = row;
			this.column = column;
			this.value = value;
		}

		/**
		 * Nos devuelve la fila.
		 */
		@Override
		public R getRowKey() {
			return row;
		}

		/**
		 * Nos devuelve la columna.
		 */
		@Override
		public C getColumnKey() {
			return column;
		}

		/**
		 * Nos devuelve el valor.
		 */
		@Override
		public V getValue() {
			return value;
		}

		/**
		 * Establecemos el valor.
		 */
		@Override
		public V setValue(V value) {
			V before = this.value;
			this.value = value;
			return before;
		}
	}

	/**
	 * Nos devuele una lista con las ternas del mapa.
	 * 
	 * @return lista
	 */
	@Override
	public Collection<es.ubu.lsi.edat.pract08.Table.Cell<R, C, V>> cellSet() {

		List<Cell<R, C, V>> lista = new ArrayList<>(this.size());
		Iterator<R> it = mapa.keySet().iterator();
		while (it.hasNext()) {
			R next = it.next();
			Iterator<C> it2 = mapa.get(next).keySet().iterator();
			while (it2.hasNext()) {
				C next2 = it2.next();
				lista.add(new Celda<R, C, V>(next, next2, mapa.get(next).get(next2)));
			}
		}
		return Collections.unmodifiableCollection(lista);
	}

	/**
	 * Nos devuelve el numero de elementos en el mapa.
	 * 
	 * @return elementos
	 */
	@Override
	public int size() {

		return elementos;
	}

	/**
	 * Nos indica si esta vacia.
	 * 
	 * @return boolean
	 */
	@Override
	public boolean isEmpty() {

		return elementos == 0;
	}

	/**
	 * Crea un nuevo mapa vacio.
	 */
	@Override
	public void clear() {

		mapa = new HashMap<R, Map<C, V>>();
		elementos = 0;
	}

}
