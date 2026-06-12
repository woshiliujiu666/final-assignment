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
    public static String GAME_TITLE = "Mulan: Guardian of the Village";
    
    public player hero; 
    public int stage = 0; // stage 0 = Menu Screen, stage 1 = Game Screen
    public PImage startMap;
    public shop store;
    public PImage monsterCaveMap;
    //public PImage playerImg;(for testing)
    public PImage enemy1Img;
    public PImage enemy2Img;
    public PImage bossImg;
    public PImage fightImg;


    // Add camera variables
    public int camX = 0;
    public int camY = 0;
    // mouse control!!!
    public boolean mouseControl = false;
    public int targetX = 0;
    public int targetY = 0;
    
    //player size
    public int playerSize = 36;

    // Add map size 
    public int mapWidth = 1400;
    public int mapHeight = 1400;
    // Cave map size 
      public int caveMapWidth = 1254;
       public int caveMapHeight = 1254;
        // Entrance areas(shop,cave)
    public int shopX = 1000;
    public int shopY = 380;
    public int shopW = 45;
    public int shopH = 45;

    public int caveX = 580;
    public int caveY = 1015;
    public int caveW = 45;
    public int caveH = 45;
    
        // Monster area variables
   // public int monsterSize = 50;(testing )

    public boolean nearMonster = false;
    public int currentMonster = 0;
        // Battle variables
    public moster caveMonster;
    public boolean defending = false;
    public String battleMessage = "Choose your action!";
    //public boolean monsterDefeated = false;(cancle,

    // Monster positions inside cave map
     public int monster1X = 350;
     public int monster1Y = 420;

     public int monster2X = 900;
     public int monster2Y = 420;

     public int monster3X = 620;
     public int monster3Y = 720;
     
     //player imageeeeee
     public PImage playerSheet;
     
     public int spriteW;
     public int spriteH;
     
    public int playerFrame = 1;
    public int playerDirection = 0;

       public boolean playerMoving = false;//(无用）
     public int animationCounter = 0;

    public void settings() {
        size(600, 600); 
    }

    public void setup() {
        background(255);
        // Create our hero at position (200, 200)
        hero = new player(this, 700, 700, "Hero", 18);
        store = new shop();
        caveMonster = new moster("enemy", 50, 8, 1, 3);
        //load startmap image
        startMap = loadImage("startingmap.png");
        mapWidth = startMap.width;
         mapHeight = startMap.height;
         // Load monster cave map image
         monsterCaveMap = loadImage("monstercave1.png");
         caveMapWidth = monsterCaveMap.width;
         caveMapHeight = monsterCaveMap.height;
         // Load player sprite sheet
         // Load enemy images
         enemy1Img = loadImage("rogues.png");
         enemy2Img = loadImage("rogues1.png");
         bossImg = loadImage("rogues2.png");
         fightImg = loadImage("fightimage.png");
           playerSheet = loadImage("actor1.png");
           spriteW = playerSheet.width / 3;
           spriteH = playerSheet.height / 4;
    }

    public void draw() {
        background(255); // Clear screen with white color every frame

        if (stage == 0) {
           // Stage 0: Story introduction screen
        background(30, 25, 35);

        fill(255, 220, 120);
        textSize(34);
        text(GAME_TITLE, 50, 80);

         fill(255);
         textSize(18);

           String introText = 
               "Inspired by the Chinese legend of Hua Mulan, this RPG tells the story of a young village guardian.\n\n" +
               "During a time of war, enemies and bandits begin to threaten the village. Instead of running away, the guardian chooses to protect her home, her family, and her community.\n\n" +
               "The player must prepare equipment in the village shop, enter the enemy cave, and defeat the enemies hiding inside.\n\n" +
               "This story focuses on courage, responsibility, and protecting others.";

           text(introText, 50, 130, 500, 330);

               fill(255, 220, 120);
               textSize(18);
               text("Press ENTER to begin your journey...", 130, 530);
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
            drawPlayer();
            
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
        // Stage 3: Monster cave map
 
         // Update camera position so it follows the hero inside cave
         camX = hero.x - width / 2;
          camY = hero.y - height / 2;

          // Stop camera from going outside the cave map
          camX = constrain(camX, 0, caveMapWidth - width);
          camY = constrain(camY, 0, caveMapHeight - height);

           // Move the cave world opposite to the camera
          pushMatrix();
          translate(-camX, -camY);

         // Draw cave map background
          image(monsterCaveMap, 0, 0);

            // Draw three enemies
            image(enemy1Img, monster1X, monster1Y, 40, 50);
            image(enemy2Img, monster2X, monster2Y, 44, 50);
            image(bossImg, monster3X, monster3Y, 48, 52);

            // Draw hero
            drawPlayer();

            popMatrix();

            // Check if player is close to any monster
            checkNearMonster();

            // Draw cave UI
            fill(255);
            textSize(18);
            text("Monster Cave", 20, 30);
            text("Press B to return", 20, 55);

            if (nearMonster == true) {
                fill(255, 255, 0);
                textSize(18);
                text("Press E to fight!", 220, 550);
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
            text(caveMonster.name, 400, 130);

            text("HP: " + hero.hp + "/" + hero.maxHp, 100, 160);
            text("HP: " + caveMonster.hp, 390, 160);
            // Use a for loop to draw the player's HP bar
            for (int i = 0; i < hero.hp / 10; i++) {
                 rect(100 + i * 12, 170, 10, 10);
            }

            // Draw player battle image
            image(fightImg, 60, 180, 180, 260);


             // Draw monster battle model
              if (currentMonster == 1) {
                  image(enemy1Img, 400, 220, 100, 130);
            }  
              else if (currentMonster == 2) {
                    image(enemy2Img, 400, 220, 100, 130);
            } 
               else if (currentMonster == 3) {
                    image(bossImg, 400, 220, 110, 130);
            }

            fill(255);
            textSize(18);
         if (hero.hp > 0 && caveMonster.hp > 0) {//(判断玩家和怪物是不是还活着)
             text("Press A to Attack", 190, 420);
             text("Press D to Defend", 190, 450);
             text("Press R to Run", 190, 480);
         } else {
             text("Press R to return", 190, 450);
      }

            fill(255, 255, 0);
            textSize(16);
            text(battleMessage, 120, 540);//战斗结果
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
             // Check if the player is touching a blocked area on the starting map
             public boolean isBlockedOnStartMap(int newX, int newY) {
             // Use the player's feet area for collision
          int px = newX + 18;
          int py = newY + 42;

               // Center flower circle / tree area
               if (px >= 520 && px <= 660 && py >= 560 && py <= 700) {
              return true;
            }

             // Shop building area
              if (px >= 840 && px <= 1150 && py >= 250 && py <= 430) {
                return true;
            }

            // Blue roof house area
            if (px >= 150 && px <= 420 && py >= 140 && py <= 390) {
                return true;
            }

            // Cave rock area
            if (px >= 430 && px <= 760 && py >= 820 && py <= 1080) {
                return true;
            }

         // Tree near cave/right side
            if (px >= 730 && px <= 830 && py >= 880 && py <= 1030) {
              return true;
            }

        return false;
   }
             // Move player only if the new position is not blocked
             public void moveHeroOnStartMap(int dx, int dy) {
                 int newX = hero.x + dx;
                 int newY = hero.y + dy;
             
                 if (isBlockedOnStartMap(newX, newY) == false) {
                     hero.move(dx, dy);
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
             hero.x = 620;
              hero.y = 1080;
             mouseControl = false;
}
    }
         // Check if hero is close to any monster
        public void checkNearMonster() {
            float d1 = dist(hero.x, hero.y, monster1X, monster1Y);
            float d2 = dist(hero.x, hero.y, monster2X, monster2Y);
            float d3 = dist(hero.x, hero.y, monster3X, monster3Y);

                  if (d1 < 80) {
                    nearMonster = true;
                    currentMonster = 1;
              } 
                else if (d2 < 80) {
                    nearMonster = true;
                   currentMonster = 2;
              } 
                else if (d3 < 80) {
                    nearMonster = true;
                    currentMonster = 3;
              } 
                else {
                    nearMonster = false;
                    currentMonster = 0;
            }
      }
               // Draw player with direction and walking animation
             public void drawPlayer() {
           PImage currentFrame = playerSheet.get(playerFrame * spriteW, playerDirection * spriteH, spriteW, spriteH);
           image(currentFrame, hero.x, hero.y, 36, 48);
       }
       /*    // Draw bigger player image for battle screen
                public void drawBattlePlayer() {
                      PImage currentFrame = playerSheet.get(playerFrame * spriteW, playerDirection * spriteH, spriteW, spriteH);
                      image(currentFrame, 130, 230, 90, 120);
               }*/ // for test

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
             //   monsterDefeated = true;(cancle )
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
        // Create a monster for the battle based on currentMonster
          public void createBattleMonster() {
              if (currentMonster == 1) {
                   caveMonster = new moster("ROBBERS", 70, 8, 3, 3);// 血量, 攻击力, 防御力, 速度
            } 
              else if (currentMonster == 2) {
                 caveMonster = new moster("THe second warrior", 77, 7, 7, 3);
            } 
              else if (currentMonster == 3) {
                 caveMonster = new moster("Boss", 164, 14, 4, 4);
            }

              defending = false;
              battleMessage = caveMonster.name + " appeared!";
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
    // Update walking animation frame
            public void updatePlayerAnimation() {//更新玩家的走路动画
                animationCounter++;

                if (animationCounter % 4 == 0) {//每 4 次换一次
                    playerFrame++;//切换到下一张动作图片
            
                    if (playerFrame > 2) {//动作帧只有2
                        playerFrame = 0;
                    }
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
            moveHeroOnStartMap(0, -hero.speed);//move up
            playerDirection = 3;
            playerMoving = true;
            updatePlayerAnimation();
            mouseControl = false;
        }
        if (key == 's' || key == 'S' || keyCode == DOWN) {
            moveHeroOnStartMap(0, hero.speed);//move down
            playerDirection = 0;
            playerMoving = true;
            updatePlayerAnimation();
            mouseControl = false;
        }
        if (key == 'a' || key == 'A' || keyCode == LEFT) {
             moveHeroOnStartMap(-hero.speed, 0);//MOVE left
             playerDirection = 1;
             playerMoving = true;
              updatePlayerAnimation();
              mouseControl = false;
        }
        if (key == 'd' || key == 'D' || keyCode == RIGHT) {
        moveHeroOnStartMap(hero.speed, 0);//move right
        playerDirection = 2;
        playerMoving = true;
        updatePlayerAnimation();
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
        playerDirection = 3;
        playerMoving = true;
        updatePlayerAnimation();
        mouseControl = false;
    }
    if (key == 's' || key == 'S' || keyCode == DOWN) {
        hero.move(0, hero.speed);
        playerDirection = 0;
        playerMoving = true;
        updatePlayerAnimation();
        mouseControl = false;
    }
    if (key == 'a' || key == 'A' || keyCode == LEFT) {
        hero.move(-hero.speed, 0);
        playerDirection = 1;
        playerMoving = true;
        updatePlayerAnimation();
        mouseControl = false;
    }
    if (key == 'd' || key == 'D' || keyCode == RIGHT) {
        hero.move(hero.speed, 0);
        playerDirection = 2;
        playerMoving = true;
        updatePlayerAnimation();
        mouseControl = false;
    }

    // Keep hero inside cave map
    hero.x = constrain(hero.x, 0, caveMapWidth - playerSize);
    hero.y = constrain(hero.y, 0, caveMapHeight - playerSize);

    if (key == 'e' || key == 'E') {
        if (nearMonster == true) {
            hero.hp = hero.maxHp; // Restore full HP before battle
            createBattleMonster();
            stage = 4;
        }
    }

    if (key == 'b' || key == 'B') {
        stage = 1;
        hero.x = 598;
        hero.y = 1100;
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
