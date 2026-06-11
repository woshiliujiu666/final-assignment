/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import processing.core.PApplet;

public class person {
    // Attributes from your lesson reference
    public int x;
    public int y;
    public String name;
    public int age;
    public PApplet app; // Needed for Processing to draw

    // Constructor
    public person(PApplet p, int x, int y, String name, int age) {
        this.app = p;
        this.x = x;
        this.y = y;
        this.name = name;
        this.age = age;
    }

    // Simple move method
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    // Draw method (Empty for now, child will handle it or we use basic shape)
    public void draw() {
        app.fill(255, 0, 0); // Red color
        app.rect(x, y, 24, 24); // Draw a square
    }
}