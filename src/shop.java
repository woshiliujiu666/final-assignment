/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class shop {
    public int swordPrice;
    public int shieldPrice;
    
    public shop() {
        this.swordPrice = 20;
        this.shieldPrice = 20;
    }
    
    // Association: shop uses a player object
    public void buySword(player hero) {
        if (hero.gold >= swordPrice && hero.hasSword == false) {
            hero.gold -= swordPrice;
            hero.damage += 10;
            hero.hasSword = true;
        }
    }
    
    // Association: shop uses a player object
    public void buyShield(player hero) {
        if (hero.gold >= shieldPrice && hero.hasShield == false) {
            hero.gold -= shieldPrice;
            hero.defense += 5;
            hero.hasShield = true;
        }
    }
}
