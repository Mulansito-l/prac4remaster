public class Carta {
    private int valor;
    private String palo;
    private boolean esVisible;
    private Sprite image;
    private int xPosition;
    private int yPosition;
    private boolean resaltada;

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
        String nombreImagen ="resources/" + palo + valor + ".png";
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

    public boolean isResaltada() {
        return resaltada;
    }

    public void setResaltada(boolean resaltada) {
        this.resaltada = resaltada;
    }

    public void setxPosition(int xPosition){ this.xPosition = xPosition;}
    public void setyPosition(int yPosition){ this.yPosition = yPosition;}
    public void setPosition(int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        image.setXPosition(xPosition);
        image.setYPosition(yPosition);
    }

    public int getxPosition(){return this.xPosition;}
    public int getyPosition(){return this.yPosition;}

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

    public void cambiarTamaño(int tamañoMaximoPixeles){
        image.changeSize(tamañoMaximoPixeles);
    }

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
