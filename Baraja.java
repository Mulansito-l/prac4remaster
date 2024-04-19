import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

// Esta clase es la representación de la
// baraja española con todas sus cartas
public class Baraja {
    private ArrayList<Carta> cartas;
    public Baraja(){
        cartas =new ArrayList<Carta>();
        llenarBaraja(); //para que se llene en cuanto se crea una baraja
    }


    // Se encarga de llenar la baraja
    // con cada una de las correspondientes
    // cartas de una baraja española
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
                Carta temp = new Carta(j,paloTemp, x + (j * 200), y + (i * 400));
                temp.cambiarTamaño(150);
                cartas.add(temp);
            }
        }
    }

    // Regrese la carta en una posición especifica
    public Carta getCarta(int posicionDeCarta){
        return cartas.get(posicionDeCarta);
    }

    // Método que elimina la carta en la posición especificada
    public void removerCartaDeLaBaraja(int posicionDeLaCarta){
        cartas.remove(posicionDeLaCarta);
    }

    public int getSizeBaraja(){
        return cartas.size();
    }

    // Método que mezcla la baraja
    public void barajear(){
        Collections.shuffle(cartas);
    }


    // Método que se encarga de eliminar los 8s y 9s para
    // una partida de ElCinquillo
    public void remover8sY9s(){
        // Implementación de lambda que filtra aquellas cartas que
        // no sean del valor 8 o 9
        cartas = cartas.stream()
                .filter(carta -> carta.getValor() != 8 && carta.getValor() != 9)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
