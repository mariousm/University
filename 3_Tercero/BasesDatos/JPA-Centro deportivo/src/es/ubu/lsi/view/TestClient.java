package es.ubu.lsi.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.lsi.model.centrodeportivo.Jugador;
import es.ubu.lsi.model.centrodeportivo.Pista;
import es.ubu.lsi.model.centrodeportivo.Reserva;
import es.ubu.lsi.model.centrodeportivo.ReservaPK;
import es.ubu.lsi.service.PersistenceException;
import es.ubu.lsi.service.PersistenceFactorySingleton;
import es.ubu.lsi.service.centrodeportivo.ReservationError;
import es.ubu.lsi.service.centrodeportivo.ReservationException;
import es.ubu.lsi.service.centrodeportivo.Service;
import es.ubu.lsi.service.centrodeportivo.ServiceImpl;
import es.ubu.lsi.view.util.ExecuteScript;
import es.ubu.lsi.view.util.PoolDeConexiones;

/**
 * Test client.
 * 
 * @author <a href="mailto:jmaudes@ubu.es">Jesús Maudes</a>
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @author <a href="mailto:mmabad@ubu.es">Mario Martínez</a>
 * @since 1.0
 */
public class TestClient {

	/** Logger. */
	private static final Logger logger = LoggerFactory.getLogger(TestClient.class);

	/** Connection pool. */
	private static PoolDeConexiones pool;

	/** Path. */
	private static final String SCRIPT_PATH = "sql/";

