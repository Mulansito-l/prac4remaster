import java.util.Scanner;
// Clase jugador se encarga de almacenar
// datos concernientes al jugador
public class Player {
    private ManoDeCartas manoDelJugador;
    private int puntuacionTotal;
    private String nombre;
    public Player(String nombre) {
        this.nombre = nombre;
        manoDelJugador=new ManoDeCartas();
    }

    public String getNombre() {
        return nombre;
    }

    public ManoDeCartas getManoDelJugador(){
        return manoDelJugador;
    }

    public void AgregarAMano(Carta laCarta){
        manoDelJugador.agregarCartaAMano(0,laCarta);
    }

    // Método que recibe una cantidad puntos y lo añade al jugador actual
    public void añadirPuntuacion(int puntos){
        puntuacionTotal += puntos;
    }

    // Método que limpia la mano del jugador, para añadir
    // las nuevas cartas
    public void limpiarMano(){
        manoDelJugador.limpiar();
    }

    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }
}
