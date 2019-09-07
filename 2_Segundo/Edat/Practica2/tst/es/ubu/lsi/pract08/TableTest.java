package es.ubu.lsi.pract08;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ubu.lsi.edat.pract08.*;

public class TableTest {

	Table<String, Integer, String> tabla;

	@Before
	public void setUp() throws Exception {
		tabla = new HashMapTable<String, Integer, String>();
	}

	@After
	public void tearDown() throws Exception {
		tabla.clear();
	}

	@Test
	public void testPut() {

		int k = 0;

		for (int i = 0; i < 5; i++) {
			assertNull(tabla.put(Integer.toString(i), i, "Contenido_" + (k++)));
		}

		assertEquals(5, tabla.size());

		for (int j = 1; j < 6; j++) {
			assertNull(tabla.put(Integer.toString(j), (j - 1), "Contenido_" + (k++)));
		}

		assertEquals(10, tabla.size());

		for (int i = 0; i < 6; i++) {
			System.out.println(tabla.row(Integer.toString(i)));
		}

	}

	// TODO - Inserción al sobreescribir un valor

	@Test
	public void testRemove() {
		testPut();

		assertEquals("Contenido_5", tabla.remove("1", 0));
		assertEquals(9, tabla.size());

		assertEquals("Contenido_1", tabla.remove("1", 1));
		assertEquals(8, tabla.size());

	}

	@Test
	public void testGet() {
		testPut();
		assertEquals("Contenido_1", tabla.get("1", 1));
		assertEquals("Contenido_8", tabla.get("4", 3));
		assertEquals("Contenido_6", tabla.get("2", 1));
	}

	// TODO - Accesos en elementos vacíos

	@Test
	public void testContainsKeys() {

		testPut();

		assertTrue(tabla.containsKeys("1", 0));
		assertTrue(tabla.containsKeys("0", 0));

		assertTrue(tabla.containsKeys("3", 2));
		assertTrue(tabla.containsKeys("3", 3));

	}

	// TODO - Claves que no están insertadas
	// TODO - Claves en orden inverso

	@Test
	public void testContainsValue() {

		testPut();

		assertTrue(tabla.containsValue("Contenido_1"));
		assertTrue(tabla.containsValue("Contenido_3"));
		assertTrue(tabla.containsValue("Contenido_9"));

	}

	// TODO - Valores que no están insertados

	@Test
	public void testRow() {

		testPut();

		Map<Integer, String> mapa = tabla.row("3");

		assertTrue(mapa.containsKey(2));
		assertTrue(mapa.containsKey(3));

		assertEquals("Contenido_7", mapa.get(2));
		assertEquals("Contenido_3", mapa.get(3));

		assertFalse(mapa.containsKey(1));
		assertFalse(mapa.containsKey(4));
		assertTrue(mapa.containsValue("Contenido_7"));
		assertTrue(mapa.containsValue("Contenido_3"));

		assertEquals(2, mapa.size());
	}

	@Test
	public void testColumn() {

		testPut();

		Map<String, String> mapa = tabla.column(4);

		assertTrue(mapa.containsKey("4"));
		assertTrue(mapa.containsKey("5"));

		assertEquals("Contenido_4", mapa.get("4"));
		assertEquals("Contenido_9", mapa.get("5"));

		assertFalse(mapa.containsKey(1));
		assertFalse(mapa.containsKey(4));
		assertTrue(mapa.containsValue("Contenido_4"));
		assertTrue(mapa.containsValue("Contenido_9"));

		assertEquals(2, mapa.size());
	}

	@Test
	public void testSize() {

		assertEquals(0, tabla.size());
		testPut();
		assertEquals(10, tabla.size());

	}

	@Test
	public void testIsEmpty() {
		assertTrue(tabla.isEmpty());
		testPut();
		assertFalse(tabla.isEmpty());
	}

	@Test
	public void testCellSet() {
		testPut();
		Collection<es.ubu.lsi.edat.pract08.Table.Cell<String, Integer, String>> cellset = tabla.cellSet();
		Iterator<es.ubu.lsi.edat.pract08.Table.Cell<String, Integer, String>> it = cellset.iterator();
		while (it.hasNext()) {
			es.ubu.lsi.edat.pract08.Table.Cell<String, Integer, String> next = it.next();
			assertTrue(tabla.containsKeys(next.getRowKey(), next.getColumnKey()));
			assertEquals(tabla.get(next.getRowKey(), next.getColumnKey()), next.getValue());
		}
	}
}
