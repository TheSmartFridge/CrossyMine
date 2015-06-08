import java.awt.Graphics2D;
import java.awt.Image;

abstract class MapElement {
    public int x;  
    public int y;  
    public String property;
    


    public abstract void draw(Graphics2D g2d);

    public boolean imageUpdate(Image img, int infoFlags, int x, int y,
                               int width, int height) {
        return true;
    }
}
