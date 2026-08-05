/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.homepage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author USER
 */


public class GlowPanel extends JPanel {

    private float glow = 0f;
    private boolean hover = false;
    private final Timer timer;

    public GlowPanel() {
        setOpaque(false);
        setLayout(null);

        timer = new Timer(16, e -> {
            if (hover && glow < 1f) glow += 0.05f;
            else if (!hover && glow > 0f) glow -= 0.05f;

            glow = Math.max(0f, Math.min(1f, glow));
            repaint();
        });

       /* addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                timer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                timer.start();
            }
        });*/
       
       
       addMouseMotionListener(new MouseMotionAdapter() {
    @Override
    public void mouseMoved(MouseEvent e) {
        hover = true;
        timer.start();
    }
});

addMouseListener(new MouseAdapter() {
    @Override
    public void mouseExited(MouseEvent e) {
        Point p = SwingUtilities.convertPoint(
                e.getComponent(),
                e.getPoint(),
                GlowPanel.this
        );

        if (!contains(p)) {
            hover = false;
            timer.start();
        }
    }
});

       
       
       
    }

   /* @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 50;
        int stroke = 13;

        // Shadow
        g2.setColor(new Color(0, 0, 0, 40));
        g2.fillRoundRect(6, 6, getWidth() - 6, getHeight() - 6, arc, arc);

        // Background
        g2.setColor(new Color(255, 140, 0, 180));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Gradient glow border
        int alpha = (int) (80 + 170 * glow);
        GradientPaint gp = new GradientPaint(
                0, 0, new Color(255, 193, 7, alpha),
                getWidth(), getHeight(), new Color(255, 140, 0, alpha)
        );
        
//        GradientPaint gp = new GradientPaint(
//        0, 0, new Color(0, 200, 255, alpha),   // bright cyan
//        getWidth(), getHeight(), new Color(0, 100, 255, alpha) // deep blue
//);
//GradientPaint gp = new GradientPaint(
//        0, 0, new Color(255, 255, 255, alpha),
//        getWidth(), getHeight(), new Color(200, 200, 200, alpha)
//);


        g2.setPaint(gp);
        g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int offset = stroke / 2;
        g2.drawRoundRect(
                offset,
                offset,
                getWidth() - stroke,
                getHeight() - stroke,
                arc,
                arc
        );

        g2.dispose();
    }*/
    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int arc = 50;
    int stroke = 6; // thick gradient border

    // Shadow
    g2.setColor(new Color(0, 0, 0, 40));
    g2.fillRoundRect(6, 6, getWidth() - 6, getHeight() - 6, arc, arc);

    // Background
    g2.setColor(new Color(255, 140, 0, 180));
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

    // Gradient glow border
    int alpha = (int) (80 + 170 * glow);
    GradientPaint gp = new GradientPaint(
                0, 0, new Color(255, 193, 7, alpha),
                getWidth(), getHeight(), new Color(255, 140, 0, alpha)
        );

    g2.setPaint(gp);
    g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    int offset = stroke / 2;
    g2.drawRoundRect(
            offset,
            offset,
            getWidth() - stroke,
            getHeight() - stroke,
            arc,
            arc
    );

    // Thin black outline on top
    g2.setColor(Color.BLACK);
    g2.setStroke(new BasicStroke(2f)); // very thin
    g2.drawRoundRect(
            offset,
            offset,
            getWidth() - stroke,
            getHeight() - stroke,
            arc,
            arc
    );

    g2.dispose();
}

}
