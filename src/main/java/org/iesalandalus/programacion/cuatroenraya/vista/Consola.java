package org.iesalandalus.programacion.cuatroenraya.vista;

import org.iesalandalus.programacion.cuatroenraya.modelo.Ficha;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.cuatroenraya.modelo.Tablero;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private Consola() {
		// Constructor privado para evitar que se puedan crear instancias de esta clase
	}

	public static String leerNombre() {
		String nombre;
		do {
			System.out.print("Introduce el nombre del jugador: ");
			nombre = Entrada.cadena();
			if (nombre == null || nombre.trim().equals("")) {
				System.out.println("ERROR: Nombre no válido, vuelve a introducirlo.");
			}
		} while (nombre == null || nombre.trim().equals(""));
		return nombre;
	}

	public static Ficha elegirColorFichas() {
		int opcion;
		do {
			System.out.print("Elige el color de tus fichas (0 - AZUL, 1 - VERDE): ");
			opcion = Entrada.entero();
			if (opcion != 0 && opcion != 1) {
				System.out.println("ERROR: Debe ser 0 para azul y 1 para verde.");
			}
		} while (opcion != 0 && opcion != 1);
		Ficha ficha = null;
		switch (opcion) {
		case 0:
			ficha = Ficha.AZUL;
			break;
		case 1:
			ficha = Ficha.VERDE;
			break;
		default:
			break;
		}
		return ficha;
	}

	public static Jugador leerJugador() {
		System.out.println("Introduce los datos del PRIMER jugador");
		String nombre = leerNombre();
		Ficha colorFichas = elegirColorFichas();
		return new Jugador(nombre, colorFichas);
	}

	public static Jugador leerJugador(Ficha colorFicha) {
		System.out.println("Introduce los datos del SEGUNDO jugador");
		String nombre = leerNombre();
		return new Jugador(nombre, colorFicha);
	}

	public static int leerColumna(Jugador jugador) {
		String nombreJugador = jugador.getNombre();
		final int limiteColumnas = Tablero.COLUMNAS - 1;
		int columnaSeleccionada;
		do {
			System.out.printf("%s, introduce la columna en la que deseas introducir la ficha (1 - %d): ", nombreJugador,
					limiteColumnas + 1);
			columnaSeleccionada = Entrada.entero() - 1;
			if (columnaSeleccionada < 0 || columnaSeleccionada > limiteColumnas) {
				System.out.println("ERROR: Columna no válida.");
			}
		} while (columnaSeleccionada < 0 || columnaSeleccionada > limiteColumnas);
		return columnaSeleccionada;
	}

}
