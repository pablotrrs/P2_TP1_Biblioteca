package TrabajoPractico;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import TrabajoPractico.Tupla;

public class Diccionario<C, S> {
	private ArrayList<Tupla<C, S>> dic;
	private Set<C> claves;

	public Diccionario() {// constructor
		dic = new ArrayList<Tupla<C, S>>();
		claves = new HashSet<C>();
	}

	public void agregar(C c, S s) {// agrega una tupla si no esta, si la clave de la tupla
									// ya estaba, le cambia el significado
		claves.add(c);
		Tupla<C, S> tupla = new Tupla<C, S>(c, s);
		if (!this.pertenece(c)) {
			dic.add(tupla);
		} else {
			for (int i = 0; i < this.tamano(); i++) {
				if (dic.get(i).getPrimElem().equals(tupla.getPrimElem())) {
					dic.get(i).setSegElem(tupla.getSegElem());
				}
			}
		}
	}

	public S obtener(C c) { // devuelve el significado de la clave pasada
		S signif = null;
		for (int i = 0; i < this.tamano(); i++) {
			if (dic.get(i).getPrimElem().equals(c)) {
				signif = dic.get(i).getSegElem();
			}
		}
		return signif;
	}

	public C iesimo(int i) {
		return dic.get(i).getPrimElem();
	}

	public boolean pertenece(C c) { // true si la clave ya esta en el diccionario
		boolean ret = false;
		for (int i = 0; i < this.tamano(); i++) {
			ret |= dic.get(i).getPrimElem().equals(c);
		}
		return ret;
	}

	public void eliminar(C c) {
		for (int i = 0; i < tamano(); i++) {
			if (dic.get(i).getPrimElem().equals(c)) {
				dic.remove(i);
				claves.remove(c);
			}
		}
	}

	public Collection<? extends Entry<Estante, Double>> claves() {
		// casteo
		Collection<? extends Entry<Estante, Double>> claves2 = (Collection<? extends Entry<Estante, Double>>) claves;
		return claves2;
	}

	public String toString() {
		return dic.toString();
	}

	public int tamano() {
		return dic.size();
	}
}