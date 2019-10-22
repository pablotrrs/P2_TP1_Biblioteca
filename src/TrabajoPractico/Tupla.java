package TrabajoPractico;

public class Tupla<T1, T2> {
	private T1 primero;
	private T2 segundo;

	public Tupla(T1 primElem, T2 segElem) {
		primero = primElem;
		segundo = segElem;
	}

	public T1 getPrimElem() {
		return primero;
	}

	public T2 getSegElem() {
		return segundo;
	}

	public void setPrimElem(T1 unElem) {
		primero = unElem;
	}

	public void setSegElem(T2 unElem) {
		segundo = unElem;
	}

	public String toString() {
		return "("+primero.toString() + " : " + segundo.toString()+")";
	}
	
	
}