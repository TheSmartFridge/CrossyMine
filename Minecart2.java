import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class MineCart2 extends MovingMiner implements ImageObserver {
    private Image img;

    private static Image minecart1 = 
        new ImageIcon("img/minecart.jpg").getImage();


    public MineCart2(int y, int x, int side, int direction, int speed) {
        this.x = x * side;
        this.y = y * side;
        this.width = side * 2;
        this.height = side;
        this.direction = direction;
        this.speed = speed;
        this.property = new String("enemy");
        this.img = MineCart2.minecart1;
    }



    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.img, this.x, this.y, this);
    }
}
