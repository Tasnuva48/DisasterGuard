/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.admindashboard;

/**
 *
 * @author TASNUVA
 */
import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // panel size অনুযায়ী image draw
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
