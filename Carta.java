// La clase carta es una representación
// de una carta de la baraja española
// cuenta con valor y palo

public class Carta {
    private int valor;
    private String palo;
    private boolean esVisible;
    private Sprite image;
    private int xPosition;
    private int yPosition;
    private boolean resaltada;

    // Constructor de carta blanca, tiene un uso muy especifico
    // solo se utiliza una vez.
    public Carta(int xPosition, int yPosition){
        esVisible=true;
        this.valor=0;
        this.palo="Blank";
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        String nombreImagen ="resources/" + palo + ".png";
        image = new Sprite(nombreImagen,xPosition,yPosition);
        this.cambiarTamaño(150);
    }

    // Crear carta con posición dada, también se crea
    // un nuevo sprite con la imagen que esté en
    // la ruta resources/ con el palo y valor
    // en formato png
    public Carta(int valor, String palo, int xPosition, int yPosition){
        esVisible=true;
        this.valor=valor;
        this.palo=palo;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        String nombreImagen ="resources/" + palo + valor + ".png";
        image = new Sprite(nombreImagen,xPosition,yPosition);
    }

    public int getValor() {
        return valor;
    }

    public String getPalo() {
        return palo;
    }

    public void setResaltada(boolean resaltada) {
        this.resaltada = resaltada;
    }


    // Método que se encarga de actualizar la posición de la carta
    // y con ello la posición de su sprite
    public void setPosition(int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        image.setXPosition(xPosition);
        image.setYPosition(yPosition);
    }

    public int getxPosition(){return this.xPosition;}
    public int getyPosition(){return this.yPosition;}

    // Método que se encarga de dibujar la carta en el canvas
    // si esta resaltada se dibuja un poco más arriba para
    // servir como pista visual
    public void mostrarEnCanvas(){
        if (resaltada) {
            if (palo.equals("Blank")) {
                this.cambiarTamaño(200);
                image.setXPosition(this.xPosition);
                image.setYPosition(this.yPosition);
            }
            else {
                image.setXPosition(this.xPosition);
                image.setYPosition(this.yPosition - 80);
            }
        }
        else {
            if (palo.equals("Blank")) {
                this.cambiarTamaño(150);
                image.setXPosition(this.xPosition);
                image.setYPosition(this.yPosition);
            }
            else {
                image.setXPosition(this.xPosition);
                image.setYPosition(this.yPosition);
            }
        }
        image.setVisible(true);
        Canvas.getCanvas().draw(this, image);
        esVisible = true;
    }

    // Método que cambia el tamaño de la imagen de la carta
    // el tamañoMaximoPixeles representa el maximo de pixeles
    // en el lado más grande la imagen, de esta manera
    // se respeta la relación de aspecto del sprite
    public void cambiarTamaño(int tamañoMaximoPixeles){
        image.changeSize(tamañoMaximoPixeles);
    }

    // Método que se encarga de ocultar la carta en el Canvas.
    public void ocultarEnCanvas(){
        image.setVisible(false);
        esVisible = false;
    }

    public String toString() {
        if (!esVisible) {
            return "[     ]";
        }
        return valor + " de " + palo;

    }
}
