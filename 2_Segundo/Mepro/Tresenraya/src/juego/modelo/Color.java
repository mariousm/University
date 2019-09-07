/**
 * 
 */
package juego.modelo;

/**
 * Enumeracion del color.
 * 
 * @author Jorge Navarro Gonz�lez
 * @author Mario Ubierna San Mam�s
 *
 */
public enum Color {
	BLANCO('O'), NEGRO('X');

	/**
	 * El caracter que asociamos al color.
	 */
	private char caracter;

	/**
	 * Asiganmos al caracter el color.
	 * 
	 * @param c
	 */
	private Color(char c) {
		this.caracter = c;
	}

	/**
	 * Nos devuelve la variable caracter.
	 * 
	 * @return caracter
	 */
	public char toChar() {
		return this.caracter;
	}
}
