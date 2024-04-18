import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class ElCinquillo {
    private int numeroDeJugadores;
    private ArrayList<Player> players;
    private Scanner scanner; 
    private Baraja barajaParaJugar;
    private int cartasParaJugador;
    private int turnoActual;
    private Tablero tableroParajugar;
    private boolean juegoFinalizado;
    private boolean rondaFinalizada;
    private volatile boolean seleccionadoCarta;
    private Carta cartaPasarTurno;
    private int puntuacionMax;

    public ElCinquillo() {
        scanner = new Scanner(System.in);
    }

    public void jugarElCinquillo() {
        prepararJuego();
        while(!juegoFinalizado){
            prepararRonda();
            jugarRonda();
            comprobarEstadoJuego();
            if (!juegoFinalizado)
                mostrarPuntuacionesGenerales();
        }
        System.out.println("Fin del Juego");
    }

    private void comprobarEstadoJuego() {
        Player ganador = null;
        int puntuacionMax = 0;
        for (int i = 0; i < numeroDeJugadores; i++){
            if (players.get(i).getPuntuacionTotal() > puntuacionMax){
                puntuacionMax = players.get(i).getPuntuacionTotal();
                ganador = players.get(i);
            }
        }
        if (ganador != null){
            JOptionPane.showMessageDialog(null, ganador.getNombre() + " ha ganado la partida con "
                    + ganador.getPuntuacionTotal() + " puntos");
            mostrarPuntuacionesGenerales();
            juegoFinalizado = true;
        }
    }

    private void mostrarPuntuacionesGenerales() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Puntuaciones Generales \n");
        for (int i = 0; i < numeroDeJugadores; i++){
            Player jugadorTemp = players.get(i);
            sb.append(jugadorTemp.getNombre()).append(" - ").append(jugadorTemp.getPuntuacionTotal()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void jugarRonda(){
        cartaPasarTurno.mostrarEnCanvas();
        while (!rondaFinalizada){
            seleccionadoCarta = false;
            System.out.println("Es el turno de " + players.get(turnoActual).getNombre());
            mostrarManoTurnoActualEnCanvas();
            System.out.println("Seleccione la carta que desea colocar en el tablero: ");
            tableroParajugar.mostrarEnCanvas();
            Canvas.getCanvas().redraw();
            while (!seleccionadoCarta) {
                Thread.onSpinWait();
            }
            System.out.println("Se ha colocado una carta");
            ocultarManoTurnoActualEnCanvas();
            Canvas.getCanvas().redraw();
            comprobarEstadoRonda();
            if (!rondaFinalizada)
                pasarTurno();
        }
    }

    private void comprobarEstadoRonda() {
        int puntuacion = 0;
        if (players.get(turnoActual).getManoDelJugador().getSizeDeMano() == 0){
            for (int i = 0; i < numeroDeJugadores; i++){
                if (i != turnoActual){
                    puntuacion += players.get(i).getManoDelJugador().getSizeDeMano();
                }
            }
            JOptionPane.showMessageDialog(null, players.get(turnoActual).getNombre()
                    + " ha colocado todas sus cartas, gana " + puntuacion + " puntos");
            players.get(turnoActual).añadirPuntuacion(puntuacion);
            rondaFinalizada = true;
        }
    }

    private void prepararJuego(){
        Canvas.getCanvas().setCinquillo(this);
        juegoFinalizado = false;
        players = new ArrayList<Player>();
        cartaPasarTurno = new Carta(50, 50);
        tableroParajugar=new Tablero();
        numeroDeJugadores = 0;
        generarJugadores();
        seleccionarPuntuacionMax();
    }

    private void seleccionarPuntuacionMax() {
        int maxPuntuacion;
        do {
             maxPuntuacion = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la puntuación máxima: "));
        } while (maxPuntuacion < 0);
        puntuacionMax = maxPuntuacion;
    }

    private void prepararRonda(){
        rondaFinalizada = false;
        tableroParajugar = new Tablero();
        barajaParaJugar = new Baraja();
        barajaParaJugar.remover8sY9s();
        barajaParaJugar.barajear();
        cartasParaJugador = barajaParaJugar.getSizeBaraja() / numeroDeJugadores;
        repartirCartas();
        agregar5AlCentro();
    }

    public void seleccionarCarta(int xPos, int yPos){
        Carta cartaMasCercana = null;
        int indiceCarta = -1;
        int distanciaX = 101;
        int distanciaY = 151;
        if (!players.isEmpty()) {
            Player jugador = players.get(turnoActual);
            ManoDeCartas manoAMostrar = jugador.getManoDelJugador();
            for (int i = 0; i < manoAMostrar.getSizeDeMano(); i++) {
                int cartaPosX = manoAMostrar.getCartaDeMano(i).getxPosition();
                int cartaPosY = manoAMostrar.getCartaDeMano(i).getyPosition();
                if (Math.abs(cartaPosX - xPos) < distanciaX && Math.abs(cartaPosY - yPos) < distanciaY) {
                    distanciaX = Math.abs(cartaPosX - xPos);
                    distanciaY = Math.abs(cartaPosY - yPos);
                    cartaMasCercana = manoAMostrar.getCartaDeMano(i);
                    indiceCarta = i;
                }
            }

            if (Math.abs(xPos - cartaPasarTurno.getxPosition()) < 101 && Math.abs(yPos - cartaPasarTurno.getyPosition()) < 151)
                cartaMasCercana = cartaPasarTurno;

            if (cartaMasCercana != null) {
                System.out.println("Intentando colocar " + cartaMasCercana);
                if (cartaMasCercana == cartaPasarTurno){

                }
                else if (tableroParajugar.sePuedeJugarLaCarta(cartaMasCercana)) {
                        manoAMostrar.removerCartaDeMano(indiceCarta);
                        seleccionadoCarta = true;
                        System.out.println("Se ha colocado la carta " + cartaMasCercana);
                }
            }

            if (cartaMasCercana == cartaPasarTurno){
                String[] opciones = {"Si", "No"};
                int op = JOptionPane.showOptionDialog(null,"Está seguro que desea pasar turno?", "Pasar turno",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opciones,opciones[0]);
                System.out.println(op);
                seleccionadoCarta = true;
                System.out.println(players.get(turnoActual) + " ha saltado turno");
            }
        }
        tableroParajugar.mostrarEnCanvas();
    }

    private void pasarTurno(){
        JOptionPane.showMessageDialog(null,"Se acabó el turno de " + players.get(turnoActual).getNombre());
        seleccionadoCarta = true;
        if (turnoActual == numeroDeJugadores - 1)
            turnoActual = 0;
        else
            turnoActual++;
        JOptionPane.showMessageDialog(null,"Sigue el turno de " + players.get(turnoActual).getNombre());
    }

    private void generarJugadores() {
        String[] opciones = {"2 jugadores", "3 jugadores", "4 jugadores", "5 jugadores", "6 jugadores"};
        int opcionSeleccionada = JOptionPane.showOptionDialog(null,"Seleccione la cantidad de jugadores: ", "Selección de Jugadores", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, opciones, opciones[0]);
        System.out.println(opcionSeleccionada);
        switch (opcionSeleccionada){
            case 0:
                numeroDeJugadores = 2;
                break;
            case 1:
                numeroDeJugadores = 3;
                break;
            case 2:
                numeroDeJugadores = 4;
                break;
            case 3:
                numeroDeJugadores = 5;
                break;
            case 4:
                numeroDeJugadores = 6;
        }

        for (int i = 0; i < numeroDeJugadores; i++){
            String[] options = {"OK"};
            String nombre;
            do {
                nombre = (String) JOptionPane.showInputDialog(null, "Ingrese el nombre del jugador #" + (i + 1));
            } while(nombre == null || nombre.isEmpty());
            players.add(new Player(nombre));
        }
    }

    private void repartirCartas() {
        for (Player player : players) {
            for (int j = 0; j < cartasParaJugador; j++) {
                player.AgregarAMano(barajaParaJugar.getCarta(0));
                barajaParaJugar.removerCartaDeLaBaraja(0);
            }
        }
    }

    private void mostrarManoTurnoActualEnCanvas(){
        Player jugador = players.get(turnoActual);
        ManoDeCartas manoAMostrar = jugador.getManoDelJugador();
        int tamañoMano = manoAMostrar.getSizeDeMano();
        int posX = (1920 / 2) - (80 * tamañoMano / 2);
        int posY = 800;

        synchronized(manoAMostrar) {
            for (int i = 0; i < tamañoMano; i++){
                manoAMostrar.getCartaDeMano(i).setPosition(posX + (i * 80), posY);
                manoAMostrar.getCartaDeMano(i).mostrarEnCanvas();
            }
        }
    }

    private void ocultarManoTurnoActualEnCanvas(){
        Player jugador = players.get(turnoActual);
        ManoDeCartas manoAMostrar = jugador.getManoDelJugador();
        int tamañoMano = manoAMostrar.getSizeDeMano();
        int posX = (1920 / 2) - (80 * tamañoMano / 2);
        int posY = 800;

        synchronized(manoAMostrar) {
            for (int i = 0; i < tamañoMano; i++){
                manoAMostrar.getCartaDeMano(i).setPosition(posX + (i * 80), posY);
                manoAMostrar.getCartaDeMano(i).ocultarEnCanvas();
            }
        }
    }

    public void resaltarCarta(int xPos, int yPos){
        Carta cartaMasCercana = null;
        int indiceCarta = -1;
        int distanciaX = 101;
        int distanciaY = 151;
        if (!players.isEmpty()) {
            Player jugador = players.get(turnoActual);
            ManoDeCartas manoAMostrar = jugador.getManoDelJugador();
            for (int i = 0; i < manoAMostrar.getSizeDeMano(); i++) {
                int cartaPosX = manoAMostrar.getCartaDeMano(i).getxPosition();
                int cartaPosY = manoAMostrar.getCartaDeMano(i).getyPosition();
                if (Math.abs(cartaPosX - xPos) < distanciaX && Math.abs(cartaPosY - yPos) < distanciaY) {
                    distanciaX = Math.abs(cartaPosX - xPos);
                    distanciaY = Math.abs(cartaPosY - yPos);
                    cartaMasCercana = manoAMostrar.getCartaDeMano(i);
                    indiceCarta = i;
                }
            }

            for (int i = 0; i < manoAMostrar.getSizeDeMano(); i++){
                if (cartaMasCercana != null && manoAMostrar.getCartaDeMano(i) == cartaMasCercana){
                    cartaMasCercana.setResaltada(true);
                    cartaMasCercana.mostrarEnCanvas();
                }
                else {
                    manoAMostrar.getCartaDeMano(i).setResaltada(false);
                    manoAMostrar.getCartaDeMano(i).mostrarEnCanvas();
                }
            }
        }

        if (Math.abs(xPos - cartaPasarTurno.getxPosition()) < 101 && Math.abs(yPos - cartaPasarTurno.getyPosition()) < 151)
            cartaPasarTurno.setResaltada(true);
        else
            cartaPasarTurno.setResaltada(false);
        cartaPasarTurno.mostrarEnCanvas();
        Canvas.getCanvas().redraw();
    }

    private void agregar5AlCentro() {
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
                    turnoActual=i; //el jugador que tenga el 5 de oros, tendra el primer turno
                    System.out.println(players.get(i).getNombre() + " colocó el 5 de oros");
                    JOptionPane.showMessageDialog(null, players.get(i).getNombre() + " colocó el 5 de oros");
                    pasarTurno();
                }
                j++;
            }
        }
    }
}