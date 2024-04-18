import java.util.Scanner;
public class Player {
    private ManoDeCartas manoDelJugador;
    private int puntuacionTotal;
    private String estado="noWinner";
    private String nombre;
    public Player(String nombre) {
        this.nombre = nombre;
        estado = "noWinner";
        manoDelJugador=new ManoDeCartas();
    }

    public String getNombre() {
        return nombre;
    }

    public ManoDeCartas getManoDelJugador(){
        return manoDelJugador;
    }
    public Carta getCartaDe(int valor, String palo){
        return manoDelJugador.getCartaDe(valor, palo);
    }
    public void AgregarAMano(Carta laCarta){
        manoDelJugador.agregarCartaAMano(0,laCarta);
    }
    public void mostrarMano() {
        for (int i = 0; i < manoDelJugador.getSizeDeMano(); i++) {
            System.out.println(manoDelJugador.getCartaDeMano(i));
            //manoDelJugador.getCartaDeMano(i).mostrarEnCanvas();
        }

    }
    public void añadirPuntuacion(int puntos){
        puntuacionTotal += puntos;
    }

    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public boolean intentarJugarCarta(Tablero tablerin, Carta laCarta){
        boolean pudoJugarCarta;
        pudoJugarCarta=tablerin.sePuedeJugarLaCarta(laCarta);
        return pudoJugarCarta;
    }
}
