/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import javax.swing.JFrame;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        
        // Create a game window with a title
        JFrame window = new JFrame("My RPG");
        
        // Set window size
        window.setSize(600, 600);
        
        // Put the window in the center of the screen
        window.setLocationRelativeTo(null);
        
        // Close program
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //  Make the window background black
        window.getContentPane().setBackground(Color.BLACK);
        
        //  Show the window on the screen
        window.setVisible(true);
    }
}