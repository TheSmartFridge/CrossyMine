
import java.awt.Graphics2D;
import java.util.ArrayList;

@SuppressWarnings({"unchecked"})

public class Map {
   public int height;            
   public int width;                
   public int sectionSide;          
   public MapElement [][] map;      
   public MovingMiner player;           
   private ArrayList <MovingMiner> [] actorMap;

   

   public Map(int height, int width, int sectionSide) {
      this.height = height;
      this.width = width;
      this.sectionSide = sectionSide;
      this.map = new MapElement [height][width];
      this.actorMap = new ArrayList [height];
   
     
   }

   public Map(int side, int sectionSide) {
      this.height = side;
      this.width = side;
      this.sectionSide = sectionSide;
      this.map = new MapElement [height][width];
      this.actorMap = new ArrayList [height];
   
      
   }
      
   public void draw(Graphics2D g2d) {
      for (int i = 0; i < this.height; i++)
         for (int j = 0; j < this.width; j++)
            this.map[i][j].draw(g2d);
   
      
      MovingMiner bonus = null;
      ArrayList <MovingMiner> minerList;
   
      for (int y = 0; y < this.actorMap.length; y++) {
         if (this.actorMap[y] == null)
            continue;
      
         minerList = this.actorMap[y];
         for (int i = 0; i < minerList.size(); i++) {
            if (minerList.get(i).property.equals("bonus")) {
               bonus = minerList.get(i);
               continue;
            }
            minerList.get(i).draw(g2d);
         }
      }
      if (bonus != null)
         bonus.draw(g2d);
   
     
      if (player != null)
         this.player.draw(g2d);
   }

  
   public void addMovingMiner(MovingMiner actor) {
      int y = actor.y / this.sectionSide;
      
      if (this.actorMap[y] == null) {
         this.actorMap[y] = new ArrayList <MovingMiner>();
      }
      if (actor.property.equals("bonus")) {
         this.actorMap[y].add(0, actor);
      }
      else
         this.actorMap[y].add(actor);
   }

   
   public void addPlayer(MovingMiner player) {
      this.player = player;
   }

   private int checkCollision(MovingMiner actor, int y) {
      int tolerance = this.sectionSide / 4;
      ArrayList <MovingMiner> minerList;
   
      if (this.actorMap[y] == null) {
         actor.speed = 0;
         return 0;
      }
   
      minerList = this.actorMap[y];
   
      for (int i = 0; i < minerList.size(); i++) {
         MovingMiner current = minerList.get(i);
      
         if (actor.equals(current))
            continue;
      
         if (actor.x + actor.width >= current.x + tolerance * 2 &&
            actor.x <= current.x + current.width - tolerance * 2) {
         
            if (current.property.equals("enemy"))
               return 1;
         
            if (current.property.equals("dynamic")) {
             
               actor.speed = current.speed * current.direction;
               return 2;
            }
                
         
         }            
      }
   
      actor.speed = 0;
      return 0;
   }

  
   public void moveMovingMiners() {
      ArrayList <MovingMiner> minerList;
   
      for (int y = 0; y < this.actorMap.length; y++) {
         if (this.actorMap[y] == null)
            continue;
         
         minerList = this.actorMap[y];
         for (int i = 0; i < minerList.size(); i++) {
            MovingMiner current = minerList.get(i);
         
                       if (current.property.equals("bonus") &&
               current.speed == 0) {
               checkCollision(current, y);
            }
         
         
           
            if (current.x > this.width * this.sectionSide && 
               current.direction == 1) {
               if (current.property.equals("bonus")) {
                  minerList.remove(i);
                  continue;
               }
               current.x = -current.width;
            }
            
            else if (current.x + current.width < 0 &&
                    current.direction == -1) {
               current.x = this.width * this.sectionSide + 
                  current.width;
            }
            current.move();
         }            
      }
   }


   public int checkMovement(MovingMiner actor, int newY, int newX) {
      int y = actor.y / this.sectionSide;
      int x = actor.x / this.sectionSide;
      int tolerance = this.sectionSide / 4;
   
      moveMovingMiners();
   
           if (actor.x > x * this.sectionSide + tolerance * 2)
         x += 1;
   
   
          if (y + newY >= this.height) {
         actor.y -= actor.height;
         return 1;
      }
            else if (actor.y + (newY * this.sectionSide) < 0) {
         actor.y = 0;
         return 1;
      }
           else if (x + newX >= this.width || x + newX < 0) {
         return 0;
      }
           if (this.map[y + newY][x].property.equals("collider")) {
         actor.y += actor.height;
         return 1;
      }
   
   
      if (actor.property.equals("player")) {
         int collision = checkCollision(actor, y);
         if (collision == 0) {
            if (this.map[y][x].property.equals("killer"))
               return 0;
            else if (this.map[y][x].property.equals("goal")) {
               this.map[y][x].property = new String("killer");
               return 2;
            }                    
         }
         else if (collision == 1) {
            return 0;
         }
         else if (collision == 3) {
            return 3;
         }
      }
   
      return 1;
   }
}
