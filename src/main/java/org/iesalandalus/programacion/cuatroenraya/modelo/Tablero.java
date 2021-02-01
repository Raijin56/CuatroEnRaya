package org.iesalandalus.programacion.cuatroenraya.modelo;

import javax.naming.OperationNotSupportedException;

public class Tablero {

	public static final int FILAS = 6;
	public static final int COLUMNAS = 7;
	public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;

	private Casilla[][] casillas;

	public Tablero() {
		casillas = new Casilla[FILAS][COLUMNAS];
		for (int fila = 0; fila < casillas.length; fila++) {
			for (int columna = 0; columna < casillas[fila].length; columna++) {
				casillas[fila][columna] = new Casilla();
			}
		}
	}

	public boolean estaVacio() {
		boolean estaElTableroVacio = true;
		for (int columna = 0; columna < COLUMNAS && estaElTableroVacio; columna++) {
			estaElTableroVacio = columnaVacia(columna);
		}
		return estaElTableroVacio;
	}

	private boolean columnaVacia(int columna) {
		comprobarColumna(columna);
		return !casillas[0][columna].estaOcupada();
	}

	public boolean estaLleno() {
		boolean estaElTableroLleno = true;
		for (int columna = 0; columna < COLUMNAS && estaElTableroLleno; columna++) {
			if (!columnaLlena(columna)) {
				estaElTableroLleno = false;
			}
		}
		return estaElTableroLleno;
	}

	private boolean columnaLlena(int columna) {
		comprobarColumna(columna);
		return casillas[FILAS - 1][columna].estaOcupada();
	}

	public boolean introducirFicha(int columna, Ficha ficha) throws OperationNotSupportedException {
		comprobarColumna(columna);
		comprobarFicha(ficha);
		if (columnaLlena(columna)) {
			throw new OperationNotSupportedException("ERROR: Columna llena.");
		}
		int fila = getPrimeraFilaVacia(columna);
		casillas[fila][columna].setFicha(ficha);
		return comprobarTirada(fila, columna);
	}

	private void comprobarFicha(Ficha ficha) {
		if (ficha == null) {
			throw new NullPointerException("ERROR: La ficha no puede ser nula.");
		}
		if (ficha != Ficha.AZUL && ficha != Ficha.VERDE) {
			throw new IllegalArgumentException("ERROR: La ficha debe ser azul o verde.");
		}
	}

	private void comprobarFila(int fila) {
		if (fila < 0 || fila >= FILAS) {
			throw new IllegalArgumentException("ERROR: No se puede poner una fila incorrecta.");
		}
	}

	private void comprobarColumna(int columna) {
		if (columna < 0 || columna >= COLUMNAS) {
			throw new IllegalArgumentException("ERROR: Columna incorrecta.");
		}
	}

	private int getPrimeraFilaVacia(int columna) {
		comprobarColumna(columna);
		int fila;
		for (fila = 0; fila < FILAS && casillas[fila][columna].estaOcupada(); fila++);
		return fila;
	}

	private boolean comprobarTirada(int fila, int columna) {
		comprobarFila(fila);
		comprobarColumna(columna);
		Ficha ficha = casillas[fila][columna].getFicha();
		return comprobarHorizontal(fila, ficha) || comprobarVertical(columna, ficha)
				|| comprobarDiagonalNE(fila, columna, ficha) || comprobarDiagonalNO(fila, columna, ficha);
	}

	private boolean objetivoAlcanzado(int numeroFichasConsecutivas) {
		if (numeroFichasConsecutivas < 0) {
			throw new IllegalArgumentException("ERROR: El número de fichas consecutivas no puede ser menor que 0.");
		}
		return numeroFichasConsecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
	}

	private boolean comprobarHorizontal(int fila, Ficha ficha) {
		comprobarFila(fila);
		comprobarFicha(ficha);
		int numeroFichasConsecutivas = 0;
		boolean esObjetivoAlcanzado = false;
		for (int columna = 0; columna < COLUMNAS && !esObjetivoAlcanzado; columna++) {
			if (ficha == casillas[fila][columna].getFicha()) {
				numeroFichasConsecutivas++;
			} else {
				numeroFichasConsecutivas = 0;
			}
			esObjetivoAlcanzado = objetivoAlcanzado(numeroFichasConsecutivas);
		}
		return esObjetivoAlcanzado;
	}

	private boolean comprobarVertical(int columna, Ficha ficha) {
		comprobarColumna(columna);
		comprobarFicha(ficha);
		int numeroFichasConsecutivas = 0;
		boolean esObjetivoAlcanzado = false;
		for (int fila = 0; fila < FILAS && !esObjetivoAlcanzado; fila++) {
			if (ficha == casillas[fila][columna].getFicha()) {
				numeroFichasConsecutivas++;
			} else {
				numeroFichasConsecutivas = 0;
			}
			esObjetivoAlcanzado = objetivoAlcanzado(numeroFichasConsecutivas);
		}
		return esObjetivoAlcanzado;
	}

	private boolean comprobarDiagonalNE(int filaSemilla, int columnaSemilla, Ficha ficha) {
		comprobarFila(filaSemilla);
		comprobarColumna(columnaSemilla);
		comprobarFicha(ficha);
		int desplazamientoInicial = menor(filaSemilla, columnaSemilla);
		int filaInicial = filaSemilla - desplazamientoInicial;
		int columnaInicial = columnaSemilla - desplazamientoInicial;
		int numeroFichasConsecutivas = 0;
		boolean esObjetivoAlcanzado = false;
		for (int fila = filaInicial, columna = columnaInicial; fila < FILAS && columna < COLUMNAS
				&& !esObjetivoAlcanzado; fila++, columna++) {
			if (ficha == casillas[fila][columna].getFicha()) {
				numeroFichasConsecutivas++;
			} else {
				numeroFichasConsecutivas = 0;
			}
			esObjetivoAlcanzado = objetivoAlcanzado(numeroFichasConsecutivas);
		}
		return esObjetivoAlcanzado;
	}

	private boolean comprobarDiagonalNO(int filaSemilla, int columnaSemilla, Ficha ficha) {
		comprobarFila(filaSemilla);
		comprobarColumna(columnaSemilla);
		comprobarFicha(ficha);
		int desplazamientoInicial = menor(filaSemilla, COLUMNAS - 1 - columnaSemilla);
		int filaInicial = filaSemilla - desplazamientoInicial;
		int columnaInicial = columnaSemilla + desplazamientoInicial;
		int numeroFichasConsecutivas = 0;
		boolean esObjetivoAlcanzado = false;
		for (int fila = filaInicial, columna = columnaInicial; fila < FILAS && columna >= 0
				&& !esObjetivoAlcanzado; fila++, columna--) {
			if (ficha == casillas[fila][columna].getFicha()) {
				numeroFichasConsecutivas++;
			} else {
				numeroFichasConsecutivas = 0;
			}
			esObjetivoAlcanzado = objetivoAlcanzado(numeroFichasConsecutivas);
		}
		return esObjetivoAlcanzado;
	}

	private int menor(int numero1, int numero2) {
		return (numero1 < numero2) ? numero1 : numero2;
	}

	@Override
	public String toString() {
		StringBuilder cadenaSB = new StringBuilder("");
		for (int fila = casillas.length - 1; fila >= 0; fila--) {
			cadenaSB.append("|");
			for (int columna = 0; columna < casillas[fila].length; columna++) {
				cadenaSB.append(String.format("%s", casillas[fila][columna].toString()));
			}
			cadenaSB.append("|\n");
		}
		cadenaSB.append(" -------\n");
		return cadenaSB.toString();
	}

}
