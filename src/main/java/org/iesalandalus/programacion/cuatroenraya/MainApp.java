package org.iesalandalus.programacion.cuatroenraya;

import org.iesalandalus.programacion.cuatroenraya.modelo.Ficha;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

public class MainApp {

	public static void main(String[] args) {
		Jugador jugador1 = Consola.leerJugador();
		Ficha colorFichaJugador2 = (jugador1.getColorFichas() == Ficha.AZUL) ? Ficha.VERDE : Ficha.AZUL;
		Jugador jugador2 = Consola.leerJugador(colorFichaJugador2);
		CuatroEnRaya cuatroEnRaya = new CuatroEnRaya(jugador1, jugador2);
		cuatroEnRaya.jugar();
	}

}
