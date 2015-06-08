import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class MineCart extends MovingMiner implements ImageObserver {
    private Image img;

    private static Image car1 = 
        new ImageIcon("img/minecart.jpg").getImage();
    private static Image car2 = 
        new ImageIcon("img/minecart.jpg").getImage();


    public MineCart(int y, int x, int side, int direction, 
                 int speed, String color) {
        this.x = x * side;
        this.y = y * side;
        this.width = side;
        this.height = side;
        this.direction = direction;
        this.speed = speed;
        this.property = new String("enemy");

        if (color.equals("red")) {
            img = MineCart.car1;
        }
        else if (color.equals("blue")) {
            img = MineCart.car2;
        }
        else 
            img = MineCart.car1;
    }


    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.img, this.x, this.y, this);
    }
}
