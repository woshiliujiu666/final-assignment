/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import processing.core.PApplet;
public class Main extends PApplet {

    public player hero; 
    public int stage = 0; // stage 0 = Menu Screen, stage 1 = Game Screen

    public void settings() {
        size(600, 600); 
    }

    public void setup() {
        background(255);
        // Create our hero at position (200, 200)
        hero = new player(this, 200, 200, "Hero", 18);
    }

    public void draw() {
        background(255); // Clear screen with white color every frame

        if (stage == 0) {
            // Stage 0: Show Main Menu
            fill(0); 
            textSize(30);
            text("FIXXX???", 50, 100);
            
            textSize(18);
            text("Press ENTER to start the game...", 50, 200);
        } 
        else if (stage == 1) {
            // Stage 1: Draw the player square
            hero.draw(); 
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
            // 
            if (key == 'w' || key == 'W') {
                hero.move(0, -hero.speed); // Move UP
            }
            if (key == 's' || key == 'S') {
                hero.move(0, hero.speed);  // Move DOWN
            }
            if (key == 'a' || key == 'A') {
                hero.move(-hero.speed, 0); // Move LEFT
            }
            if (key == 'd' || key == 'D') {
                hero.move(hero.speed, 0);  // Move RIGHT
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}