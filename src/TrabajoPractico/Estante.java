package TrabajoPractico;

public class Estante {
	private String categoria;
	private int orden;
	private double ancho;
	private Diccionario<String, Libro> libros;
	private Diccionario<String, Integer> dicEjemplaresLibros;

	public Estante(String categ, int ord, double a) {
		categoria = categ;
		orden = ord;
		ancho = a;
		libros = new Diccionario<String, Libro>();
		dicEjemplaresLibros = new Diccionario<String, Integer>();
		if (ord < 0 || a < 1 || categ.equals("")) {
			throw new RuntimeException(
					"Debe ingresar los datos de la siguiente manera: La categoria no puede ser una cadena vacia, el numero de orden debe ser mayor a 0 y el ancho debe ser mayor a 0.");
		}
	}

	public void agregarLibro(Libro libro) {
		if (!libros.pertenece(libro.getIsbn())) {
			libros.agregar(libro.getIsbn(), libro);
			dicEjemplaresLibros.agregar(libro.getIsbn(), 1);
			this.setAncho(this.getAncho() - libros.obtener(libro.getIsbn()).getAncho());
		} else {
			dicEjemplaresLibros.agregar(libro.getIsbn(), dicEjemplaresLibros.obtener(libro.getIsbn()) + 1);
			this.setAncho(this.getAncho() - libros.obtener(libro.getIsbn()).getAncho());
		}
	}

	public void eliminarLibro(String isbn) {
		for (int i = 0; i < dicEjemplaresLibros.tamano(); i++) {
			if (dicEjemplaresLibros.iesimo(i).equalsIgnoreCase(isbn)) {
				if (dicEjemplaresLibros.obtener(isbn) == 1) {
					dicEjemplaresLibros.eliminar(isbn);

					this.setAncho(this.getAncho() + libros.obtener(isbn).getAncho());
					libros.eliminar(isbn);
				} else if (dicEjemplaresLibros.obtener(dicEjemplaresLibros.iesimo(i)) > 1) {
					this.setAncho(this.getAncho() + libros.obtener(libros.iesimo(i)).getAncho());
					dicEjemplaresLibros.agregar(isbn, dicEjemplaresLibros.obtener(isbn) - 1);
				}
			}
		}
	}


	public String toString() {
		return "(CATEGORIA: " + categoria + "; NUMERO: " + orden + "; ANCHO: " + ancho + ")";
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getOrden() {
		return orden;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public Diccionario<String, Libro> getLibros() {
		return libros;
	}

	public Diccionario<String, Integer> getdicEjemplaresLibros() {
		return this.dicEjemplaresLibros;
	}
}