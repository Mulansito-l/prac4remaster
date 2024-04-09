import java.util.ArrayList;

public class ManoDeCartas {
    private ArrayList <Carta> Mano;
    public ManoDeCartas(){
        Mano=new ArrayList<Carta>();
    }
    public void agregarCartaAMano(int Posicion, Carta laCarta){
        Mano.add(0,laCarta);
    }

    public String toString() {
        String ManoCadena = "";

        for (Carta carta : Mano) {
            ManoCadena = ManoCadena + carta;
        }
        return ManoCadena;
    }
    public int getSizeDeMano(){
        int sizeDeMano=Mano.size();
        return sizeDeMano;
    }
    public Carta getCartaDeMano(int posicionDeCarta){
        return Mano.get(posicionDeCarta);
    }
    public void removerCartaDeMano(int posicionDeCarta) {
        Mano.remove(posicionDeCarta);

    }
}
