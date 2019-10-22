package TrabajoPractico;
import java.util.ArrayList;
import java.util.Iterator;

public class conjunto<T> {
	ArrayList<T> conj;
	Integer indice = 0;

	public conjunto() {
		this.conj = new ArrayList<T>();
	}

	public boolean contiene(T e) {
		boolean ret = false;
		for (int i = 0; i < conj.size(); i++) {
			ret |= e.equals(conj.get(i));
		}
		return ret;
	}

	public int tamano() {
		return conj.size();
	}

	public void agregar(T e) {
		if (!conj.contains(e)) {
			conj.add(e);
		}
	}

	public T iesimo(Integer i) {
		if (i > conj.size()) {
			throw new RuntimeException("Indice invalido.");
		}
		return conj.get(i);
	}

	// CON STRINGBUILDER E ITERADOR

	public String toString() {
		StringBuilder str = new StringBuilder("[ ");
		Iterator<T> it = conj.iterator();

		while (it.hasNext()) {
			str.append("( ");
			str.append(it.next());
			str.append(" ) ");
		}

		str.append("]");
		return str.toString();

	}

	// CON STRINGBUILDER Y FOREACH
	/*
	 * public String toString() { StringBuilder str = new StringBuilder("[ "); for
	 * (T elem : conj) { str.append("( "); str.append(elem); str.append(" ) "); }
	 * str.append("]"); return str.toString(); }
	 */

	// public String toString() {
	// return conj.toString();
	// }

	public void union1(conjunto<T> c2) {
		for (int i = 0; i < c2.tamano(); i++) {
			this.agregar(c2.iesimo(i));
		}
	}

	public conjunto<T> union2(conjunto<T> c2) {
		conjunto<T> copia = new conjunto<T>();
		for (int i = 0; i < this.tamano(); i++) {
			copia.agregar(this.iesimo(i));
		}
		copia.union1(c2);
		return copia;
	}

	public void remover(int i) {
		conj.remove(i);
	}

	public void removerElem(T e) {
		conj.remove(e);
	}

	public void interseccion1(conjunto<T> c2) {
		if (this.tamano() <= c2.tamano()) {
			for (int j = this.tamano() - 1; j > -1; j--) {
				if (!(c2.contiene(this.iesimo(j)))) {
					this.remover(j);
				}
			}
		} else {
			for (int j = c2.tamano() - 1; j > -1; j--) {
				if (!(this.contiene(c2.iesimo(j)))) {
					c2.remover(j);
				}
			}
		}
	}

	public conjunto<T> interseccion2(conjunto<T> c2) {
		conjunto<T> inter = new conjunto<T>();
		if (this.tamano() <= c2.tamano()) {
			for (int i = 0; i < this.tamano(); i++) {
				if (c2.contiene(this.iesimo(i))) {
					inter.agregar(this.iesimo(i));
				}
			}
		} else {
			for (int i = 0; i < c2.tamano(); i++) {
				if (this.contiene(c2.iesimo(i))) {
					inter.agregar(c2.iesimo(i));
				}
			}
		}
		return inter;
	}

	public T dameUno() {
		if (this.tamano() == 0) {
			throw new RuntimeException("El conjunto esta vacio");
		}
		T ret = null;
		if (indice == this.tamano()) {
			indice = 0;
		}
		ret = this.iesimo(indice);
		indice++;
		return ret;
	}
}