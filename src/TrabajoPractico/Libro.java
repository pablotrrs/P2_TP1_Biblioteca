package TrabajoPractico;

public class Libro {
	private String isbn, categoria, nombre;
	private double ancho;

	public Libro(String codI, String categ, String n, double a) {
		isbn = codI;
		categoria = categ;
		nombre = n;
		ancho = a;
		if (codI.length() != 13 || a < 1 || n.equals("") || categ.equals("")) {
			throw new RuntimeException(
					"Debe ingresar los datos de la siguiente manera: El largo del ISBN debe ser igual a 13, ni la categoria ni el nombre pueden ser una cadena vacia y el ancho debe ser mayor a 0.");
		}
	}

	public String toString() {
		// return "(ISBN: " + isbn + "; CATEGORIA: " + categoria+ "; NOMBRE: " + nombre
		// + "; ANCHO:" + ancho + ")";
		return "(CATEGORIA: " + categoria + "; NOMBRE: " + nombre + "; ANCHO:" + ancho + ")";
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

}