	/** Simple date format. */
	private static SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Main.
	 * 
	 * @param args
	 *            arguments.
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Iniciando...");
			init();
			System.out.println("Probando el servicio...");
			testService();
			System.out.println("FIN.............");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error grave en la aplicación {}", ex.getMessage());
		}
	}

	/**
	 * Init pool.
	 */
	static public void init() {
		try {
			// Acuerdate de q la primera vez tienes que crear el .bindings con:
			// PoolDeConexiones.reconfigurarPool();
			// Inicializacion de Pool
			pool = PoolDeConexiones.getInstance();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Create tables.
	 */
	static public void createTables() {
		ExecuteScript.run(SCRIPT_PATH + "centro_deportivo.sql");
	}

	/**
	 * Test service using JDBC and JPA.
	 */
	static void testService() throws Exception {
		createTables();
		Service implService = null;
		try {
			// JPA Service
			implService = new ServiceImpl();
			System.out.println("Framework y servicio iniciado...");

			// insertar reserva la pista 1, para el 01/01/2018 a las 18:00
			insertarReservaCorrecta(implService);

			// insertar la reserva anterior otra vez, no debería dejarnos...
			noDejaInsertarUnaReservaExistente(implService);

			// insertar al jugador "11111111E" en una pista ya completa el 03/03/2018 a las
			// 15:00 en la pista 1, no debería dejarnos
			noDejaInsertarReservaEnPistaCompleta(implService);

			// borrar la reserva de la pista 1 para la fecha 03/03/2018
			borrarReservasEnFechaYPista(implService);
			
			// asignar reserva de jugador correcto en una pista con hueco, completándola
			asignarReservaEnPistaLibre(implService);
			
			// comprobar las reservas que tienen huecos libres
			reservasConHuecos(implService);

			// comprueba que la consulta de pistas carga todos los datos
			consultarPistasUsandoGrafo(implService);

		} catch (Exception e) { // for testing code...
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			pool = null;
			// Release the resources of the persistence framework
			PersistenceFactorySingleton.close();
		} // finally
	} // testClient

	/**
	 * Borra la reserva y las asignaciones de jugadores asociados a dicha reserva.
	 * 
	 * @param implService
	 *            implementación del servicio
	 * @throws Exception
	 *             error en test
	 */
	private static void borrarReservasEnFechaYPista(Service implService) throws Exception {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			StringBuilder resultado;
			String cadenaEsperada;
			// Test
			implService.borrarReservasEnPista(dateformat.parse("03/03/2018"), 1);
			con = pool.getConnection();

			// Comprobar si las reservas se han borrado
			st = con.createStatement();
			rs = st.executeQuery("SELECT fecha||' '||hora||' '||pista||' '||nif"
					+ " FROM RESERVA NATURAL JOIN RESERVAJUGADOR ORDER BY fecha, hora, pista, nif");

			resultado = new StringBuilder();
			while (rs.next()) {
				resultado.append(rs.getString(1));
			}

			logger.debug(resultado.toString());
			cadenaEsperada = "03/03/18 18 2 11111111E03/03/18 18 4 11111111F";
			logger.debug(cadenaEsperada);

			logger.error("Cadena obtenida: {}", resultado);

			System.out.print("Borrado de la reserva de la pista en la fecha indicada");
			if (cadenaEsperada.equals(resultado.toString())) {
				System.out.println(" OK");
			} else {
				System.out.println(" ERROR");
			}
			con.commit();
		} catch (Exception ex) {
			logger.error("ERROR grave en test. " + ex.getLocalizedMessage());
			con.rollback();
			throw ex;
		} finally {
			cerrarRecursos(con, st, rs);
		}
	}

	/**
	 * Intento erróneo de insertar reserva en pista ya completa.
	 * 
	 * @param implService
	 *            implementación del servicio
	 * @throws PersistenceException
	 *             error en persistencia
	 * @throws ParseException
	 *             error en generación de fechas
	 */
	private static void noDejaInsertarReservaEnPistaCompleta(Service implService)
			throws PersistenceException, ParseException {
		// Third test...
		// Intenta asignar una reserva posible (existe la reserva, la pista y jugador) a
		// una pista ya completa
		try {
			implService.asignarJugadorAReserva(dateformat.parse("03/03/2018"), 15, 1, "11111111E");
			System.out.println("NO se da cuenta de que la pista ya está completa ERROR");
		} catch (ReservationException e) {
			System.out.print("Se da cuenta de que la pista está completa");
			if (e.getError() == ReservationError.FULL_RESERVATION) {
				System.out.println(" OK");
			} else {
				System.out.println(" ERROR. Error mal identificado: " + e.getError().toString());
			}
		} // catch
	}

	/**
	 * Intento erróneo de insertar una reserva ya existente.
	 * 
	 * @param implService
	 *            implementación del servicio
	 * @throws PersistenceException
	 *             error en persistencia
	 * @throws ParseException
	 *             error en generación de fechas
	 */
	private static void noDejaInsertarUnaReservaExistente(Service implService)
			throws PersistenceException, ParseException {
		// Begin second test...
		try {
			implService.insertarReserva(dateformat.parse("01/01/2018"), 18, 1); // intentamos repetir la reserva
			System.out.println("NO se da cuenta de que la reserva ya existe ERROR");
		} catch (ReservationException e) {
			System.out.print("Se da cuenta de que existe ya la reserva");
			if (e.getError() == ReservationError.RESERVATION_EXIST) {
				System.out.println(" OK");
			} else {
				System.err.println(" ERROR. Error mal identificado: " + e.getError().toString());
			}
		} // catch
	}

	/**
	 * Inserta una reserva correcta.
	 * 
	 * @param implService
	 *            implementación del servicio
	 * @throws Exception
	 *             error en test
	 */
	private static void insertarReservaCorrecta(Service implService) throws Exception {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			implService.insertarReserva(dateformat.parse("01/01/2018"), 18, 1); // reserva la pista 1, para el 1 de
																				// enero a las 18:00

			con = pool.getConnection();

			// Comprobar si la reserva se ha realizado
			st = con.createStatement();
			rs = st.executeQuery("SELECT fecha||hora||pista " + "FROM RESERVA ORDER BY fecha, hora, pista");

			StringBuilder resultado = new StringBuilder();
			while (rs.next()) {
				resultado.append(rs.getString(1));
			}
			logger.debug(resultado.toString());
			String cadenaEsperada = "01/01/1818103/03/1815103/03/1818203/03/18184";

			System.out.print("Inserción de la nueva reserva");
			if (cadenaEsperada.equals(resultado.toString())) {
				System.out.println(" OK");
			} else {
				System.out.println(" ERROR");
			}
			con.commit();
		} catch (Exception ex) {
			logger.error("ERROR grave en test. " + ex.getLocalizedMessage());
			con.rollback();
			throw ex;
		} finally {
			cerrarRecursos(con, st, rs);
		}
	}

	/**
	 * Asigna un jugador a una reserva libre.
	 * 
	 * @param implService
	 *            implementación del servicio
	 * @throws Exception
	 *             error en test
	 */
	private static void asignarReservaEnPistaLibre(Service implService) throws Exception {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			implService.asignarJugadorAReserva(dateformat.parse("03/03/2018"), 18, 4, "11111111D");

			con = pool.getConnection();

			// Comprobar si la reserva se ha añadido
			st = con.createStatement();
			rs = st.executeQuery("SELECT fecha||' '||hora||' '||pista||' '||nif "
					+ "FROM RESERVAJUGADOR ORDER BY fecha, hora, pista, nif");

			StringBuilder resultado = new StringBuilder();
			while (rs.next()) {
				resultado.append(rs.getString(1));
			}
			logger.debug(resultado.toString());
			String cadenaEsperada = "03/03/18 18 2 11111111E03/03/18 18 4 11111111D03/03/18 18 4 11111111F";

			System.out.print("Asignación de la nueva reserva para el jugador 11111111D");
			if (cadenaEsperada.equals(resultado.toString())) {
				System.out.println(" OK");
			} else {
				System.out.println(" ERROR");
			}
			con.commit();
		} catch (Exception ex) {
			logger.error("ERROR grave en test. " + ex.getLocalizedMessage());
			ex.printStackTrace();
			con.rollback();
			throw ex;
		} finally {
			cerrarRecursos(con, st, rs);
		}
	}

	/**
	 * Prueba de la consulta de reservas con hueco libre.
	 * 
	 * @param implService
	 *            implementación del servicio
	 */
	private static void reservasConHuecos(Service implService) {
		final int NUM_RESERVAS_CON_HUECO = 2;
		try {
			List<Reserva> reservas = implService.consultarReservaDePistasLibres();

			Date fecha1 = dateformat.parse("01/01/2018");
			Date fecha2 = dateformat.parse("03/03/2018");

			if (reservas.size() != NUM_RESERVAS_CON_HUECO) {
				System.out.println("Número de reservas con plazas libre incorrecto: " + reservas.size() + ". ERROR");
				for (Reserva reserva : reservas) {
					System.out.println(reserva.toString());
				}
			} else {
				ReservaPK reserva1 = reservas.get(0).getId();
				ReservaPK reserva2 = reservas.get(1).getId();

				ReservaPK reserva1Esperada = new ReservaPK(fecha1, 18, 1);
				ReservaPK reserva2Esperada = new ReservaPK(fecha2, 18, 2);

				System.out.print("Comparando el resutado de la consulta ");
				boolean flag = true;
				if (!reserva1.equals(reserva1Esperada)) {
					System.out.println(
							" ERROR con " + reserva1Esperada.toString() + " comparando con " + reserva1.toString());
					flag = false;
				}
				if (!reserva2.equals(reserva2Esperada)) {
					System.out.println(
							" ERROR con " + reserva2Esperada.toString() + " comparando con " + reserva2.toString());
					flag = false;
				}
				if (flag) {
					System.out.println(" OK");
				}
			}
		} catch (ParseException ex) {
			throw new RuntimeException("Error con conversión de fecha", ex);
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción de consultas de reservas con pista libre con JPA: "
					+ ex.getLocalizedMessage());
			throw new RuntimeException("Error en consulta de huecos", ex);
		}
	}

	/**
	 * Prueba consulta de pistas, cargando datos completos desde un grafo de
	 * entidades.
	 * 
	 * @param implService
	 *            implementación del servicio
	 */
	private static void consultarPistasUsandoGrafo(Service implService) {
		try {
			List<Pista> pistas = implService.consultarPistas();
			System.out.println("Información completa...");
			for (Pista pista : pistas) {
				System.out.println(pista.toString());
				Set<Reserva> reservas = pista.getReservas();
				for (Reserva reserva : reservas) {
					System.out.println("\t" + reserva.toString());
					Set<Jugador> jugadores = reserva.getJugadores();
					for (Jugador jugador : jugadores) {
						System.out.println("\t\t" + jugador.toString());
					}
				}
			}
			System.out.println("Sin excepciones en la consulta completa y acceso posterior OK");
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción de consultas de pistas con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error en consulta de pistas", ex);
		}
	}

	/**
	 * Cierra recursos de la transacción.
	 * 
	 * @param con
	 *            conexión
	 * @param st
	 *            sentencia
	 * @param rs
	 *            conjunto de datos
	 * @throws SQLException
	 *             si se produce cualquier error SQL
	 */
	private static void cerrarRecursos(Connection con, Statement st, ResultSet rs) throws SQLException {
		if (rs != null && !rs.isClosed())
			rs.close();
		if (st != null && !st.isClosed())
			st.close();
		if (con != null && !con.isClosed())
			con.close();
	}

} // TestClient
