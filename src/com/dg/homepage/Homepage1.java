/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dg.homepage;

import com.dg.volunteerform.MainVolunteerFrame;
import com.dg.adminform.MainAdminFrame;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import com.dg.dbconnection.*;
import java.sql.*;

public class Homepage1 extends javax.swing.JFrame {

    private JPanel rolePanel;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Homepage1.class.getName());

    /**
     * Creates new form Homepage1
     */
    private String fullText = "Together We Bring Change";
    private int charIndex = 0;
    private float alpha = 0f;
    private Timer fadeTimer;

    private String[] slogans = {
        "Saving Lives, One Action At A Time",
        "Together We Stand, Together We Rebuild",
        "Hope In The Face Of Disaster",
        "Every Volunteer Makes A Difference",
        "Prepared Today, Safe Tomorrow",
        "Unity In Crisis, Strength In Recovery",
        "Your Help Creates Hope",
        "Disaster Relief Starts With You"
    };
    private String currentSlogan = "";
    private java.util.Random random = new java.util.Random();

    public Homepage1() {
        initComponents();
        jLabel9.setVisible(false);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);
        // getContentPane().setLayout(new BorderLayout());
//getContentPane().add(jPanel1, BorderLayout.CENTER);
        this.setResizable(true);
        Volunteer.setVisible(false);
        Admin.setVisible(false);
        BtnContinue.setVisible(false);
        Back.setVisible(false);
        buttonGroup1.add(Admin);
        buttonGroup1.add(Volunteer);
        Admin.setVisible(false);
        Volunteer.setVisible(false);
        setLocationRelativeTo(null);
        txtUsername.setVisible(false);
        txtPassword.setVisible(false);
        BtnSubmit.setVisible(false);
        BtnRegister.setVisible(false);

        jLabel7.setVisible(false);
        int randomIndex = random.nextInt(slogans.length);
        currentSlogan = slogans[randomIndex];
        getContentPane().remove(jLabelSlogan);

        jLabelSlogan = new javax.swing.JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 25));

                // Calculate center position
                java.awt.FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(currentSlogan);
                int x = (getWidth() - textWidth) / 2;
                int y = 30;

                // Draw white outline (border effect)
                g2.setColor(Color.WHITE);
                g2.drawString(currentSlogan, x - 1, y - 1);
                g2.drawString(currentSlogan, x - 1, y + 1);
                g2.drawString(currentSlogan, x + 1, y - 1);
                g2.drawString(currentSlogan, x + 1, y + 1);
                g2.drawString(currentSlogan, x - 1, y);
                g2.drawString(currentSlogan, x + 1, y);
                g2.drawString(currentSlogan, x, y - 1);
                g2.drawString(currentSlogan, x, y + 1);

                // Draw black text on top
                g2.setColor(new Color(40, 40, 40));
                g2.drawString(currentSlogan, x, y);

                g2.dispose();
            }
        };
        jLabel2 = new javax.swing.JLabel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new javax.swing.ImageIcon(getClass()
                        .getResource("/finaldisaster/Images/Screenshot 2026-01-23 205707.png"))
                        .getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        getContentPane().add(jLabelSlogan, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 380, 700, 50));
        getContentPane().setComponentZOrder(jLabelSlogan, 0);
        getContentPane().remove(jLabel4);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                // Stretch the background label to the new window size
                int centerX = (getWidth() - 220) / 2;
                txtUsername.setBounds(centerX, txtUsername.getY() + 30, 220, txtUsername.getHeight());
                txtPassword.setBounds(centerX, txtPassword.getY() + 30 + 2, 220, txtPassword.getHeight());
                txtUsername.setVisible(true);
                txtPassword.setVisible(true);
                BtnSubmit.setBounds(centerX + (220 - BtnSubmit.getWidth()) / 2, txtPassword.getY() + 27 + txtPassword.getHeight() + 10, BtnSubmit.getWidth(), BtnSubmit.getHeight());
                BtnSubmit.setVisible(true);
                BtnRegister.setBounds(centerX + 220 - BtnRegister.getWidth(), BtnSubmit.getY() + 30 + BtnSubmit.getHeight() + 8, BtnRegister.getWidth(), BtnRegister.getHeight());
                BtnRegister.setVisible(true);

                jLabel7.setBounds(centerX + 10, BtnRegister.getY(), 160, BtnRegister.getHeight());
                jLabel7.setVisible(true);
                jLabel8.setBounds(centerX + (220 - jLabel8.getWidth()) / 2, txtUsername.getY() - 35, jLabel8.getWidth(), jLabel8.getHeight());
                buttonPanel.setBounds(centerX - 15, jLabel8.getY() - 10, 250, BtnRegister.getY() + BtnRegister.getHeight() - jLabel8.getY() + 20);
