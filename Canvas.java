import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.*;

// Clase canvas es donde se muestran
// todos los elementos básicos, está inspirada
// en aquella que se encuentra en los materiales del
// BlueJ pero se reescribio para poder mostrar
// BufferedImages y hacer el canvas scrolleable
public class Canvas{
    // Singleton de Canvas ya que solo
    // debe existir uno en todo momento
    private static Canvas canvas = null; 

    // Método estático que devuelve el
    // singleton del canvas o crea uno
    // si no existe todavía
    public static Canvas getCanvas(){
        if(canvas == null){
            canvas = new Canvas("El Cinquillo",1920,1080, new Color(115,209,159));
        }

        canvas.setVisible(true);
        return canvas;
    }

    private JFrame frame;
    private CanvasPane canvasPane;
    private JScrollPane scrollPane;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, Sprite> sprites;
    private ElCinquillo cinquillo;

    // Constructor de Canvas, con título, tamaño y color de fondo
    // además se añade un par de Listeners para el uso del cursor
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
        canvasPane.addMouseListener(canvasPane);
        canvasPane.addMouseMotionListener(canvasPane);
    }

    // Método que muestra la ventana del Canvas
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

    // Método que se encarga de añadir un objeto al
    // HashMap de objetos dibujables, relaciona un objeto
    // con un Sprite y cada vez que se intenta dibujar el
    // objeto en el Canvas se dibuja el Sprite relacionado
    // con el objeto.
    public void draw(Object objectReference, Sprite sprite){
        objects.remove(objectReference);
        objects.add(objectReference);
        sprites.put(objectReference, sprite);
    }

    // Método que elimina el objeto dado del HashMap de objetos
    // a dibujar
    public void erase(Object objectReference){
        objects.remove(objectReference);
        canvasPane.repaint();
    }

    // Método que se encarga de actualizar el Canvas
    public void redraw(){
        canvasPane.repaint();
    }

    // Extensión del JPanel para que funcione como un Canvas, implementa algunos
    // Listeners para tener eventos con el cursor, dibuja el fondo siempre y
    // posteriormente itera sobre todo el hashmap y dibuja todos los objetos
    // que tienen un Sprite visible, al clickear o mover el mouse se pasan
    // las coordenadas del cursor a un método del cinquillo para realizar
    // la interacción.
    private class CanvasPane extends JPanel implements MouseListener, MouseMotionListener {
        public void paint(Graphics g)
        {
            super.paint(g);
            g.drawImage(canvasImage, 0, 0, null);

            // Aplicación de Lambda en la que se filtran los objetos que tienen
            // tienen sprite y son visibles para dibujarlos posteriormente
            objects.stream().filter(
                    object -> sprites.get(object) != null && sprites.get(object).isVisible()
            ).forEach(
                    object ->{
                        Sprite objectSprite = sprites.get(object);
                        g.drawImage(objectSprite.getImage(), objectSprite.getXPosition(),objectSprite.getYPosition(),null);
                    }
            );
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
