import java.util.ArrayList;
import java.util.Scanner;
public class ElCinquillo {
    private int numeroDeJugadores;
    private ArrayList<Player> players;
    private Scanner scanner; 
    private Baraja barajaParaJugar;
    private int cartasParaJugador;
    private int turnoActual;
    private Tablero tableroParajugar;

    public ElCinquillo() {
        players = new ArrayList<Player>();
        scanner = new Scanner(System.in);
        tableroParajugar=new Tablero();
        numeroDeJugadores = 0;
        turnoActual = 0;
        barajaParaJugar = new Baraja();

    }

    public void jugarElCinquillo() {
        boolean pasa=true;
        generarJugadores();
        barajaParaJugar.remover8sY9s();
        barajaParaJugar.barajear();
        cartasParaJugador = barajaParaJugar.getSizeBaraja() / numeroDeJugadores;

        System.out.println("El numero de cartas para cada jugador sera: " + cartasParaJugador);
        repartirCartas();
        mostrarManosDeJugador();
        agregar5AlCentro();
        System.out.println(tableroParajugar.getArrayDeOros());

        int j=1;
        for (Player player : players){

            for (int i=0;i<player.getManoDelJugador().getSizeDeMano();i++) {
                if ((player.intentarJugarCarta(tableroParajugar, player.getManoDelJugador().getCartaDeMano(i)))){
                    pasa=false;
                    System.out.println("El jugador"+j+" agrego la carta "+player.getManoDelJugador().getCartaDeMano(i));
                    player.getManoDelJugador().removerCartaDeMano(i);
                    i=0;
                }
            }
            if (pasa) {
                System.out.println("El jugador "+j+" pasa de turno porque no tiene cartas disponibles para poner");
            }
           j++;
        }

        mostrarManosDeJugador();

        System.out.println(tableroParajugar.getArrayDeEspadas());
        System.out.println(tableroParajugar.getArrayDeCopas());
        System.out.println(tableroParajugar.getArrayDeBastos());
       System.out.println(tableroParajugar.getArrayDeOros());

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

                    tableroParajugar.getArrayDeOros().add(carta);
                    players.get(i).getManoDelJugador().removerCartaDeMano(j);
                    bandera = true;
                    turnoActual=players.get(i).getTurno(); //el jugador que tenga el 5 de oros, tendra el primer turno
                }
                j++;
            }
        }
    }
}