//getContentPane().setComponentZOrder(buttonPanel, getContentPane().getComponentCount() - 1);
                getContentPane().setComponentZOrder(jPanel1, getContentPane().getComponentCount() - 1);
                getContentPane().setComponentZOrder(buttonPanel, getContentPane().getComponentCount() - 2);
//Admin.setBounds(centerX, txtPassword.getY() + txtPassword.getHeight() + 10, 90, Admin.getHeight());
                ///Volunteer.setBounds(centerX + 90, txtPassword.getY() + txtPassword.getHeight()+10, 90, Volunteer.getHeight());
Admin.setBounds(centerX + 60, txtPassword.getY() + txtPassword.getHeight() - 30, 80, Admin.getHeight());
                Volunteer.setBounds(centerX + 60, Admin.getY() + Admin.getHeight() + 5, 100, Volunteer.getHeight());
//Back.setBounds(centerX+10, Volunteer.getY() + Volunteer.getHeight() + 8, 80, Back.getHeight());
//BtnContinue.setBounds(centerX + 120, Volunteer.getY() + Volunteer.getHeight() + 8, 100, BtnContinue.getHeight());
                Back.setBounds(centerX + 10, Volunteer.getY() + Volunteer.getHeight() + 10, 100, Back.getHeight());
                BtnContinue.setBounds(centerX + 120, Volunteer.getY() + Volunteer.getHeight() + 10, 100, BtnContinue.getHeight());
//jLabel9.setBounds(centerX + (220 - jLabel9.getWidth()) / 2, Admin.getY() - 30, jLabel9.getWidth(), jLabel9.getHeight());
                jLabel9.setBounds(centerX + (220 - 135) / 2, Admin.getY() - 30, jLabel9.getWidth(), jLabel9.getHeight());
                getContentPane().setComponentZOrder(jLabelSlogan, 0);
//jLabelSlogan.setBounds((getWidth() - 700) / 2, 20, 700, 50);
                jLabelSlogan.setBounds((getWidth() - 700) / 2, buttonPanel.getY() + buttonPanel.getHeight() + 15 + 20 + 30 + 30, 700, 50);
                jLabel4.setBounds((getWidth() - 385) / 2, 100, 500, 80);
                getContentPane().setComponentZOrder(jLabel4, 0);
                jLabel5.setBounds(100, getHeight() - 150, 150, 25);
                jLabel1.setBounds(100, getHeight() - 120, 310, 25);
                jLabel2.setBounds(0, 0, getWidth(), getHeight());

                // Stretch the main container panel to the new window size
                jPanel1.setBounds(0, 0, getWidth(), getHeight());

                // SCALE THE IMAGE: This forces the image to stretch to fill the screen
                if (jLabel2.getIcon() != null) {
                    java.awt.Image img = ((javax.swing.ImageIcon) new javax.swing.ImageIcon(getClass().getResource("/finaldisaster/Images/Screenshot 2026-01-23 205707.png"))).getImage();
                    java.awt.Image scaledImg = img.getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH);
                    jLabel2.setIcon(new javax.swing.ImageIcon(scaledImg));
                }
            }
        });

        /* this.addComponentListener(new java.awt.event.ComponentAdapter() {
    @Override
    public void componentResized(java.awt.event.ComponentEvent e) {
        int w = getWidth();
        int h = getHeight();

        // Stretch the container panel to full screen width/height
        jPanel1.setBounds(0, 0, w, h);
        
        // Stretch the background label to full screen width/height
        jLabel2.setBounds(0, 0, w, h);

        // Scale the actual image file to match the new width and height
        try {
            java.awt.Image img = new javax.swing.ImageIcon(getClass().getResource("/finaldisaster/Images/Screenshot 2026-01-23 205707.png")).getImage();
            java.awt.Image scaledImg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
            jLabel2.setIcon(new javax.swing.ImageIcon(scaledImg));
        } catch (Exception ex) {
            System.out.println("Image scaling failed: " + ex.getMessage());
        }
        
        // OPTIONAL: Move the login text/fields to the center horizontally
        // int centerX = (w - 240) / 2; // 240 is your login panel width
        // (You would need to call setLocation for your login components here)
    }
});*/
        //getContentPane().add(jLabel2);
