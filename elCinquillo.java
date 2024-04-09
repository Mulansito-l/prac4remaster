import java.util.ArrayList;
import java.util.Scanner;
public class elCinquillo {
    private int NumeroDeJugadores;
    private ArrayList<Player> Players;
    private Scanner scanner;
    private Baraja barajaParaJugar;
    private int cartasParaJugador;
    private ArrayList <Carta>ArrayDeOros;
    private ArrayList <Carta>ArrayDeBastos;
    private ArrayList <Carta>ArrayDeEspadas;
    private ArrayList <Carta>ArrayDeCopas;
    public elCinquillo() {
        Players = new ArrayList<Player>();
        scanner = new Scanner(System.in);
        NumeroDeJugadores = 0;
        barajaParaJugar = new Baraja();
        ArrayDeOros=new ArrayList<Carta>();
        ArrayDeBastos=new ArrayList<Carta>();
        ArrayDeEspadas=new ArrayList<Carta>();
        ArrayDeCopas=new ArrayList<Carta>();
    }

    public void jugarElCinquillo() {
        generarJugadores();
        barajaParaJugar.remover8sY9s();
        barajaParaJugar.barajear();
        cartasParaJugador = barajaParaJugar.getSizeBaraja() / Players.size();

        System.out.println("El numero de cartas para cada jugador sera: " + cartasParaJugador);
        repartirCartas();
        mostrarManosDeJugador();
        agregar5AlCentro();
        System.out.println(ArrayDeOros);
    }

    public void generarJugadores() {
        do {
            System.out.println("Cuantos jugadores habra en la partida (2-6)");
            NumeroDeJugadores = scanner.nextInt();
            scanner.nextLine();
            if (NumeroDeJugadores < 2 || NumeroDeJugadores > 6) {
                System.out.println("Ingrese un numero valido de jugadores");
            }
        } while (NumeroDeJugadores < 2 || NumeroDeJugadores > 6);
        for (int i = 0; i < NumeroDeJugadores; i++) {
            Players.add(new Player());
        }
    }

    public void repartirCartas() {
        for (Player player : Players) {
            for (int j = 0; j < cartasParaJugador; j++) {
                player.AgregarAMano(barajaParaJugar.getCarta(0));
                barajaParaJugar.removerCartaDeLaBaraja(0);
            }
        }
    }

    public void mostrarManosDeJugador() {
        int i = 1;
        for (Player player : Players) {
            System.out.println("La mano del jugador " + i + " es");
            player.mostrarMano();
            System.out.println("");
            i++;
        }
    }
    public void agregar5AlCentro() {
        boolean bandera = false;
        int j;
        for (Player player : Players) {
            j=0;
            while (!bandera && j<player.getManoDelJugador().getSizeDeMano()) {
                if (player.getManoDelJugador().getCartaDeMano(j).getValor() == 5 && (player.getManoDelJugador().getCartaDeMano(j).getPalo().equals("Oros"))) {
                    ArrayDeOros.add(player.getManoDelJugador().getCartaDeMano(j));
                    player.getManoDelJugador().removerCartaDeMano(j);
                    bandera = true;
                    player.setTurno(1);//El jugador que tenga el 5 de oros será el que comience
                }
                j++;
            }
        }
    }
}