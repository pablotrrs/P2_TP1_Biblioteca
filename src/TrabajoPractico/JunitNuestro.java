package TrabajoPractico;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class JunitNuestro {

	BDUNGS bd1, bd2;

	@Before
	public void setUp() throws Exception {
		bd1 = new BDUNGS(5, 30);
		bd2 = new BDUNGS(3, 45.5);

		bd1.rotularEstante("Literatura", 1);
		bd1.rotularEstante("Matematica", 2);

		bd1.ingresarLibro("9789684443457", "Literatura", "Blancanieves", 5);
		bd1.ingresarLibro("9788415552222", "Literatura", "Romeo y Julieta", 7);
		bd1.ingresarLibro("9788415552222", "Literatura", "Romeo y Julieta", 7);

		bd2.rotularEstante("Matematica", 2);
		bd2.rotularEstante("Computacion", 3);

		bd2.ingresarLibro("9876546532121", "Computacion", "Sistemas operativos I", 20);
		bd2.ingresarLibro("9076546432121", "Matematica", "Funciones cuadraticas", 20);
	}

	@Test
	public void testIngresarLibro() {
		// El primer libro se debe ingresar en el estante 5, el otro no porque no entra
		// en ninguno
		bd1.rotularEstante("Literatura", 5);
		bd1.ingresarLibro("9589875809482", "Literatura", "Hamlet", 12);
		bd1.ingresarLibro("9589875803053", "Literatura", "La odisea", 25);
		// El espacio libre del estante 5 debe ser 18
		assertTrue(bd1.espacioLibre(5) == 18.0);
	}

	@Test(expected = RuntimeException.class)
	public void testRotularEstanteNoVacio() {
		// Lanza excepcion porque el estante 1 en la bd1 tiene libros
		bd1.rotularEstante("Historia", 1);
	}

	@Test
	public void testVerLibrosCategoria() {
		HashMap<String, Integer> libComp = bd1.verLibrosCategoria("Literatura");
		assertEquals(libComp.size(), 2);
		assertTrue(libComp.containsKey("9789684443457"));
		assertTrue(libComp.containsKey("9788415552222"));
	}

	@Test(expected = RuntimeException.class)
	public void testVerLibrosCategoriaSinEstante() {
		// Lanza excepcion porque la bd1 no tiene estantes para Historia
		HashMap<String, Integer> libComp = bd1.verLibrosCategoria("Historia");
	}

	@Test
	public void testEspacioLibre() {
		// El espacio libre del estante 1 en la bd1 debe ser 11
		assertTrue(bd1.espacioLibre(1) == 11.0);
		// El espacio libre del estante 2 en la bd2 debe ser 70
		assertTrue(bd2.espacioLibre(3) == 25.5);
	}

	@Test(expected = RuntimeException.class)
	public void testEspacioLibre_EstanteSinRotular() {
		// El estante 3 en la bd1 no esta rotulado,
		// por lo que debe lanzar una excepcion
		System.out.println(bd1.espacioLibre(3));
	}

	@Test
	public void testEliminarLibro() {
		double esp = bd1.espacioLibre(1);
		bd1.eliminarLibro("9788415552222");
		// Al eliminar el libro, hay 14 cm mas de espacio libre en el estante 
		assertTrue(bd1.espacioLibre(1) == esp + 14);
	}

	@Test
	public void testReacomodarCategoria1() {
		// Lanza excepcion si no hay ningun estante con la categoria
		bd1.rotularEstante("Literatura", 5);
		bd1.ingresarLibro("9789684441234", "Literatura", "Dracula", 15);
		bd1.ingresarLibro("9789684449876", "Literatura", "Frankestein", 10);
		bd1.ingresarLibro("9788415555678", "Literatura", "Carrie", 8);
		bd1.eliminarLibro("9789684449876");
		bd1.eliminarLibro("9789684441234");
		// Se debe liberar un estante
		assertEquals(bd1.reacomodarCategoria("Literatura"), 1);
	}

	@Test
	public void testReacomodarCategoria2() {
		// Lanza excepcion si no hay ningun estante con la categoria
		bd1.rotularEstante("Literatura", 5);
		bd1.ingresarLibro("9789684441234", "Literatura", "Dracula", 15);
		bd1.ingresarLibro("9789684449876", "Literatura", "Frankestein", 10);
		bd1.ingresarLibro("9788415555678", "Literatura", "Carrie", 8);
		bd1.rotularEstante("Literatura", 3);

		bd1.ingresarLibro("9788411111678", "Literatura", "It", 28);

		bd1.eliminarLibro("9789684449876");
		bd1.eliminarLibro("9789684441234");
		// Se debe liberar un estante, el libro del estante 5 primero se fija si tiene
		// lugar en el 3 ( el que menos lugar tiene ) como ve que no entra sigue con
		// otro
		assertEquals(bd1.reacomodarCategoria("Literatura"), 1);
	}

	@Test
	public void testReacomodarCategoria3() {
		// Lanza excepcion si no hay ningun estante con la categoria
		bd1.rotularEstante("Literatura", 5);
		bd1.ingresarLibro("9789684441234", "Literatura", "Dracula", 15);
		bd1.ingresarLibro("9789684449876", "Literatura", "Frankestein", 10);
		bd1.ingresarLibro("9788415555678", "Literatura", "Carrie", 8);
		bd1.rotularEstante("Literatura", 3);

		bd1.ingresarLibro("9788411111678", "Literatura", "It", 28);

		bd1.ingresarLibro("9994521564646", "Literatura", "Martin Fierro", 3);
		bd1.ingresarLibro("9333331564646", "Literatura", "Luna de Pluton", 2);
		bd1.ingresarLibro("9111111111646", "Literatura", "El festival de la blasfemia", 2);

		bd1.eliminarLibro("9789684449876");
		bd1.eliminarLibro("9789684441234");
		bd1.eliminarLibro("9333331564646");

		assertEquals(bd1.reacomodarCategoria("Literatura"), 1);

	}
}