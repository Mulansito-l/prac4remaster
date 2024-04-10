import java.util.ArrayList;

public class Tablero {

    private ArrayList<Carta> arrayDeOros;
    private ArrayList <Carta> arrayDeBastos;
    private ArrayList <Carta> arrayDeEspadas;
    private ArrayList <Carta> arrayDeCopas;

    public Tablero() {
        arrayDeOros =new ArrayList<Carta>();
        arrayDeBastos =new ArrayList<Carta>();
        arrayDeEspadas =new ArrayList<Carta>();
        arrayDeCopas =new ArrayList<Carta>();
    }

    public ArrayList<Carta> getArrayDeBastos() {
        return arrayDeBastos;
    }
    public ArrayList<Carta> getArrayDeCopas() {
        return arrayDeCopas;
    }

    public ArrayList<Carta> getArrayDeEspadas() {
        return arrayDeEspadas;
    }
    public ArrayList<Carta> getArrayDeOros() {
        return arrayDeOros;
    }
    public boolean sePuedeJugarLaCarta(Carta laCarta){
        boolean sePudoJugar=false;
        if (esUnCinco(laCarta)){
            sePudoJugar=true;
        }

        else if (sePuedeAgregarArriba(laCarta)){
            sePudoJugar=true;
        }
        else if (sePuedeAgregarAbajo(laCarta)){
            sePudoJugar=true;
        }
        return sePudoJugar;
    }
    public boolean esUnCinco(Carta laCarta){
        if (laCarta.getValor()==5){
            switch (laCarta.getPalo()){
                case "Bastos":
                    getArrayDeBastos().add(laCarta);
                    return true;
                case "Espadas":
                    getArrayDeEspadas().add(laCarta);
                    return true;
                //Agregar al de copas
                default:
                    getArrayDeCopas().add(laCarta);
                    return true;
            }
        }
        else {
            return false;
        }
    }

    public boolean sePuedeAgregarArriba(Carta laCarta) {
        boolean sePudoAgregar=false;

        if (laCarta.getPalo().equals("Oros") && laCarta.getValor() == arrayDeOros.getLast().getValor() + 1) {
            arrayDeOros.add(laCarta);
            sePudoAgregar=true;

        }

        else if (!arrayDeBastos.isEmpty()) {
         if (laCarta.getPalo().equals("Bastos") && laCarta.getValor() == arrayDeBastos.getLast().getValor() + 1) {
                arrayDeBastos.add(laCarta);
                sePudoAgregar = true;
            }
        }
        else if (!arrayDeEspadas.isEmpty()) {
            if (laCarta.getPalo().equals("Espadas") && laCarta.getValor() == arrayDeEspadas.getLast().getValor() + 1) {
                arrayDeEspadas.add(laCarta);
                sePudoAgregar = true;
            }
        }
        else if (!arrayDeCopas.isEmpty()) {
            if (laCarta.getPalo().equals("Copas") && laCarta.getValor() == arrayDeCopas.getLast().getValor() + 1) {
                arrayDeCopas.add(laCarta);
                sePudoAgregar = true;
            }
        }

        return sePudoAgregar;
        }
    public boolean sePuedeAgregarAbajo(Carta laCarta) {
        boolean sePudoAgregar=false;

        if (laCarta.getPalo().equals("Oros") && laCarta.getValor() == arrayDeOros.getFirst().getValor() - 1) {
            arrayDeOros.addFirst(laCarta);
            sePudoAgregar=true;
        }
        else if (!arrayDeBastos.isEmpty()) {
            if (laCarta.getPalo().equals("Bastos") && laCarta.getValor() == arrayDeBastos.getFirst().getValor() - 1) {
                arrayDeBastos.addFirst(laCarta);
                sePudoAgregar = true;
            }
        }
        else if (!arrayDeEspadas.isEmpty()) {
            if (laCarta.getPalo().equals("Espadas") && laCarta.getValor() == arrayDeEspadas.getFirst().getValor() - 1) {
                arrayDeEspadas.addFirst(laCarta);
                sePudoAgregar = true;
            }
        }
        else if (!arrayDeCopas.isEmpty()) {
            if (laCarta.getPalo().equals("Copas") && laCarta.getValor() == arrayDeCopas.getFirst().getValor() - 1) {
                arrayDeCopas.addFirst(laCarta);
                sePudoAgregar = true;
            }
        }
        return sePudoAgregar;
    }

