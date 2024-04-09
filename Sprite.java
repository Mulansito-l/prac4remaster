import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Sprite{
    int xPosition;
    int yPosition;
    BufferedImage image = null;

    // Para crear un sprite simplemente coloca
    // el nombre de la imagen que se encuentra
    // en el directorio app/src/main/resources/
    
    public Sprite(String imageName, int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        try{
            image = ImageIO.read(new File("app/src/main/resources/"+imageName));
        }
        catch(Exception e){
            System.out.println("No se pudo cargar la imagen: " + e.toString());
        }

    }

    public BufferedImage getImage(){
        return image;
    }

    public int getXPosition(){return xPosition;}
    public int getYPosition(){return yPosition;}
    public void setXPosition(int x){this.xPosition = x;}
    public void setYPosition(int y){this.yPosition = y;}
}
