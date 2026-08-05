/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.homepage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import javax.swing.JPanel;

public class GradientGreen extends JPanel {

    public GradientGreen() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Color color1 = new Color(34, 139, 34);
        Color color2 = new Color(144, 238, 144);

        GradientPaint gp = new GradientPaint(
                0, 0, color1,
                0, getHeight(), color2
        );

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
