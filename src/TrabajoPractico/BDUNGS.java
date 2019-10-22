package TrabajoPractico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BDUNGS {
	private int cantEstantes;
	private double anchoEstantes;
	private ArrayList<Estante> biblioteca;
	private Iterator<Estante> iterador;

	public BDUNGS(int e, double a) {
		cantEstantes = e;
		anchoEstantes = a;
		biblioteca = new ArrayList<Estante>();
		Estante estante;
		for (int i = 1; i < e + 1; i++) {
			estante = new Estante("no asignado", i, a);
			biblioteca.add(estante);
		}
		if (cantEstantes < 1 || anchoEstantes < 1) {
			throw new RuntimeException(
					"Debe ingresar los datos de la siguiente manera: La cantidad y el ancho de los estantes debe ser mayor a 0.");
		}
	}

	public boolean ingresarLibro(String isbn, String categoria, String nombre, double ancho) {
		this.buscarISBN(isbn, categoria, nombre, ancho);
		boolean existeEstanteCateg = false, lugar = false;
		Libro libro = new Libro(isbn, categoria, nombre, ancho);
		for (Estante estante : biblioteca) {
			existeEstanteCateg |= estante.getCategoria().equalsIgnoreCase(categoria);
			if (estante.getCategoria().equalsIgnoreCase(categoria)) {
				if (estante.getAncho() >= ancho) {
					estante.agregarLibro(libro);
					lugar |= true;
					break;// PARA QUE NO SE AGREGUEN LOS LIBROS EN LAS OTRAS CATEGORIAS
				}
			}
		}
		if (!existeEstanteCateg) {
			throw new RuntimeException("No existe un estante con la categoria '" + categoria + "'.");
		}
		return lugar;
	}

	private void buscarISBN(String isbn, String categoria, String nombre, double ancho) {
		for (Estante estante : biblioteca) {
			for (int i = 0; i < estante.getLibros().tamano(); i++) {
				if (estante.getLibros().obtener(estante.getLibros().iesimo(i)).getIsbn().equals(isbn)
						&& (!estante.getLibros().obtener(estante.getLibros().iesimo(i)).getCategoria().equals(categoria)
								|| !estante.getLibros().obtener(estante.getLibros().iesimo(i)).getNombre()
										.equals(nombre)
								|| estante.getLibros().obtener(estante.getLibros().iesimo(i)).getAncho() != ancho)) {
					throw new RuntimeException(
							"El isbn '" + isbn + "' asignado a '" + nombre + "' ya pertenece al libro '"
									+ estante.getLibros().obtener(estante.getLibros().iesimo(i)).getNombre()
									+ "'\n\t\t\t    de la categoria '"
									+ estante.getLibros().obtener(estante.getLibros().iesimo(i)).getCategoria()
									+ "' con un ancho de "
									+ estante.getLibros().obtener(estante.getLibros().iesimo(i)).getAncho() + ".");
				}
			}
		}
	}

	public void rotularEstante(String rotulo, int orden) {
		if (orden > biblioteca.size()) {
			throw new RuntimeException(
					"Debe ingresar un numero de orden menor o igual a " + this.cantEstantes + " y mayor a 0.");
		}
		for (Estante estante : biblioteca) {
			if (estante.getOrden() == orden) {
				if (estante.getAncho() == anchoEstantes) {
					estante.setCategoria(rotulo.toLowerCase());
				} else {
					throw new RuntimeException("El estante no esta vacio.");
				}
			}
		}
	}

	public void eliminarLibro(String isbn) {
		for (Estante estante : biblioteca) {
			if (estante.getLibros().pertenece(isbn)) {
				estante.setAncho(estante.getAncho() + estante.getLibros().obtener(isbn).getAncho()
						* estante.getdicEjemplaresLibros().obtener(isbn));
			}
			estante.getLibros().eliminar(isbn);
			estante.getdicEjemplaresLibros().eliminar(isbn);
		}
	}

	public double espacioLibre(int orden) {
		if (biblioteca.get(orden - 1).getCategoria().equalsIgnoreCase("no asignado")) {
			throw new RuntimeException("El estante " + orden + " no tiene una categoria asignada.");
		}
		return biblioteca.get(orden - 1).getAncho();
	}

	public HashMap<String, Integer> verLibrosCategoria(String categoria) {
		HashMap<String, Integer> librosCategoria = new HashMap<String, Integer>();
		boolean ret = false;
		for (Estante estante : biblioteca) {
			ret |= estante.getCategoria().equalsIgnoreCase(categoria);
			if (estante.getCategoria().equalsIgnoreCase(categoria)) {
				for (int i = 0; i < estante.getLibros().tamano(); i++) {
					if (!librosCategoria.containsKey(estante.getLibros().iesimo(i))) {
						librosCategoria.put(estante.getdicEjemplaresLibros().iesimo(i),
								estante.getdicEjemplaresLibros().obtener(estante.getdicEjemplaresLibros().iesimo(i)));
					} else {
						librosCategoria.put(estante.getdicEjemplaresLibros().iesimo(i),
								estante.getdicEjemplaresLibros().obtener(estante.getdicEjemplaresLibros().iesimo(i))
										+ librosCategoria.get(estante.getdicEjemplaresLibros().iesimo(i)));
					}
				}
			}
		}
		if (!ret) {
			throw new RuntimeException("No existe un estante rotulado con la categoria '" + categoria + "'.");
		}
		return librosCategoria;
	}

	public int reacomodarCategoria(String categoria) {
		int vaciados = 0;
		// CUAL ES EL QUE MAS ESPACIO LIBRE TIENE, CUAL ES EL QUE MENOS ESPACIO LIBRE
		// TIENE, PARA GUARDAR EL TAMANO TOTAL SIN LIBROS DEL ESTANTE
		boolean buscar = true, ret = false, entranLibros = true;
		LinkedHashMap<Estante, Double> mayorAmenor = new LinkedHashMap<Estante, Double>();
		LinkedHashMap<Estante, Double> menorAmayor = new LinkedHashMap<Estante, Double>();
		Diccionario<String, Libro> libros = new Diccionario<String, Libro>();
		while (buscar) {
			// GUARDO LOS ESTANTES EN UN DICCIONARIO JUNTO A SUS ANCHOS
			for (Estante estantes : biblioteca) {
				ret |= categoria.equalsIgnoreCase(estantes.getCategoria());
				if (categoria.equalsIgnoreCase(estantes.getCategoria()) && estantes.getAncho() < anchoEstantes) {
					mayorAmenor.put(estantes, estantes.getAncho());
					menorAmayor.put(estantes, estantes.getAncho());
				}
			}
			if (!ret) {
				throw new RuntimeException("No existe un estante rotulado con la categoria '" + categoria + "'.");
			}
			if (mayorAmenor.size() == 0 || menorAmayor.size() == 0) {
				buscar = false;
			}
			menorAmayor = sortMenor(menorAmayor); // ORDENO LOS DICCIONARIOS DE MENOR A MAYOR
			mayorAmenor = sortMayor(mayorAmenor); // ORDENO LOS DICCIONARIOS DE MAYOR A MENOR
			for (int i = 0; i < mayorAmenor.size(); i++) {
				for (int j = 0; j < menorAmayor.size(); j++) {
					entranLibros = true;
					double anchoMay = new ArrayList<Double>(mayorAmenor.values()).get(i);// ES EL ANCHO DEL ESTANTE i
																							// (de los mayores)
					double anchoMen = new ArrayList<Double>(menorAmayor.values()).get(j);
					if (anchoMay != anchoMen && anchoMay > anchoMen) { // SI NO SON EL MISMO ESTANTE/ANCHO Y SI EL ANCHO
																		// ES MAYOR
						Estante estanteMay = new ArrayList<Estante>(mayorAmenor.keySet()).get(i);
						Estante estanteMen = new ArrayList<Estante>(menorAmayor.keySet()).get(j);
						while (estanteMay.getLibros().tamano() != 0 && estanteMay.getAncho() != anchoEstantes
								&& entranLibros) {
							libros = estanteMay.getLibros(); // GUARDO LOS LIBROS DEL ESTANTE DE DONDE HAY QUE SACARLOS
																// (estanteMay)
							for (int k = 0; k < libros.tamano(); k++) {
								Libro libro = new Libro(libros.iesimo(k),
										libros.obtener(libros.iesimo(k)).getCategoria(),
										libros.obtener(libros.iesimo(k)).getNombre(),
										libros.obtener(libros.iesimo(k)).getAncho());
								if (estanteMen.getAncho() >= libro.getAncho()) {// VEO SI PUEDO MOVER LOS LIBROS AL OTRO
									// ESTANTE (estanteMen)
									estanteMen.agregarLibro(libro);
									estanteMay.eliminarLibro(libro.getIsbn());
								} else {
									entranLibros = false;
								}
							}
						}
						if (estanteMay.getAncho() == anchoEstantes) {
							vaciados++;
						}
					} else {
						buscar = false;
					}
				}
			}
		}
		return vaciados;
	}

	// ORDENA EL DICCIONARIO DE MENOR A MAYOR (los estantes de una categoria, segun
	// sus anchos)
	private static LinkedHashMap<Estante, Double> sortMenor(LinkedHashMap<Estante, Double> hm) {
		// CREA UNA LISTA DE LOS ELEMENTOS DEL LinkedHashMap
		List<Map.Entry<Estante, Double>> list = new LinkedList<Map.Entry<Estante, Double>>(hm.entrySet());
		// ORDENA ESA LISTA
		Collections.sort(list, new Comparator<Map.Entry<Estante, Double>>() {
			public int compare(Map.Entry<Estante, Double> o1, Map.Entry<Estante, Double> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		// PONE LOS DATOS DE LA LISTA ORDENADA EN UN NUEVO LinkedHashMap
		LinkedHashMap<Estante, Double> temp = new LinkedHashMap<Estante, Double>();
		for (Map.Entry<Estante, Double> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	// ORDENA EL DICCIONARIO DE MAYOR A MENOR (los estantes de una categoria, segun
	// sus anchos)
	private static LinkedHashMap<Estante, Double> sortMayor(LinkedHashMap<Estante, Double> hm) {
		// CREA UNA LISTA DE LOS ELEMENTOS DEL LinkedHashMap
		List<Map.Entry<Estante, Double>> list = new LinkedList<Map.Entry<Estante, Double>>(hm.entrySet());
		// ORDENA ESA LISTA
		Collections.sort(list, new Comparator<Map.Entry<Estante, Double>>() {
			public int compare(Map.Entry<Estante, Double> o1, Map.Entry<Estante, Double> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		}.reversed());
		// PONE LOS DATOS DE LA LISTA ORDENADA EN UN NUEVO LinkedHashMap PERO AL REVES
		// (de mayor a menor)
		LinkedHashMap<Estante, Double> temp = new LinkedHashMap<Estante, Double>();
		for (Map.Entry<Estante, Double> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	public String toString() {
		StringBuilder str = new StringBuilder("\t\t\tBDUNGS\n");
		iterador = biblioteca.iterator();
		Object objeto;
		Estante estante;
		while (iterador.hasNext()) {
			objeto = iterador.next();
			estante = (Estante) objeto; // CASTEO DEL TIPO O bject A Estante
			str.append(estante.toString() + " LIBROS: ");
			str.append(estante.getLibros().toString());
			str.append("\n");
		}
		return str.toString();
	}
}