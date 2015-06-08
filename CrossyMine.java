import java.util.Random;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import javax.swing.*;
import java.awt.*;


public class CrossyMine extends JPanel 
    implements ActionListener, ImageObserver {
   private Miner miner;    
   private Map level;      
   private int timeL;      
   private int speed;      
   private int beatScore;  
   private int goals;      
   private int bonusTime;     
   private Death d;
   private boolean filter;
   private Font font;
   private Timer timer;
   private Image img = new ImageIcon("img/Cover.png").getImage();


   public static void main(String args[]) {
      JFrame frame = new JFrame("JMinerger");
   
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(new CrossyMine());
      frame.setSize(710, 500);
      frame.setLocationRelativeTo(null);
      frame.setResizable(false);
      frame.setVisible(true);
      frame.setIconImage(new ImageIcon("img/Icon.png").getImage());
   }


   public CrossyMine() {
      boolean t = true;
   
      addKeyListener(new TAdapter());
      setFocusable(true);
      setDoubleBuffered(true);
      setBackground(Color.BLACK);
   
      this.speed = 1;
      this.beatScore = 1000;
      this.bonusTime = 1;
      this.d = new Death(3);

      this.level = new Map(13, 700 / 25, 25);
      this.miner = new Miner(12, 9, 25);
   
      this.level.addMovingMiner(this.miner);      
   
      for (int i = 0; i < 25; i += 6) {
         level.addMovingMiner(new MineCart(7, i, 25, 1, 3 + speed, "red"));
         level.addMovingMiner(new MineCart(11, i + 3, 25, 1, 1 + speed, "red"));
      }
      for (int i = 0; i < 20; i += 9)
         level.addMovingMiner(new MineCart(9, i, 25, 1, 5 + speed, "blue"));
      for (int i = 0; i < 21; i += 10)  {
         level.addMovingMiner(new MineCart2(8, i, 25, -1, 2 + speed));
         level.addMovingMiner(new MineCart2(10, i + 4, 25, -1, 1 + speed));
      }
      for (int i = 0; i < 27; i += 7) 
         level.addMovingMiner(new Raft(4, i, 25, 1, 4 + speed));
      for (int i = 0; i < 25; i += 8) 
         level.addMovingMiner(new Raft(1, i, 25,1, 2 + speed));
      level.addMovingMiner(new Raft(3, 0, 25,1, 1 + speed));
      level.addMovingMiner(new Raft(3, 12, 25, 1, 1 + speed));
      level.addMovingMiner(new Raft(3, 24, 25,1, 1 + speed));        
      for (int i = 0; i < 31; i += 6) {
            level.addMovingMiner(new Buckets(5, i, 25, 3, -1, 1 + speed, t));
            t = !t;
        }
        for (int i = 0; i < 29; i += 4) {
            level.addMovingMiner(new Buckets(2, i, 25, 2, -1, 2 + speed, t));
            t = !t;
        }
   
   
      for (int i = 1; i < 6; i++)
         for (int j = 0; j < this.level.width; j++) {
            this.level.map[i][j] = new Water(i, j, 25);
            this.level.map[i + 6][j] = new Coal(i + 6, j, 25);
         }        
      
      for (int j = 0; j < this.level.width; j++) {
         this.level.map[6][j] = new Rocks(6, j, 25);
         this.level.map[12][j] = new Rocks(12, j, 25);
      }
      
      this.level.map[0][0] = new Border(0, 0, 25);        
      for (int i = 0; i < 25; i += 4) {
         this.level.map[0][i] = new Border(0, i, 25);
         this.level.map[0][i + 1] = new Border(0, i + 1, 25);
      }
      this.level.map[0][26] = new Border(0, 26, 25);
      this.level.map[0][27] = new Border(0, 27, 25);
      for (int i = 2; i < 25; i += 4) {        
         this.level.map[0][i] = new Goal(0, i, 25);
         this.level.map[0][i + 1] = this.level.map[0][i];
      }
   
      this.timeL = 600 - speed * 4;  
   
      this.timer = new Timer(90, this);   
   }


      private void addBonus() {
         Random r = new Random();
         int pos = Math.abs(r.nextInt()) % 5;
 
         if (pos == 0) 
            pos = 1;
         if (pos == 2) 
            pos = 3;
         if (pos == 3) 
            pos = 4;
         
              }


   private void out(int outCode) {
      if (outCode == 2) {
         System.out.println("You Win!!!");
         System.out.println("Your points:  " + miner.score);
         System.exit(1);
      }
      else if (outCode == 3) {
         this.miner.score += 200; 
         this.goals++;            
         this.timeL += 100;
         
         this.miner.x = 6 * this.miner.width; 
         this.miner.y = 12 * this.miner.height;
         System.out.println("Welcome!!!");
      }
      
      else {
         if (outCode == 1) {
            System.out.println("You Have Died!!!");
         }
         if (outCode == 0) {
            System.out.println("You have run out of time!!!");
            this.timeL = 600 - speed * 4;
         }
         if (this.miner.lives > 0) {
            this.miner.lives--;               
            
            this.miner.x = 6 * this.miner.width;
            this.miner.y = 12 * this.miner.height;
         }
         else {
            
            System.out.println("You Have Lost!!!");
            System.out.println("Your Points: " + miner.score);
            System.exit(1);
         }            
      }
   }


   public void infoScreen(Graphics2D g2d) {
      int k = 15;
   
      g2d.setColor(Color.WHITE);        
   
           g2d.drawString("SCORE: " + this.miner.score, 20, 370);
   
    
      for (int i = 0; i < this.miner.lives; i++) {
         g2d.fillRect(680 - k, 340, 10, 10);
         k += 15;
      }
   
      
      g2d.drawString("TIME: ", 20, 400);
      if (this.timeL < 60) 
         g2d.setColor(Color.RED);
      g2d.fillRect(20, 410, this.timeL, 20);
      g2d.setColor(Color.WHITE);
   }        

   public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
   
      super.paint(g);
      this.level.draw(g2d);   
      this.infoScreen(g2d);
      this.d.draw(g2d);       
   
      if (this.filter) {
         
         g2d.setColor(new Color(0, 0, 0, 134));
         g2d.fillRect(0, 0, 710, 13*25);
      }
   
      if (!this.timer.isRunning()) {
         
         g2d.drawImage(this.img, 0, 0, this);
      }       
   }
   
   public void actionPerformed(ActionEvent e) {
      int state = this.level.checkMovement(miner, miner.dy, miner.dx);
   
      if (state == 1) {
         this.miner.move();
      
         if (this.miner.dy == -1)
            this.miner.score += 10; 
      }
      else if (state == 2) {
         this.miner.move(); 
         this.out(3);                
      }
      else if (state == 0){
         this.d.active = true;        
         this.d.x = this.miner.x;    
         this.d.y = this.miner.y;    
         this.out(1);                 
      }  
      else if (state == 3) {
         this.miner.score += 100;   
      }
      
     
      if (this.miner.score >= this.beatScore) {
         this.miner.lives++;
         this.beatScore += 1000;
         this.timeL += 100;
      }
      
      if (this.goals == 6)
         this.out(2);                  
      this.timeL--;
      if (this.timeL == 0) {
         this.d.active = true;
         this.d.x = this.miner.x;
         this.d.y = this.miner.y;
         this.out(0);                 
      }
   
     
      if (this.bonusTime % 700 == 0)
         this.addBonus();
      this.bonusTime++;
         
      this.repaint(); 
   }

   public boolean imageUpdate(Image img, int infoFlags, int x, int y,
                              int width, int height) {
      return true;
   }

   private class TAdapter extends KeyAdapter {
      public void keyPressed(KeyEvent e) {
         if (!timer.isRunning())
            timer.start();
      
         if (e.getKeyCode() == KeyEvent.VK_F)
            filter = !filter;
      
         miner.keyPressed(e);
         
      }
   
      public void keyReleased(KeyEvent e) {
         miner.keyReleased(e);
         
      }
   }              

   private class Death implements ImageObserver {
      private int time;
      private int cTime;
      public int x;
      public int y;
      public boolean active;
            
      public Death(int time) {
         this.time = time;
         this.cTime = time;
         this.active = false;
      }
      
      public void draw(Graphics2D g2d) {
         if (this.active) {
            if (this.cTime != 0) {
               this.cTime--;
            }
            else {
               cTime = time;
               this.active = false;
            }
         
                    }
      }
   
      public boolean imageUpdate(Image img, int infoFlags, 
                                 int x, int y,
                                 int width, int height) {
         return true;
      }
   }
}


