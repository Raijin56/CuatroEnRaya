package org.iesalandalus.programacion.cuatroenraya;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.cuatroenraya.modelo.Tablero;
import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

public class CuatroEnRaya {

	private static final int NUMERO_JUGADORES = 2;

	Jugador[] jugadores;
	Tablero tablero;

	public CuatroEnRaya(Jugador jugador1, Jugador jugador2) {
		if (jugador1 == null) {
			throw new NullPointerException("ERROR: El jugador 1 no puede ser nulo.");
		}
		if (jugador2 == null) {
			throw new NullPointerException("ERROR: El jugador 2 no puede ser nulo.");
		}
		jugadores = new Jugador[NUMERO_JUGADORES];
		jugadores[0] = new Jugador(jugador1.getNombre(), jugador1.getColorFichas());
		jugadores[1] = new Jugador(jugador2.getNombre(), jugador2.getColorFichas());
		tablero = new Tablero();
	}

	public void jugar() {
		boolean hayGanador = false;
		int numeroDelJugadorQueTira = 0;
		do {
			hayGanador = tirar(jugadores[numeroDelJugadorQueTira]);
			if (!hayGanador) {
				numeroDelJugadorQueTira = (numeroDelJugadorQueTira == 0) ? 1 : 0;
			}
			System.out.println();
			System.out.println(tablero.toString());
		} while (!tablero.estaLleno() && !hayGanador);
		if (hayGanador) {
			String nombreDelGanador = jugadores[numeroDelJugadorQueTira].getNombre();
			System.out.printf("¡¡¡Enhorabuena %s, has ganado!!!", nombreDelGanador);
		} else if (tablero.estaLleno()) {
			System.out.println("El tablero se ha llenado sin haber ganador.");
		}
	}

	private boolean tirar(Jugador jugador) {
		int columna;
		boolean esJugadaGanadora = false;
		boolean salirBucle = false;
		do {
			columna = Consola.leerColumna(jugador);
			try {
				esJugadaGanadora = tablero.introducirFicha(columna, jugador.getColorFichas());
				salirBucle = true;
			} catch (OperationNotSupportedException e) {
				System.out.println(e.getMessage());
			}
		} while (!salirBucle);
		return esJugadaGanadora;
	}
}
