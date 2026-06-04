/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
    // player inherits name from person
public class player extends person {
    
    // 1. Attributes (New Game Data)
    public int hp;
    public int maxHp;      // Max health limit
    public int gold;       // Money to buy things
    public int damage;     // Attack power
    public int defense;    // Shield block power
    public int speed;      // Fast or slow (Decides who attacks first)
    
    public boolean hasSword;  // Will increase damage
    public boolean hasShield; // Will increase defense
    
    // 2. Constructor
    public player(String name) {
        super(name); 
        
        // Starting Data (You have money now!)
        this.maxHp = 100;
        this.hp = 100;
        this.gold = 30;       // Start with 30 gold
        this.damage = 10;     // Base attack
        this.defense = 2;     // Base defense
        this.speed = 5;       // Base speed
        
        this.hasSword = false;
        this.hasShield = false;
    }
}
