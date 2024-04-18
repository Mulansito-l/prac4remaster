import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.*;

public class Canvas{
    private static Canvas canvas = null; 

    public static Canvas getCanvas(){
        if(canvas == null){
            canvas = new Canvas("Canvas",1920,1080, new Color(115,209,159));
        }

        canvas.setVisible(true);
        return canvas;
    }

    private JFrame frame;
    private CanvasPane canvasPane;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, Sprite> sprites;
    private ElCinquillo cinquillo;
    private JLabel saltarLabel;

    public Canvas(String title, int width, int height, Color bgColor){
        frame = new JFrame();
        canvasPane = new CanvasPane();
        objects = new ArrayList<Object>();
        sprites = new HashMap<Object, Sprite>();
        saltarLabel = new JLabel("Saltar turno");
        frame.setContentPane(canvasPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);
        frame.setLocationRelativeTo(null);
        canvasPane.setPreferredSize(new Dimension(width, height));
        saltarLabel.setFont(new Font("Arial", Font.BOLD, 16));
        saltarLabel.setForeground(Color.BLUE);
        saltarLabel.setBackground(Color.LIGHT_GRAY);
        saltarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        canvasPane.add(saltarLabel);
        saltarLabel.setVisible(true);
        backgroundColor = bgColor;
        frame.pack(); 
        setVisible(true);
        canvasPane.addMouseListener(canvasPane);
        canvasPane.addMouseMotionListener(canvasPane);
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

    public void setCinquillo(ElCinquillo cinquillo) {
        this.cinquillo = cinquillo;
    }

    // Utilizar esta función para dibujar
    // objetos, mandar la referencia al objeto
    // y el sprite con el que se quiere dibujar
    public void draw(Object objectReference, Sprite sprite){
        objects.remove(objectReference);
        objects.add(objectReference);
        sprites.put(objectReference, sprite);
    }

    // Para borrar un objecto simplemente
    // pasa la referencia al objeto
    public void erase(Object objectReference){
        objects.remove(objectReference);
        canvasPane.repaint();
    }

    public void redraw(){
        canvasPane.repaint();
    }

    private class CanvasPane extends JPanel implements MouseListener, MouseMotionListener {
        public void paint(Graphics g)
        {
            super.paint(g);
            g.drawImage(canvasImage, 0, 0, null);
            for(Object object : objects){
                Sprite objectSprite = sprites.get(object);
                if(objectSprite != null && objectSprite.isVisible()){
                    g.drawImage(objectSprite.getImage(), objectSprite.getXPosition(),objectSprite.getYPosition(),null);
                }
            }
        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        public void mouseDragged(MouseEvent e)
        {

        }

        public void mouseMoved(MouseEvent e)
        {
            cinquillo.resaltarCarta(e.getX(), e.getY());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            cinquillo.seleccionarCarta(e.getX(), e.getY());
        }
    }
}
