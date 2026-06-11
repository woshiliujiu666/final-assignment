/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {

    public player hero; 
    public int stage = 0; // stage 0 = Menu Screen, stage 1 = Game Screen
    public PImage startMap;
    public shop store;

    // Add camera variables
    public int camX = 0;
    public int camY = 0;
    // mouse control!!!
    public boolean mouseControl = false;
    public int targetX = 0;
    public int targetY = 0;
    
    //player size
    public int playerSize = 24;

    // Add map size variables
    public int mapWidth = 1400;
    public int mapHeight = 1400;
        // Entrance areas
    public int shopX = 1005;
    public int shopY = 330;
    public int shopW = 45;
    public int shopH = 45;

    public int caveX = 580;
    public int caveY = 1015;
    public int caveW = 45;
    public int caveH = 45;
    
        // Monster area variables
    public int monsterX = 300;
    public int monsterY = 250;
    public int monsterSize = 50;

    public boolean nearMonster = false;
        // Battle variables
    public moster caveMonster;
    public boolean defending = false;
    public String battleMessage = "Choose your action!";
    public boolean monsterDefeated = false;


    
    

    public void settings() {
        size(600, 600); 
    }

    public void setup() {
        background(255);
        // Create our hero at position (200, 200)
        hero = new player(this, 700, 700, "Hero", 18);
        store = new shop();
        caveMonster = new moster("Slime", 50, 8, 1, 3);
        //load startmap image
        startMap = loadImage("startingmap.png");
        mapWidth = startMap.width;
         mapHeight = startMap.height;
    }

    public void draw() {
        background(255); // Clear screen with white color every frame

        if (stage == 0) {
            // Stage 0: Show Main Menu
            fill(0); 
            textSize(30);
            text("RPG Game", 50, 100);

            textSize(18);
            text("Press ENTER to start the game...", 50, 200);
        } 
        else if (stage == 1) {
            // Stage 1: Draw the player square
            updateMouseMovement();
            checkMapEntrances();
            // Update camera position so it follows the hero
            camX = hero.x - width / 2;
            camY = hero.y - height / 2;

            // Stop camera IF going outside the map,,constrain:范围，限制数字不超过范围（要限制的数字，最小值，最大值）
            camX = constrain(camX, 0, mapWidth - width);
            camY = constrain(camY, 0, mapHeight - height);

            // Move the whole world opposite to the camera
            pushMatrix();//保存
            translate(-camX, -camY);//根据camera移动

            // Draw map background
            image(startMap,0,0);

            // Draw the player in the world
            hero.draw();

            popMatrix();

            // Draw UI on screen
            fill(0);
            textSize(16);
            text("Player World X: " + hero.x, 20, 25);
            text("Player World Y: " + hero.y, 20, 45);
            text("Camera X: " + camX, 20, 65);
            text("Camera Y: " + camY, 20, 85);
        }
                else if (stage == 2) {
            // Stage 2: Shop screen
            background(230, 210, 160);

             fill(0);
             textSize(30);
             text("Shop", 250, 80);
             textSize(18);
             text("Press 1 to buy Sword ($20)", 150, 150);
             text("Press 2 to buy Shield ($20)", 150, 180);
             text("Press B to go back to the starting map.", 150, 230);

             text("Gold: " + hero.gold, 150, 300);
             text("Damage: " + hero.damage, 150, 330);
            text("Defense: " + hero.defense, 150, 360);
            text("Has Sword: " + hero.hasSword, 150, 390);
            text("Has Shield: " + hero.hasShield, 150, 420);
        }
        else if (stage == 3) {
            // Stage 3: Monster cave area
            background(70);

            fill(255);
            textSize(26);
            text("Monster Cave", 20, 40);

            textSize(16);
            text("Move with W A S D or arrow keys", 20, 70);
            text("Press B to go back to the starting map", 20, 95);if (monsterDefeated == false) {
                fill(0, 180, 0);
                rect(monsterX, monsterY, monsterSize, monsterSize);

                fill(255);
                textSize(14);
                text("Monster", monsterX, monsterY - 10);
            } else {
                fill(255);
                textSize(18);
                text("Monster defeated!", 220, 280);
            }

           // Draw monster if it is not defeated
            if (monsterDefeated == false) {
                fill(0, 180, 0);
                rect(monsterX, monsterY, monsterSize, monsterSize);

                fill(255);
                textSize(14);
                text("Monster", monsterX, monsterY - 10);
            } else {
                fill(255);
                textSize(18);
                text("Monster defeated!", 220, 280);
            }

            // Draw hero
            hero.draw();

            // Check if player is close to monster
            if (monsterDefeated == false) {
              checkNearMonster();  
            }

            if (nearMonster == true) {
                fill(255, 255, 0);
                textSize(18);
                text("Press E to fight!", 220, 520);
            }
        }
        else if (stage == 4) {
             // Stage 4: Battle screen
            background(30, 30, 50);

            fill(255);
            textSize(30);
            text("Battle Screen", 190, 60);

            textSize(18);
            text("Player", 120, 130);
            text("Monster", 400, 130);

            text("HP: " + hero.hp + "/" + hero.maxHp, 100, 160);
            text("HP: " + caveMonster.hp, 390, 160);

            // Draw player battle model
            fill(0, 0, 255);
            rect(120, 230, 80, 120);

            // Draw monster battle model
            fill(0, 180, 0);
            rect(400, 220, 100, 130);

            fill(255);
            textSize(18);
         if (hero.hp > 0 && caveMonster.hp > 0) {
             text("Press A to Attack", 190, 420);
             text("Press D to Defend", 190, 450);
             text("Press R to Run", 190, 480);
         } else {
             text("Press R to return", 190, 450);
      }

            fill(255, 255, 0);
            textSize(16);
            text(battleMessage, 120, 540);
   }
    }
    // Mouse control: click somewhere on the screen and the hero will move there
    public void mousePressed() {
        if (stage == 1) {
            targetX = mouseX + camX;
            targetY = mouseY + camY;

            targetX = constrain(targetX, 0, mapWidth - playerSize);//CANNOT GO OUTSIDE//
            targetY = constrain(targetY, 0, mapHeight - playerSize);

            mouseControl = true;
        }
    }
        // Check if the hero enters shop or cave area
    public void checkMapEntrances() {
        if (hero.x >= shopX && hero.x <= shopX + shopW && hero.y >= shopY && hero.y <= shopY + shopH) {
            stage = 2;
            mouseControl = false;
        }

        if (hero.x >= caveX && hero.x <= caveX + caveW && hero.y >= caveY && hero.y <= caveY + caveH) {
            stage = 3;
            mouseControl = false;
        }
    }
        // Check if hero is close to the monster
    public void checkNearMonster() {
        float distance = dist(hero.x, hero.y, monsterX, monsterY);

        if (distance < 80) {
            nearMonster = true;
        } else {
            nearMonster = false;
        }
    }
        // Player attacks the monster
    public void playerAttack() {
        if (caveMonster.hp > 0 && hero.hp > 0) {
            int damageToMonster = hero.damage - caveMonster.defense;

            if (damageToMonster < 1) {
                damageToMonster = 1;
            }

            caveMonster.hp -= damageToMonster;

            if (caveMonster.hp <= 0) {
                caveMonster.hp = 0;
                monsterDefeated = true;
                hero.gold += 25;
                battleMessage = "You defeated the monster! +25 gold. Press R to return.";
            } else {
                battleMessage = "You attacked for " + damageToMonster + " damage!";
                monsterAttack();
            }
        }
    }

    // Monster attacks the player
    public void monsterAttack() {
        int damageToPlayer = caveMonster.damage - hero.defense;

        if (defending == true) {
            damageToPlayer = damageToPlayer / 2;
            defending = false;
        }

        if (damageToPlayer < 1) {
            damageToPlayer = 1;
        }

        hero.hp -= damageToPlayer;

        if (hero.hp <= 0) {
            hero.hp = 0;
            battleMessage = "You were defeated! Press R to return.";
        } else {
            battleMessage = battleMessage + " Monster hit you for " + damageToPlayer + " damage.";
        }
    }

    // Player defends
    public void playerDefend() {
        if (caveMonster.hp > 0 && hero.hp > 0) {
            defending = true;
            battleMessage = "You defended!";
            monsterAttack();
        }
    }
    // Move hero toward mouse target
    public void updateMouseMovement() {
        if (mouseControl == true) {
            int dx = targetX - hero.x;
            int dy = targetY - hero.y;

            float distance = dist(hero.x, hero.y, targetX, targetY);

            if (distance <= hero.speed) {
                hero.x = targetX;
                hero.y = targetY;
                mouseControl = false;
            } else {
                hero.x += Math.round(hero.speed * dx / distance);
                hero.y += Math.round(hero.speed * dy / distance);
            }

            hero.x = constrain(hero.x, 0, mapWidth - playerSize);
            hero.y = constrain(hero.y, 0, mapHeight - playerSize);
        }
    }

   // Keyboard controls
          public void keyPressed() {
    // Menu Controls
       if (stage == 0) {
        if (keyCode == ENTER) {
            stage = 1; // Start game
        }
    } 
    //Game Controls 
    else if (stage == 1) {
        // Keyboard movement will stop mouse movement
        if (key == 'w' || key == 'W' || keyCode == UP) {
            hero.move(0, -hero.speed); // Move UP
            mouseControl = false;
        }
        if (key == 's' || key == 'S' || keyCode == DOWN) {
            hero.move(0, hero.speed);  // Move DOWN
            mouseControl = false;
        }
        if (key == 'a' || key == 'A' || keyCode == LEFT) {
            hero.move(-hero.speed, 0); // Move LEFT
            mouseControl = false;
        }
        if (key == 'd' || key == 'D' || keyCode == RIGHT) {
            hero.move(hero.speed, 0);  // Move RIGHT
            mouseControl = false;
        }

        // Keep hero inside map
        hero.x = constrain(hero.x, 0, mapWidth - playerSize);
        hero.y = constrain(hero.y, 0, mapHeight - playerSize);
    }
    else if (stage == 2) {
        if (key == '1') {
        store.buySword(hero);
       }

        if (key == '2') {
        store.buyShield(hero);
       }

    if (key == 'b' || key == 'B') {
        stage = 1;
        hero.x = 1023;
        hero.y = 450;
        mouseControl = false;
       }
  }
    else if (stage == 3) {
        if (key == 'w' || key == 'W' || keyCode == UP) {
            hero.move(0, -hero.speed);
        }
        if (key == 's' || key == 'S' || keyCode == DOWN) {
            hero.move(0, hero.speed);
        }
        if (key == 'a' || key == 'A' || keyCode == LEFT) {
            hero.move(-hero.speed, 0);
        }
        if (key == 'd' || key == 'D' || keyCode == RIGHT) {
            hero.move(hero.speed, 0);
        }

        // Keep hero inside cave screen
        hero.x = constrain(hero.x, 0, width - playerSize);
        hero.y = constrain(hero.y, 0, height - playerSize);

        if (key == 'e' || key == 'E') {
    if (nearMonster == true && monsterDefeated == false) {
        stage = 4;
        battleMessage = "Choose your action!";
    }
        }

        if (key == 'b' || key == 'B') {
            stage = 1;
            hero.x = 598;
            hero.y = 950;
            mouseControl = false;
        }
    }
    else if (stage == 4) {
         if (key == 'a' || key == 'A') {
            playerAttack();
        }

        if (key == 'd' || key == 'D') {
            playerDefend();
        }

         if (key == 'r' || key == 'R') {
        stage = 3;
       hero.x = 250;
      hero.y = 350;
     defending = false;
    battleMessage = "Choose your action!";

    if (hero.hp <= 0) {
        hero.hp = hero.maxHp;
         hero.gold = 0;
          stage = 1;
           hero.x = 700;
            hero.y = 700;
    }
}
    }

        }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
