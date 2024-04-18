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
        }return sePudoJugar;
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

    //método para colocar una carta en su array de palo correspondiente,
    //tomando en cuenta que este método intenta colocar arriba de la carta central
    public boolean sePuedeAgregarArriba(Carta laCarta) {
        boolean sePudoAgregar = false;

        if (laCarta.getPalo().equals("Oros")) {

            //si la carta es mayor por 1 con respecto al último valor, se coloca
            if (laCarta.getValor() == arrayDeOros.getLast().getValor() + 1 ||
                    //si la carta es un 7, se brinca directamente al 10 y se coloca
                    (arrayDeOros.getLast().getValor() == 7 && laCarta.getValor() == 10)) {
                arrayDeOros.add(laCarta);
                sePudoAgregar = true;
            }

        }

        else if (!arrayDeBastos.isEmpty() && laCarta.getPalo().equals("Bastos")) {

            if (laCarta.getValor() == arrayDeBastos.getLast().getValor() + 1 ||
                    (arrayDeBastos.getLast().getValor() == 7 && laCarta.getValor() == 10)) {
                 synchronized (arrayDeBastos) {
                     arrayDeBastos.add(laCarta);
                 }
                 sePudoAgregar = true;
             }
        }
        else if (!arrayDeEspadas.isEmpty() && laCarta.getPalo().equals("Espadas")) {

            if (laCarta.getValor() == arrayDeEspadas.getLast().getValor() + 1 ||
                    (arrayDeEspadas.getLast().getValor() == 7 && laCarta.getValor() == 10)) {
                synchronized (arrayDeEspadas) {
                    arrayDeEspadas.add(laCarta);
                }
                sePudoAgregar = true;
            }
        }
        else if (!arrayDeCopas.isEmpty() && laCarta.getPalo().equals("Copas")) {

            if (laCarta.getValor() == arrayDeCopas.getLast().getValor() + 1 ||
                    (arrayDeCopas.getLast().getValor() == 7 && laCarta.getValor() == 10)) {
                synchronized (arrayDeCopas) {
                    arrayDeCopas.add(laCarta);
                }
                sePudoAgregar = true;
            }
        }

        return sePudoAgregar;
        }
    public boolean sePuedeAgregarAbajo(Carta laCarta) {
        boolean sePudoAgregar=false;

        if (laCarta.getPalo().equals("Oros") && laCarta.getValor() == arrayDeOros.getFirst().getValor() - 1) {
            synchronized (arrayDeOros) {
                arrayDeOros.addFirst(laCarta);
            }
            sePudoAgregar=true;
        }
        else if (!arrayDeBastos.isEmpty() && laCarta.getPalo().equals("Bastos")) {
            if (laCarta.getValor() == arrayDeBastos.getFirst().getValor() - 1) {
                synchronized (arrayDeBastos) {
                    arrayDeBastos.addFirst(laCarta);
                }
                sePudoAgregar = true;
            }
        }
        else if (!arrayDeEspadas.isEmpty() && laCarta.getPalo().equals("Espadas")) {
            if ( laCarta.getValor() == arrayDeEspadas.getFirst().getValor() - 1) {
                synchronized (arrayDeEspadas) {
                    arrayDeEspadas.addFirst(laCarta);
                }
                sePudoAgregar = true;
            }
        }
        else if (!arrayDeCopas.isEmpty() && laCarta.getPalo().equals("Copas")) {
            if ( laCarta.getValor() == arrayDeCopas.getFirst().getValor() - 1) {
                synchronized (arrayDeCopas) {
                    arrayDeCopas.addFirst(laCarta);
                }
                sePudoAgregar = true;
            }
        }
        return sePudoAgregar;
    }

    public void mostrarEnCanvas(){
        mostrarArrayEnCanvasEn(arrayDeOros, 300);
        mostrarArrayEnCanvasEn(arrayDeCopas, 700);
        mostrarArrayEnCanvasEn(arrayDeBastos, 1100);
        mostrarArrayEnCanvasEn(arrayDeEspadas, 1500);
    }

    private void mostrarArrayEnCanvasEn(ArrayList<Carta> array,int xPosition){
        int indiceCartaCentral = -1;
        int y = 400;
        for (int i = 0; i < array.size(); i++){
            if (array.get(i).getValor() == 5){
                indiceCartaCentral = i;
            }
        }
            for (Carta carta : array) {
                carta.setResaltada(false);
                carta.setPosition(xPosition, y + 50 * indiceCartaCentral);
                indiceCartaCentral--;
                carta.mostrarEnCanvas();
            }
    }
}
