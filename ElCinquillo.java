import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;


// Clase que representa el modo de juego
// ElCinquillo que se puede jugar con una
// baraja española
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

    // Método en el cual se empieza el juego.
    public void jugarElCinquillo() {
        prepararJuego();
        while(!juegoFinalizado){
            prepararRonda();
            jugarRonda();
            comprobarEstadoJuego();
            if (!juegoFinalizado)
                mostrarPuntuacionesGenerales();
            tableroParajugar.limpiarTablero();
        }
        System.out.println("Fin del Juego");
    }

    // Método que se encarga de determinar si
    // el juego cumple con las condiciones para
    // darse por finalizado, esto ocurre si
    // los puntos de un jugador son mayores al
    // puntaje acordado al inicio.
    private void comprobarEstadoJuego() {
        Player ganador = null;
        int puntos = 0;
        for (int i = 0; i < numeroDeJugadores; i++){
            if (players.get(i).getPuntuacionTotal() >= puntuacionMax && players.get(i).getPuntuacionTotal() > puntos){
                ganador = players.get(i);
                puntos = players.get(i).getPuntuacionTotal();
            }
        }
        if (ganador != null){
            JOptionPane.showMessageDialog(null, ganador.getNombre() + " ha ganado la partida con "
                    + ganador.getPuntuacionTotal() + " puntos");
            mostrarPuntuacionesGenerales();
            juegoFinalizado = true;
        }
    }

    // Método que muestra un popup informativo con los
    // puntajes de todos los jugadores.
    private void mostrarPuntuacionesGenerales() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Puntuaciones Generales \n");
        players.forEach(
                player -> {
                    sb.append(player.getNombre()).append(" - ").append(player.getPuntuacionTotal()).append("\n");
                }
        );
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Método de bucle de una ronda de juego
    // es donde ocurre la mayoría de las cosas
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
                // Mientras que no se haya seleccionado una carta
                // ponemos en espera el hilo que se encarga de la
                // ejecución del juego
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

    // Se comprueba si el jugador del turno actual
    // ya no tiene más cartas, en caso de ser así
    // se da por finalizada la ronda.
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

    // Prepara las variables necesarias para un nuevo juego
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

    // Método que muestra un menú con una entrada para determinar
    // el puntaje con el cual se dará por terminado el juego
    private void seleccionarPuntuacionMax() {
        int maxPuntuacion;
        do {
             maxPuntuacion = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la puntuación máxima (mayor a 0): "));
        } while (maxPuntuacion <= 0);
        puntuacionMax = maxPuntuacion;
    }

    // Método que se encarga de preparar las variables
    // de una nueva ronda
    private void prepararRonda(){
        rondaFinalizada = false;
        tableroParajugar = new Tablero();
        barajaParaJugar = new Baraja();
        barajaParaJugar.remover8sY9s();
        barajaParaJugar.barajear();
        cartasParaJugador = barajaParaJugar.getSizeBaraja() / numeroDeJugadores;
        repartirCartas();
        JOptionPane.showMessageDialog(null, "Inicio de una nueva ronda");
        agregar5AlCentro();
    }

    // Método que recibe la posicion del cursor e intenta colocar
    // la carta más cercana a la posición del cursor y dentro de
    // ciertos límites de distancia, también se puede clickear
    // la carta en blanco para pasar turno
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
        Canvas.getCanvas().redraw();
    }

    // Método que se encarga de girar entre los turnos
    private void pasarTurno(){
        JOptionPane.showMessageDialog(null,"Se acabó el turno de " + players.get(turnoActual).getNombre());
        seleccionadoCarta = true;
        if (turnoActual == numeroDeJugadores - 1)
            turnoActual = 0;
        else
            turnoActual++;
        JOptionPane.showMessageDialog(null,"Sigue el turno de " + players.get(turnoActual).getNombre());
    }

    // Método que muestra un menú de selección de número de jugadores
    // y permite ingresar nombres para cada uno de ellos
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

    // Método que se encarga de repartir las cartas a cada
    // uno de los jugadores según cuantas cartas le toquen
    // a cada uno, esto depende del número de jugadores
    private void repartirCartas() {
        // Aplicación de lambda en el cual se itera por todos
        // los jugadores y se le otorga n cantidad de cartas.
        players.forEach(
                player -> {
                    player.limpiarMano();
                    for (int j = 0; j < cartasParaJugador; j++) {
                        player.AgregarAMano(barajaParaJugar.getCarta(0));
                        barajaParaJugar.removerCartaDeLaBaraja(0);
                    }
                }
        );
    }

    // Método que se encarga de mostrar la mano del jugador
    // en turno actual en la parte inferior del canvas.
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

    // Método que se encarga de ocultar las cartas
    // del jugador en turno actual en el Canvas.
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
        Canvas.getCanvas().redraw();
    }

    // Método que se llama cada vez que el cursor cambia de posición
    // si hay una carta cerca de ciertos limites esta se levanta un
    // poco para demostrar que está siendo seleccionada
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

    // Método que busca el jugador que tiene el 5 de oros
    // lo coloca y hace que el turno empiece con el
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