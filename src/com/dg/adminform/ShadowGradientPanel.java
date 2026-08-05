/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.adminform;


import javax.swing.*;
import java.awt.*;

public class ShadowGradientPanel extends JPanel {

    private Color gradientStart;
    private Color gradientEnd;
    private int cornerRadius;
    private int shadowSize;

    public ShadowGradientPanel(Color start, Color end, int radius, int shadow) {
        this.gradientStart = start;
        this.gradientEnd = end;
        this.cornerRadius = radius;
        this.shadowSize = shadow;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Shadow
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, cornerRadius, cornerRadius);

        // Gradient panel
        GradientPaint gp = new GradientPaint(0, 0, gradientStart, getWidth(), getHeight(), gradientEnd);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }
}