//getContentPane().setComponentZOrder(jLabel2, getContentPane().getComponentCount() - 1);
        // getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 490));
        // Use existing jLabel4 and override paintComponent
        jLabel4 = new javax.swing.JLabel() {

            ///
    @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 30));

                // If animation is complete, draw the entire text
                if (charIndex > fullText.length()) {
                    // Draw black outline (border effect)
                    g2.setColor(Color.BLACK);
                    g2.drawString(fullText, 4, 34);
                    g2.drawString(fullText, 4, 36);
                    g2.drawString(fullText, 6, 34);
                    g2.drawString(fullText, 6, 36);
                    g2.drawString(fullText, 4, 35);
                    g2.drawString(fullText, 6, 35);
                    g2.drawString(fullText, 5, 34);
                    g2.drawString(fullText, 5, 36);

                    // Draw white text on top
                    g2.setColor(new Color(255, 215, 0));
                    g2.drawString(fullText, 5, 35);
                } else {
                    // Draw all previous characters fully visible
                    if (charIndex > 1) {
                        String prevText = fullText.substring(0, charIndex - 1);

                        // Black outline
                        g2.setColor(Color.BLACK);
                        g2.drawString(prevText, 4, 34);
                        g2.drawString(prevText, 4, 36);
                        g2.drawString(prevText, 6, 34);
                        g2.drawString(prevText, 6, 36);
                        g2.drawString(prevText, 4, 35);

                        g2.drawString(prevText, 6, 35);
                        g2.drawString(prevText, 5, 34);
                        g2.drawString(prevText, 5, 36);

                        // White text
                        g2.setColor(Color.WHITE);
                        g2.drawString(prevText, 5, 35);
                    }

                    // Draw current fading-in character
                    if (charIndex > 0 && charIndex <= fullText.length()) {
                        String currentChar = fullText.substring(charIndex - 1, charIndex);
                        int x = 5 + g2.getFontMetrics().stringWidth(fullText.substring(0, charIndex - 1));

                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

                        // Black outline
                        g2.setColor(Color.BLACK);
                        g2.drawString(currentChar, x - 1, 34);
                        g2.drawString(currentChar, x - 1, 36);
                        g2.drawString(currentChar, x + 1, 34);
                        g2.drawString(currentChar, x + 1, 36);
                        g2.drawString(currentChar, x - 1, 35);
                        g2.drawString(currentChar, x + 1, 35);
                        g2.drawString(currentChar, x, 34);
                        g2.drawString(currentChar, x, 36);

                        // White text
                        g2.setColor(new Color(255, 215, 0));
                        g2.drawString(currentChar, x, 35);
                    }
                }

                g2.dispose();
            }

        };
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));

        // Make sure the label is added to the JFrame again
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 500, 60));
        getContentPane().setComponentZOrder(jLabel4, 0);
        createButtonPanel();
//getContentPane().add(jLabel4);
//createRolePanel();
        addPlaceholder(txtUsername, "Username");
        addPasswordPlaceholder(txtPassword, "Password");
        startFadeIn(); // start the fade-in timer

        getContentPane().validate();
        java.awt.Insets insets = getInsets();
//setSize(getWidth() + 1, getHeight());
//setSize(getWidth() - 1, getHeight());
        getContentPane().dispatchEvent(new java.awt.event.ComponentEvent(getContentPane(), java.awt.event.ComponentEvent.COMPONENT_RESIZED));
    }

    private void startFadeIn() {
        // Initially, label is empty
        // jLabel4.setText("");

        fadeTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.3f; // increase opacity
                if (alpha >= 1f) {
                    alpha = 0f;
                    charIndex++; // move to next character
                    if (charIndex > fullText.length()) {
                        fadeTimer.stop();
                        //charIndex = fullText.length();
                    }
                }
                jLabel4.repaint();
            }
        });
        fadeTimer.start();

    }

    private void addPlaceholder(javax.swing.JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void addPasswordPlaceholder(javax.swing.JPasswordField passwordField, String placeholder) {
        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // Show text, not dots

        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•'); // Show dots when typing
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(placeholder);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0); // Show placeholder text
                }
            }
        });
    }
