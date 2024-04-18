import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Sprite{
    private int xPosition;
    private int yPosition;
    private BufferedImage image = null;
    private boolean visible;

    // Para crear un sprite simplemente coloca
    // el nombre de la imagen que se encuentra
    // en el directorio app/src/main/resources/
    
    public Sprite(String imageName, int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        try{
            image = ImageIO.read(new File(imageName));
        }
        catch(Exception e){
            System.out.println("No se pudo cargar la imagen: " + e.toString());
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public BufferedImage getImage(){
        return image;
    }

    public void changeSize(int maxSizePixels){
        image = Scalr.resize(image,maxSizePixels);
    }
    public int getXPosition(){
        return xPosition;}
    public int getYPosition(){
        return yPosition;}
    public void setXPosition(int x){this.xPosition = x;}
    public void setYPosition(int y){this.yPosition = y;}
}
