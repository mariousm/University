package es.ubu.lsi.edat.pr10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ArbolAVL<E> extends ArbolBB<E> {

	public ArbolAVL() {

	}

	/**
	 * Este método sirve para coger la altura de un elemento en el arbol
	 * 
	 * @param elemento
	 * es el elemento que deseamos buscar.
	 * @return altura a la que está el elemento que deseabamos buscar.
	 */

	public int altura(E elemento) {
		if (elemento == null) {
			return 0;
		}

		Nodo cont = buscar(raiz, elemento).get(0);
		if (cont == null)
			return -1;

		if (cont.getIzq() == null && cont.getDer() == null) {
			return 0;
		} else {
			if (cont.getIzq() != null && cont.getDer() == null)
				return altura(cont.getIzq().getDato()) + 1;
			else if (cont.getIzq() == null && cont.getDer() != null)
				return altura(cont.getDer().getDato()) + 1;
			else
				return 1 + Math.max(altura(cont.getDer().getDato()), altura(cont.getIzq().getDato()));
		}
	}

	/**
	 * Este metodo devuelve la profundidad que tiene un elemento desead en el árbol.
	 * 
	 * @param elemento
	 * que es el elemento que nosotros deseamos buscar en el arbol.
	 * @return la profundidad del elemento que deseamos buscar.
	 */

	public int profundidad(E elemento) {
		Nodo a = raiz;
		int profundidad = 0;

		if (buscar(raiz, elemento).get(0) == null) {
			int retorno=-1;
			return retorno;
		}

		while (a.getDato() != elemento) {
			if (comparar(a.getDato(), elemento) > 0) {
				a = a.getIzq();
				profundidad=profundidad+1;
			} else {
				a = a.getDer();
				profundidad=profundidad+1;
			}
		}

		return profundidad;

	}

	/**
	 * Este método hace la rotación simple hacia la izquierda.
	 * 
	 * @param raizSub 
	 * es el nodo raiz del subarbol.
	 * @return devolvemos la raiz de ese subarbol que queremos equilibrar.
	 */

	private Nodo rotacionIzq(Nodo raizSub) {
		Nodo newR = raizSub.getDer();
		raizSub.setDer(newR.getIzq());
		newR.setIzq(raizSub);

		return newR;
	}

	/**
	 * Este metodo hace la rotación simple hacia la derecha.
	 * 
	 * @param raizSub 
	 * es el nodo raiz del subarbol.
	 * @return devolvemos la raiz de ese subarbol que queremos equilibrar.
	 */

	private Nodo rotacionDer(Nodo raizSub) {
		Nodo newR = raizSub.getIzq();
		raizSub.setIzq(newR.getDer());
		newR.setDer(raizSub);

		return newR;
	}

	/**
	 * Este metodo realiza una rotación compuesta izquierda-derecha.
	 * 
	 * @param raizSub 
	 * es la raiz del subarbol a equilibrar.
	 * @return devolvemos la raiz de ese subarbol que queremos equilibrar.
	 */

	private Nodo rotacionIzqDer(Nodo raizSub) {
		raizSub.setIzq(rotacionIzq(raizSub.getIzq()));
		Nodo newR = rotacionDer(raizSub);
		return newR;
	}

	/**
	 * Este metodo realiza una rotación compuesta izquierda-derecha.
	 * 
	 * @param raizSub 
	 * es la raiz del subarbol a equilibrar.
	 * @return devolvemos la raiz de ese subarbol que queremos equilibrar.
	 */

	private Nodo rotacionDerIzq(Nodo raizSub) {
		raizSub.setDer(rotacionDer(raizSub.getDer()));
		Nodo newR = rotacionIzq(raizSub);
		return newR;
	}

	/**
	 * Este método como el nombre indica devolvera el factor de equilibrio del nodo.
	 * 
	 * @param nodo 
	 * es el nodo que vamos a mirar su factor.
	 * @return devuelve el factor de ese nodo que hemos metido.
	 */

	private int equilibrio(Nodo nodo) {
		int alturaDerecha=0, alturaIzquierda=0, retorno=0;
		if(nodo.getDer()!=null){
			alturaIzquierda = altura(nodo.getDer().getDato())+1;
			retorno=altura(nodo.getDer().getDato())+1;
			return retorno;
		}
		if(nodo.getIzq()!=null){
			alturaDerecha = (altura(nodo.getIzq().getDato())+1);
			retorno = 0-(altura(nodo.getIzq().getDato())+1);
			return retorno;
		}
		if(nodo.getDer()==null && nodo.getIzq()==null){
			return retorno;
		}
		retorno = alturaDerecha - alturaIzquierda;
		return retorno;
	}

	/**
	 * Este metodo hace un equilibrio del árbol para que no deje de ser árbol AVL.
	 * 
	 * @param nodo 
	 * es el determinado nodo por donde lo equilibramos.
	 * @return devuelve un nodo que ya esta equilibrado.
	 */

	private Nodo reequilibrar(Nodo nodo) {
		Nodo retorno = nodo;
			if (retorno != null) {
				if (equilibrio(retorno) == 2) {
					if (equilibrio(retorno.getDer()) == 1) {
						retorno = rotacionIzq(retorno);
					} else if (equilibrio(retorno.getDer()) == -1) {
						retorno = rotacionDerIzq(retorno);
					}
				} else if (equilibrio(retorno) == -2) {
					if (equilibrio(retorno.getIzq()) == -1) {
						retorno = rotacionDer(retorno);
					} else if (equilibrio(retorno.getIzq()) == 1) {
						retorno = rotacionIzqDer(retorno);
					}
				}
			}
			return retorno;
	}

	/**
	 * Este metodo sirve para añadir un valor al arbol y que no deje de ser AVL.
	 * 
	 * @param valor 
	 * es la informacion que vamos a añadir.
	 * @return devuelve true si consiguio añadir y false si no consiguio añadir.
	 */

	@Override
	public boolean add(E valor) {
		boolean retorno = super.add(valor);
		if (numElementos > 2 && retorno==true) {
			Nodo donde = buscar(raiz, valor).get(1);
			while (donde != raiz) {
				if (Math.abs(equilibrio(donde)) > 1) {
					Nodo a = reequilibrar(donde);
					Nodo hijo = buscar(raiz, donde.getDato()).get(1);
					if (comparar(hijo.getDato(), a.getDato()) > 0) {
						hijo.setIzq(a);
					} else {
						hijo.setDer(a);
					}
					donde = raiz;
				} else {
					donde = buscar(raiz, donde.getDato()).get(1);
				}
			}
		}
		return retorno;
	}

	/**
	 * Este metodo sirve para borrar un determinado elemento y que el arbol siga siendo AVL.
	 * 
	 * @param o 
	 * que es el dato que queremos borrar.
	 * @return true o false dependiendo de si tuvo éxito.
	 */

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		E valor;
		try {
			valor = (E) o;
		} catch (ClassCastException e) {
			return false;
		}
		Nodo donde = buscar(raiz, valor).get(1);
		boolean retorno = super.remove(o);
		if (numElementos > 2 && retorno==true) {
			while (donde != raiz) {
				if (Math.abs(equilibrio(donde)) > 1) {
					Nodo a = reequilibrar(donde);
					Nodo hijo = buscar(raiz, donde.getDato()).get(1);
					if (comparar(hijo.getDato(), a.getDato()) > 0) {
						hijo.setIzq(a);
					} else {
						hijo.setDer(a);
					}
					donde = raiz;
				} else {
					donde = buscar(raiz, donde.getDato()).get(1);
				}
			}
		}
		return retorno;
	}

	/**
	 * Nos da el recorrido inorden.
	 * 
	 * @return la lista con el recorrido inorden.
	 */

	public List<E> inOrden() {
		List<E> lista1 = new ArrayList<E>();
		Stack<Nodo> pilaPorDerecha = new Stack<Nodo>();
		Stack<Nodo> pilaPorIzquierda = new Stack<Nodo>();
		pilaPorDerecha.push(raiz);
		while (lista1.size() != numElementos) {
			if (pilaPorDerecha.isEmpty()) {
				lista1.add(pilaPorIzquierda.peek().getDato());
				if (pilaPorIzquierda.peek().getDer() != null)
					pilaPorDerecha.push(pilaPorIzquierda.peek().getDer());
				pilaPorIzquierda.pop();
			} else {
				Nodo p = pilaPorDerecha.pop();
				if (p.getIzq() != null) {
					pilaPorDerecha.push(p.getIzq());
					pilaPorIzquierda.push(p);
				} else {
					lista1.add(p.getDato());
				}
			}
		}
		return lista1;
	}

	/**
	 * Nos da el recorrido preorden.
	 * 
	 * @return la lista con el recorrido preorden.
	 */

	public List<E> preOrden() {
		Stack<Nodo> pila1 = new Stack<Nodo>();
		List<E> lista2 = new ArrayList<E>();
		pila1.push(raiz);
		while (pila1.isEmpty()==false) {
			Nodo nodo = pila1.pop();
			lista2.add(nodo.getDato());
			if (nodo.getDer() != null) {
				pila1.push(nodo.getDer());
			}
			if (nodo.getIzq() != null) {
				pila1.push(nodo.getIzq());
			}
		}
		return lista2;
	}

	/**
	 * Nos da el recorrido postorden.
	 * 
	 * @return la lista con el recorrido postorden.
	 */

	public List<E> posOrden() {
		Map<Nodo, Integer> mapa1 = new HashMap<Nodo, Integer>();
		List<E> lista3 = new ArrayList<E>();
		Stack<Nodo> pila2 = new Stack<Nodo>();
		pila2.push(raiz);
		mapa1.put(raiz, 1);
		while (pila2.isEmpty()==false) {
			Nodo pila3 = pila2.pop();
			int numero = mapa1.get(pila3);

			if (numero == 2) {
				lista3.add(pila3.getDato());
			} else {
				pila2.push(pila3);
				mapa1.put(pila3, 1+numero);
			}

			if (pila3.getDer() != null && mapa1.containsKey(pila3.getDer())==false) {
				pila2.push(pila3.getDer());
				mapa1.put(pila3.getDer(), 1);
			}

			if (pila3.getIzq() != null && mapa1.containsKey(pila3.getIzq())==false) {
				pila2.push(pila3.getIzq());
				mapa1.put(pila3.getIzq(), 1);
			}

		}

		return lista3;
	}

}
