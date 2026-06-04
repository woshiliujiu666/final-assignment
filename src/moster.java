/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class moster {
    
    // 1. Attributes
    public String name;
    public int hp;
    public int damage;
    public int defense;
    public int speed;     // If player speed < monster speed, monster attacks first
    
    // 2. Constructor
    public moster(String name, int hp, int damage, int defense, int speed) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
        this.defense = defense;
        this.speed = speed;
    }
}