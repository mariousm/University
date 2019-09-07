package es.ubu.lsi.dao;

/**
 * DAO.
 * 
 * @param <E> entity type
 * @param <K> key type
 * @author <a href="mailto:jmaudes@ubu.es">Jes�s Maudes</a>
 * @author <a href="mailto:rmartico@ubu.es">Ra�l Marticorena</a>
 * @author <a href="mailto:mmabad@ubu.es">Mario Mart�nez</a>
 * @since 1.0
 */
public interface DAO<E,K> {
	/** 
	 * Persist. 
	 *  
	 * @param entity entity
	 */
	void persist(E entity);

	/**
	 * Remove.
	 * 
	 * @param entity entity
	 */
	void remove(E entity);
	
	/**
	 * Find by primary key.
	 * 
	 * @param id value
	 * @return entity
	 */
	E findById(K id);
}