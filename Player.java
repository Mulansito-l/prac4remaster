import java.util.Scanner;
public class Player {
    private ManoDeCartas manoDelJugador;
    private int ladoElegido;
    Scanner scanner=new Scanner(System.in);
    private boolean enTurno;
    private int turno;
    private int puntuacionTotal;
    private int puntuacionDeRonda=0;
    private String estado="noWinner";
    public Player(int turno) {
        enTurno = true;
        estado = "noWinner";
        this.turno = turno;
        manoDelJugador=new ManoDeCartas();
    }

    public int getTurno(){
        return turno;
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
    public void setTurno(int Turno){
        this.turno =Turno;
    }
    public void mostrarMano(){
        for (int i=0;i<manoDelJugador.getSizeDeMano();i++) {
            System.out.println(manoDelJugador.getCartaDeMano(i));
            //manoDelJugador.getCartaDeMano(i).mostrarEnCanvas();
        }

    }

    public boolean intentarJugarCarta(Tablero tablerin, Carta laCarta){
        boolean pudoJugarCarta;
        pudoJugarCarta=tablerin.sePuedeJugarLaCarta(laCarta);
        return pudoJugarCarta;
    }
}
