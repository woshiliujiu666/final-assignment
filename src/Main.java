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

    public void settings() {
        size(600, 600); 
    }

    public void setup() {
        background(255);
        // Create our hero at position (200, 200)
        hero = new player(this, 700, 700, "Hero", 18);
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
            // Update camera position so it follows the hero
            camX = hero.x - width / 2;
            camY = hero.y - height / 2;

            // Stop camera IF going outside the map
            camX = constrain(camX, 0, mapWidth - width);
            camY = constrain(camY, 0, mapHeight - height);

            // Move the whole world opposite to the camera
            pushMatrix();
            translate(-camX, -camY);

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

        }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
