import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class Canvas{
    private static Canvas canvas = null; 

    public static Canvas getCanvas(){
        if(canvas == null){
            canvas = new Canvas("Canvas",10000,10000, Color.white);
        }

        canvas.setVisible(true);
        return canvas;
    }

    private JFrame frame;
    private JScrollPane scrollPane;
    private CanvasPane canvasPane;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, Sprite> sprites;

    public Canvas(String title, int width, int height, Color bgColor){
        frame = new JFrame();
        canvasPane = new CanvasPane();
        scrollPane = new JScrollPane(canvasPane);
        objects = new ArrayList<Object>();
        sprites = new HashMap<Object, Sprite>();
        frame.setContentPane(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);
        frame.setLocationRelativeTo(null);
        canvasPane.setPreferredSize(new Dimension(width, height));
        backgroundColor = bgColor;
        frame.pack(); 
        setVisible(true);
    }

    public void setVisible(boolean visible){
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background color
            Dimension size = canvasPane.getSize();
            canvasImage = canvasPane.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    // Utilizar esta función para dibujar
    // objetos, mandar la referencia al objeto
    // y el sprite con el que se quiere dibujar
    public void Draw(Object objectReference, Sprite sprite){
        objects.remove(objectReference);
        objects.add(objectReference);
        sprites.put(objectReference, sprite);
    }

    // Para borrar un objecto simplemente
    // pasa la referencia al objeto
    public void Erase(Object objectReference){
        objects.remove(objectReference);
        canvasPane.repaint();
    }

    public void Redraw(){
        canvasPane.repaint();
    }

    private class CanvasPane extends JPanel{
        public void paint(Graphics g)
        {
            super.paint(g);
            g.drawImage(canvasImage, 0, 0, null);
            for(Object object : objects){
                Sprite objectSprite = sprites.get(object);
                if(objectSprite != null){
                    g.drawImage(objectSprite.getImage(), objectSprite.getXPosition(),objectSprite.getYPosition(),null);
                }
            }
        }
    }
}
