import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.*;


public class Goal extends Section implements ImageObserver {
    private Image img;

    private static Image goal = 
        new ImageIcon("img/Gold.jpg").getImage();
    private static Image taken = 
        new ImageIcon("img/Goal-taken.png").getImage();


    public Goal(int y, int x, int side) {
        this.x = x * side;
        this.y = y * side;
        this.side = side;
        this.property = new String("goal");
    }




    public void draw(Graphics2D g2d) {
        if (this.property.equals("goal")) {
            this.img = goal;
        }
        else {
            System.out.println("You Win!!");
           
            System.exit(0);
        }

        g2d.drawImage(this.img, this.x, this.y, this);
    }
}
