/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
    // player inherits name from person
import processing.core.PApplet;

// player inherits from person (Inheritance)
public class player extends person {
    
    // RPG attributes
    public int hp;
    public int maxHp;
    public int gold;
    public int damage;
    public int defense;
    public int speed;
    public boolean hasSword;
    public boolean hasShield;

    // Fix: Update the constructor to accept 5 parameters!
    public player(PApplet p, int x, int y, String name, int age) {
        // Pass p, x, y, name, age to the parent (person) class
        super(p, x, y, name, age); 
        
        // Setup starting values
        this.maxHp = 100;
        this.hp = 100;
        this.gold = 30; // Start with some money as you wished!
        this.damage = 10;
        this.defense = 2;
        this.speed = 5;
        this.hasSword = false;
        this.hasShield = false;
    }
}