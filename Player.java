import java.util.Scanner;
public class Player {
    private ManoDeCartas manoDelJugador;
    private int ladoElegido;
    Scanner scanner=new Scanner(System.in);
    private boolean EnTurno;
    private int Turno;
    private int puntuacionTotal;
    private int puntuacionDeRonda=0;
    private String estado="noWinner";
    public Player() {
        EnTurno = true;
        estado = "noWinner";
        manoDelJugador=new ManoDeCartas();
    }
    public ManoDeCartas getManoDelJugador(){
        return manoDelJugador;
    }
    public void AgregarAMano(Carta laCarta){
        manoDelJugador.agregarCartaAMano(0,laCarta);
    }
    public void setTurno(int Turno){
        this.Turno=Turno;
    }
    public void mostrarMano(){
        for (int i=0;i<manoDelJugador.getSizeDeMano();i++) {
            System.out.println(manoDelJugador.getCartaDeMano(i));
            //manoDelJugador.getCartaDeMano(i).mostrarEnCanvas();
        }

    }
}