//   private void createRolePanel() {
//    rolePanel = new GlowPanel(); // ✅ Now assigns to class variable
//    rolePanel.setLayout(null);
//    rolePanel.setOpaque(false);
//    rolePanel.setBounds(450, 280, 200, 110);
//    rolePanel.setVisible(false); // Hidden initially
//    Admin.setBounds(20, 10, 120, 30);
//    Volunteer.setBounds(20, 45, 120, 30);
//    Back.setBounds(10, 85, 80, 25);
//    BtnContinue.setBounds(110, 85, 100, 25);
//
//    rolePanel.add(Admin);
//    rolePanel.add(Volunteer);
//    rolePanel.add(Back);
//    rolePanel.add(BtnContinue);
//    getContentPane().add(rolePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, 200, 110));
//    getContentPane().setComponentZOrder(rolePanel, 5);
//}

    JPanel buttonPanel = new GlowPanel();

    /*private void createButtonPanel() {
 
//         JPanel buttonPanel = new GlowPanel();   
    
    buttonPanel.setLayout(null);
    buttonPanel.setOpaque(false); // Make background transparent so custom paint shows
   // buttonPanel.setBounds(350, 125, 240, 200);
   // buttonPanel.setBounds(centerX - 15, jLabel8.getY() - 10, 250, BtnRegister.getY() + BtnRegister.getHeight() - jLabel8.getY() + 20);
    
    getContentPane().add(buttonPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 125, 240, 200));
    getContentPane().setComponentZOrder(buttonPanel, 5);
    getContentPane().setComponentZOrder(jLabel7, 0);
    getContentPane().setComponentZOrder(Volunteer, 0);
    getContentPane().setComponentZOrder(Back, 0);
    getContentPane().setComponentZOrder(BtnContinue, 0);
    getContentPane().setComponentZOrder(txtUsername, 0);
    getContentPane().setComponentZOrder(txtPassword, 0);
    getContentPane().setComponentZOrder(jLabel8, 0);
    getContentPane().add(jLabel9);
getContentPane().setComponentZOrder(jLabel9, 0);
    
}
     */
    private void createButtonPanel() {
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false);

        getContentPane().add(buttonPanel);  // no AbsoluteConstraints = no initial wrong position
        getContentPane().setComponentZOrder(buttonPanel, 5);
        getContentPane().setComponentZOrder(jLabel7, 0);
        getContentPane().setComponentZOrder(Volunteer, 0);
        getContentPane().setComponentZOrder(Back, 0);
        getContentPane().setComponentZOrder(BtnContinue, 0);
        getContentPane().setComponentZOrder(txtUsername, 0);
        getContentPane().setComponentZOrder(txtPassword, 0);
        getContentPane().setComponentZOrder(jLabel8, 0);
        getContentPane().add(jLabel9);
        getContentPane().setComponentZOrder(jLabel9, 0);
    }

    private void setRandomSlogan() {
        // Pick a random slogan from the array
        int randomIndex = (int) (Math.random() * slogans.length);
        jLabelSlogan.setText(slogans[randomIndex]);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        Admin = new javax.swing.JRadioButton();
        BtnRegister = new javax.swing.JButton();
        BtnSubmit = new javax.swing.JButton();
        txtUsername = new javax.swing.JTextField();
        BtnContinue = new javax.swing.JButton();
        Volunteer = new javax.swing.JRadioButton();
        txtPassword = new javax.swing.JPasswordField();
        Back = new javax.swing.JButton();
        jLabelSlogan = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        Admin.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Admin.setForeground(new java.awt.Color(0, 51, 51));
        Admin.setText("Admin");
        Admin.addActionListener(this::AdminActionPerformed);

        BtnRegister.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        BtnRegister.setText("Register");
        BtnRegister.addActionListener(this::BtnRegisterActionPerformed);

        BtnSubmit.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        BtnSubmit.setText("Submit");
        BtnSubmit.addActionListener(this::BtnSubmitActionPerformed);

        txtUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(255, 255, 255));
        txtUsername.setText("Username");
        txtUsername.addActionListener(this::txtUsernameActionPerformed);

        BtnContinue.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnContinue.setText("Continue");
        BtnContinue.addActionListener(this::BtnContinueActionPerformed);

        Volunteer.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Volunteer.setText("Volunteer");
        Volunteer.addActionListener(this::VolunteerActionPerformed);

        txtPassword.addActionListener(this::txtPasswordActionPerformed);

        Back.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Back.setText("Back");
        Back.setMinimumSize(new java.awt.Dimension(80, 23));
        Back.addActionListener(this::BackActionPerformed);

        jLabelSlogan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Login");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Contact:");
        jLabel5.setPreferredSize(new java.awt.Dimension(85, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Email:disasterguard220@gmail.com");
        jLabel1.setPreferredSize(new java.awt.Dimension(310, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Don't Have an Account?");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/homepage/Images/logo2.jpg"))); // NOI18N
        jLabel3.setText("jLabel3");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Dont have an account?");

        jPanel1.setPreferredSize(new java.awt.Dimension(1010, 513));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dg/homepage/Images/Screenshot 2026-04-03 21573156107111.png"))); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Choose Your Role");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1713, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1159, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(366, 366, 366)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(602, 602, 602)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(440, 440, 440)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(360, 360, 360)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Admin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Volunteer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(Back, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(360, 360, 360)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addComponent(BtnRegister))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(jLabelSlogan))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(430, 430, 430)
                        .addComponent(BtnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(480, 480, 480)
                        .addComponent(BtnContinue))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(360, 360, 360)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 555, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1315, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30)
                                .addComponent(jLabel8)
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Admin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(Volunteer)))
                                .addGap(15, 15, 15)
                                .addComponent(Back, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BtnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabelSlogan)
                                .addGap(110, 110, 110)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(260, 260, 260)
                                .addComponent(BtnSubmit))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(240, 240, 240)
                                .addComponent(BtnContinue))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(210, 210, 210)
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1159, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnContinueActionPerformed
        // TODO add your handling code here:
        if (Volunteer.isSelected()) {
            // Open MainVolunteerFrame only for Volunteer
            MainVolunteerFrame mvf = new MainVolunteerFrame();
            mvf.setVisible(true);

            // Close current homepage
            this.dispose();
        } else if (Admin.isSelected()) {
            MainAdminFrame maf = new MainAdminFrame();
            maf.setVisible(true);
            this.dispose();

        } else {
            // No option selected
            JOptionPane.showMessageDialog(this,
                    "Please select Volunteer to continue.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtnContinueActionPerformed

    private void BtnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegisterActionPerformed
        BtnSubmit.setVisible(false);
        jLabel9.setVisible(true);
        //rolePanel.setVisible(true);
        // Keep Register button visible ✅
        BtnRegister.setVisible(false);
        jLabel7.setVisible(false);
        txtPassword.setVisible(false);
        txtUsername.setVisible(false);
        jLabel8.setVisible(false);
        // rolePanel.setVisible(true);
        // Show radio buttons and continue
        Volunteer.setVisible(true);
        Admin.setVisible(true);
        BtnContinue.setVisible(true);
        Back.setVisible(true);
        //jLabel7.setVisible(true);
    }//GEN-LAST:event_BtnRegisterActionPerformed

    private void AdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdminActionPerformed

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed

        BtnSubmit.setVisible(true);
        jLabel9.setVisible(false);
        BtnRegister.setVisible(true);
        jLabel7.setVisible(true);
        txtPassword.setVisible(true);
        txtUsername.setVisible(true);
        jLabel8.setVisible(true);
//rolePanel.setVisible(false); 
        // Hide radio buttons and Continue button
        Volunteer.setVisible(false);
        Admin.setVisible(false);
        BtnContinue.setVisible(false);
        Back.setVisible(false);
//rolePanel.setVisible(false);
        // Clear radio button selection
        buttonGroup1.clearSelection();

        // TODO add your handling code here:
    }//GEN-LAST:event_BackActionPerformed

    private void BtnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSubmitActionPerformed
        // TODO add your handling code here:

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()
                || username.equals("Username") || password.equals("Password")) {
            JOptionPane.showMessageDialog(this, "Please enter username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = SQLiteConnect.Connectordb(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String designation = rs.getString("designation_type").toLowerCase();
                User user = null;

                switch (designation) {
                    case "super admin":
                        user = new SuperAdminUser(username);
                        break;
                    case "admin":
                        user = new AdminUser(username);
                        break;
                    case "volunteer":
                        user = new VolunteerUser(username);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Unknown role: " + designation, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Open respective dashboard
                if (user != null) {
                    user.openDashboard();
                    this.dispose(); // close homepage
                }

            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

    } catch(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
        /*} catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getClass().getSimpleName() + " - " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }*/
    }//GEN-LAST:event_BtnSubmitActionPerformed

    private void VolunteerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolunteerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VolunteerActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Homepage1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Admin;
    private javax.swing.JButton Back;
    private javax.swing.JButton BtnContinue;
    private javax.swing.JButton BtnRegister;
    private javax.swing.JButton BtnSubmit;
    private javax.swing.JRadioButton Volunteer;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelSlogan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
