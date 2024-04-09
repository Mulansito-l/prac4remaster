public class Carta {
    private int valor;
    private String palo;
    private boolean esVisible;
    private Sprite image;
    private int xPosition;
    private int yPosition;

    public Carta(int valor, String palo){
        esVisible=true;
        this.valor=valor;
        this.palo=palo;
        xPosition = 0;
        yPosition = 0;
        String nombreImagen = palo + valor + ".png";
        //image = new Sprite(nombreImagen,xPosition,yPosition);
    }

    // Crear carta con posición dada
    public Carta(int valor, String palo, int xPosition, int yPosition){
        esVisible=true;
        this.valor=valor;
        this.palo=palo;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        String nombreImagen = palo + valor + ".png";
        image = new Sprite(nombreImagen,xPosition,yPosition);
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public boolean getEsVisible() {
        return esVisible;
    }

    public void setEsVisible(boolean esVisible) {
        this.esVisible = esVisible;
    }

    public void setxPosition(int xPosition){ this.xPosition = xPosition;}
    public void setyPosition(int yPosition){ this.yPosition = yPosition;}

    public int getxPosition(){return this.xPosition;}
    public int getyPosition(){return this.yPosition;}

    public void mostrarEnCanvas(){
        Canvas.getCanvas().Draw(this, image);
    }

    public void ocultarEnCanvas(){
        Canvas.getCanvas().Erase(this);
    }

    public String toString() {
        if (!esVisible) {
            return "[     ]";
        }
        return valor + " de " + palo;

    }
}
