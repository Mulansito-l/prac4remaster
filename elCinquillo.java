import java.util.ArrayList;
import java.util.Scanner;
public class elCinquillo {
    private int numeroDeJugadores;
    private ArrayList<Player> players;
    private Scanner scanner; 
    private Baraja barajaParaJugar;
    private int cartasParaJugador;
    private int turnoActual;
    private ArrayList <Carta> arrayDeOros;
    private ArrayList <Carta> arrayDeBastos;
    private ArrayList <Carta> arrayDeEspadas;
    private ArrayList <Carta> arrayDeCopas;
    public elCinquillo() {
        players = new ArrayList<Player>();
        scanner = new Scanner(System.in);
        numeroDeJugadores = 0;
        turnoActual = 0;
        barajaParaJugar = new Baraja();
        arrayDeOros =new ArrayList<Carta>();
        arrayDeBastos =new ArrayList<Carta>();
        arrayDeEspadas =new ArrayList<Carta>();
        arrayDeCopas =new ArrayList<Carta>();
    }

    public void jugarElCinquillo() {
        generarJugadores();
        barajaParaJugar.remover8sY9s();
        barajaParaJugar.barajear();
        cartasParaJugador = barajaParaJugar.getSizeBaraja() / numeroDeJugadores;

        System.out.println("El numero de cartas para cada jugador sera: " + cartasParaJugador);
        repartirCartas();
        mostrarManosDeJugador();
        agregar5AlCentro();
        System.out.println(arrayDeOros);

        colocarCarta();
        System.out.println(arrayDeEspadas);
        System.out.println(arrayDeCopas);
        System.out.println(arrayDeBastos);
        System.out.println(arrayDeOros);
        mostrarManosDeJugador();
    }

    public void generarJugadores() {
        do {
            System.out.println("Cuantos jugadores habra en la partida (2-6)");
            numeroDeJugadores = scanner.nextInt();
            scanner.nextLine();
            if (numeroDeJugadores < 2 || numeroDeJugadores > 6) {
                System.out.println("Ingrese un numero valido de jugadores");
            }
        } while (numeroDeJugadores < 2 || numeroDeJugadores > 6);
        for (int i = 0; i < numeroDeJugadores; i++) {
            players.add(new Player(i));
        }
    }

    public void repartirCartas() {
        for (Player player : players) {
            for (int j = 0; j < cartasParaJugador; j++) {
                player.AgregarAMano(barajaParaJugar.getCarta(0));
                barajaParaJugar.removerCartaDeLaBaraja(0);
            }
        }
    }

    public void mostrarManosDeJugador() {
        int i = 1;
        for (Player player : players) {
            System.out.println("La mano del jugador " + i + " es");
            player.mostrarMano();
            System.out.println("");
            i++;
        }
    }
    public void agregar5AlCentro() {
        boolean bandera = false;
        int j;
        int sizeManoEnTurno;
        int valorCarta;
        String paloCarta;
        Carta carta;

        for (int i=0; i<players.size(); i++) {

            j=0;

            ManoDeCartas mano = players.get(i).getManoDelJugador();
            sizeManoEnTurno = mano.getSizeDeMano();

            while (!bandera && j< sizeManoEnTurno ) {

                carta = players.get(i).getManoDelJugador().getCartaDeMano(j);
                valorCarta = carta.getValor();
                paloCarta = carta.getPalo();

                if ( valorCarta == 5 && (paloCarta.equals("Oros"))) {

                    arrayDeOros.add(carta);
                    players.get(i).getManoDelJugador().removerCartaDeMano(j);
                    bandera = true;
                    turnoActual=players.get(i).getTurno(); //el jugador que tenga el 5 de oros, tendra el primer turno
                }
                j++;
            }
        }
    }

    public boolean buscarOtro5() {
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

    }

    public boolean ponerCartaAlPrincipio(){
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

    public boolean ponerCartaAlFinal(){
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

    //método para poder buscar si el jugador en turno tiene un 5,
    //sino va a colocar al prinpicio o al final de una escalera
    public void colocarCarta(){
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

    }

}