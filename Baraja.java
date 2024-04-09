import java.util.ArrayList;
import java.util.Collections;

public class Baraja {
    private ArrayList<Carta> BarajaConCartas;
    public Baraja(){
        BarajaConCartas=new ArrayList<Carta>();
        llenarBaraja(); //para que se llene en cuanto se crea una baraja
    }

    public void llenarBaraja() {
        int x = 0;
        int y = 0;
        String paloTemp="Oros";
        for (int i = 0; i < 4; i++) { //tipo de carta
            paloTemp = switch (i) {
                case 0 -> paloTemp;
                case 1 -> "Copas";
                case 2 -> "Bastos";
                default -> "Espadas";
            };
            for (int j=1;j<13;j++){ //las cartas de la baraja española van del 1 al 12
                BarajaConCartas.add(new Carta(j,paloTemp, x + (j * 200), y + (i * 400)));
            }
        }
    }

    public Carta getCarta(int posicionDeCarta){
        return BarajaConCartas.get(posicionDeCarta);
    }

    public ArrayList<Carta> getCartasDeLaBaraja(int cantidadASacar) {
        ArrayList<Carta> cartasSacadas = new ArrayList<>();

        //solo saca cartas si existe esa cantidad disponible
        if ( cantidadASacar <= getSizeBaraja() ) {
            //las agrega a un array que voy a regresar con las cartas sacadas
            for (int i=0; i<cantidadASacar; i++) {
                cartasSacadas.add(BarajaConCartas.remove(0));
            }
        } else {
            System.out.println("No hay suficientes cartas en la baraja.");
            return null;
        }

        return cartasSacadas;
    }

    public void removerCartaDeLaBaraja(int posicionDeLaCarta){
        BarajaConCartas.remove(posicionDeLaCarta);
    }


    public int getSizeBaraja(){
        return BarajaConCartas.size();
    }

    public void barajear(){
        Collections.shuffle(BarajaConCartas);
    }

    // Metodo temporal para observar todas las cartas
    public void mostrarBarajaEnCanvas(){
        for(Carta carta : BarajaConCartas){
            carta.mostrarEnCanvas();
        }
    }
    public void mostrarBarajaEnTerminal(){
        for (Carta carta : BarajaConCartas){
            System.out.println(carta);
        }
    }
    public void remover8sY9s(){
        for (int i=0;i< BarajaConCartas.size();i++){
            if (getCarta(i).getValor()==8 || getCarta(i).getValor()==9){
                removerCartaDeLaBaraja(i);
                BarajaConCartas.remove(getCarta(i));
                i-=1;
            }
        }
    }
}
