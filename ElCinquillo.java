import java.util.ArrayList;
import java.util.Scanner;
public class ElCinquillo {
    private int numeroDeJugadores;
    private ArrayList<Player> players;
    private Scanner scanner; 
    private Baraja barajaParaJugar;
    private int cartasParaJugador;
    private int turnoActual;

    public ElCinquillo() {
        players = new ArrayList<Player>();
        scanner = new Scanner(System.in);
        numeroDeJugadores = 0;
        turnoActual = 0;
        barajaParaJugar = new Baraja();

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
//        System.out.println(arrayDeOros);
//
//        colocarCarta();
//        System.out.println(arrayDeEspadas);
//        System.out.println(arrayDeCopas);
//        System.out.println(arrayDeBastos);
//        System.out.println(arrayDeOros);
//        mostrarManosDeJugador();
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

}