    /*public boolean buscarOtro5() {
        boolean bandera = false;
        Player jugadorActual = players.get(turnoActual);
        ManoDeCartas mano = jugadorActual.getManoDelJugador();
        int sizeMano = mano.getSizeDeMano();
        int i = 0; //para controlar

        while (!bandera && i < sizeMano) {
            Carta carta = mano.getCartaDeMano(i);

            if (carta.getValor() == 5) {
                switch (carta.getPalo()){
                    case "Copas" -> arrayDeCopas.add(carta);
                    case "Bastos" -> arrayDeBastos.add(carta);
                    case "Espadas" -> arrayDeEspadas.add(carta);
                }

                mano.removerCartaDeMano(i); // Remueve la carta de la mano del jugador actual
                bandera = true;
            }
            i++;
        }

        if (!bandera) {
            System.out.println("No se encontró ninguna carta con valor 5 en la mano del jugador actual");
        }

        return bandera;

    }*/

    /*public boolean ponerCartaAlPrincipio(){
        boolean bandera = false;
        Player jugadorActual = players.get(turnoActual);
        ManoDeCartas mano = jugadorActual.getManoDelJugador();
        int sizeMano = mano.getSizeDeMano();
        int i = 0; //para controlar

        while (!bandera && i < sizeMano) {
            Carta carta = mano.getCartaDeMano(i);
            String paloCarta = carta.getPalo();
            int valorCarta = carta.getValor();
            Carta cartaAux;
            int valorCartaAux;

            switch (paloCarta){
                case "Oros":

                    if (!arrayDeOros.isEmpty()){

                        cartaAux = arrayDeOros.get(0);
                        valorCartaAux = cartaAux.getValor();

                        if ( valorCarta == (valorCartaAux-1) ){
                            arrayDeOros.add(0,carta);
                            mano.removerCartaDeMano(i);
                            bandera = true;
                        }

                    } else {
                        System.out.println("No se pueden colocar cartas al principio de la escalera de oros");
                    }

                    break;

                case "Bastos":

                    if (!arrayDeBastos.isEmpty()){

                        cartaAux = arrayDeBastos.get(0);
                        valorCartaAux = cartaAux.getValor();

                        if ( valorCarta == (valorCartaAux-1) ){
                            arrayDeBastos.add(0,carta);
                            mano.removerCartaDeMano(i);
                            bandera = true;
                        }

                    } else {
                        System.out.println("No se pueden colocar cartas al principio de la escalera de bastos");
                    }

                    break;

                case "Espadas":

                    if (!arrayDeEspadas.isEmpty()){

                        cartaAux = arrayDeEspadas.get(0);
                        valorCartaAux = cartaAux.getValor();

                        if ( valorCarta == (valorCartaAux-1) ){
                            arrayDeEspadas.add(0,carta);
                            mano.removerCartaDeMano(i);
                            bandera = true;
                        }

                    } else {
                        System.out.println("No se pueden colocar cartas al principio de la escalera de espadas");
                    }

                    break;

                case "Copas":

                    if (!arrayDeCopas.isEmpty()) {

                        cartaAux = arrayDeCopas.get(0);
                        valorCartaAux = cartaAux.getValor();

                        if ( valorCarta == (valorCartaAux-1) ){
                            arrayDeCopas.add(0,carta);
                            mano.removerCartaDeMano(i);
                            bandera = true;
                        }

                    } else {
                        System.out.println("No se pueden colocar cartas al principio de la escalera de copas");
                    }

                    break;

            }

            i++;

        }

        if (!bandera) {
            System.out.println("No se encontró ninguna carta para colocar al principio de cualquier escalera");
        }

        return bandera;

    }
*/
   /* public boolean ponerCartaAlFinal(){
        boolean bandera = false;
        Player jugadorActual = players.get(turnoActual);
        ManoDeCartas mano = jugadorActual.getManoDelJugador();
        int sizeMano = mano.getSizeDeMano();
        int i = 0; //para controlar

        while (!bandera && i < sizeMano) {
            Carta carta = mano.getCartaDeMano(i);
            String paloCarta = carta.getPalo();
            int valorCarta = carta.getValor();
            Carta cartaAux;
            int valorCartaAux;
            int cantidaDeCartasEn;


            switch (paloCarta){
                case "Oros":

                    if (!arrayDeOros.isEmpty()){

                        cantidaDeCartasEn = arrayDeOros.size();
                        cartaAux = arrayDeOros.get( cantidaDeCartasEn - 1 );
                        valorCartaAux = cartaAux.getValor();

                        if ( valorCarta == (valorCartaAux+1) ){
                            arrayDeOros.add(cantidaDeCartasEn,carta);
                            mano.removerCartaDeMano(i);
                            bandera = true;
                        }

                    } else {
                        System.out.println("No se pueden colocar cartas al final de la escalera de oros");
                    }

                    break;

                case "Bastos":

                    if (!arrayDeBastos.isEmpty()) {

                        cantidaDeCartasEn = arrayDeBastos.size();
                        cartaAux = arrayDeBastos.get( cantidaDeCartasEn -1 );
                        valorCartaAux = cartaAux.getValor();

                        if ( valorCarta == (valorCartaAux+1) ){
                            arrayDeBastos.add(cantidaDeCartasEn,carta);
                            mano.removerCartaDeMano(i);
                            bandera = true;
                        }

                    } else {
                        System.out.println("No se pueden colocar cartas al final de la escalera de bastos");
                    }

                    break;

                case "Espadas":

                    if (!arrayDeEspadas.isEmpty()) {
                        cantidaDeCartasEn = arrayDeEspadas.size();
                        cartaAux = arrayDeEspadas.get( cantidaDeCartasEn -1 );
                        valorCartaAux = cartaAux.getValor();

                        if ( valorCarta == (valorCartaAux+1) ){
                            arrayDeEspadas.add(cantidaDeCartasEn,carta);
                            mano.removerCartaDeMano(i);
                            bandera = true;
                        }
                    } else {
                        System.out.println("No se pueden colocar cartas al final de la escalera de espadas");
                    }

                    break;

                case "Copas":

                    if (!arrayDeCopas.isEmpty()){

                        cantidaDeCartasEn = arrayDeCopas.size();
                        cartaAux = arrayDeCopas.get( cantidaDeCartasEn -1 );
                        valorCartaAux = cartaAux.getValor();

                        if ( valorCarta == (valorCartaAux+1) ){
                            arrayDeCopas.add(cantidaDeCartasEn,carta);
                            mano.removerCartaDeMano(i);
                            bandera = true;
                        }

                    } else {
                        System.out.println("No se pueden colocar cartas al final de la escalera de copas");
                    }

                    break;

            }

            i++;

        }

        if (!bandera) {
            System.out.println("No se encontró ninguna carta para colocar al final de cualquier escalera");
        }

        return bandera; //regresa true cuando se coloca
    }
*/
    //método para poder buscar si el jugador en turno tiene un 5,
    //sino va a colocar al prinpicio o al final de una escalera

    /*public void colocarCarta(){
        boolean resultado=false;
        resultado = buscarOtro5();

        if (resultado){
            System.out.println("Se pudo colocar la carta");
        } else {
            System.out.println("No pudiste colocar la carta en ningun lado");
        }

        resultado = ponerCartaAlPrincipio();

        if (resultado){
            System.out.println("Se pudo colocar la carta");
        } else {
            System.out.println("No pudiste colocar la carta en ningun lado");
        }

        resultado = ponerCartaAlFinal();

        if (resultado){
            System.out.println("Se pudo colocar la carta");
        } else {
            System.out.println("No pudiste colocar la carta en ningun lado");
        }

    }*/
}
