/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.volunteerdashboard;
//package com.dg.admindashboard;

import javax.swing.JDesktopPane;
import com.dg.dao.VolunteerDAO;
import com.dg.model.Volunteer;
import com.dg.dbconnection.*;
import java.awt.Color;
import static java.awt.Color.WHITE;
import javax.swing.BoxLayout;  // ✅ ADD THIS LINE!
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author USER
 */
public class ViewVolunteerProfile extends javax.swing.JInternalFrame {

    private JDesktopPane desktop;
    private javax.swing.JDialog currentPopup = null; // ✅ tracks open popup
    /**
     * Creates new form ViewVolunteerProfile
     */
    private final java.util.Map<String, String> disasterIcons = new java.util.HashMap<>() {
        {
            /*put("Flood", "🌊");
    put("Cyclone", "🌀");
    put("Landslide", "🏔️");
    put("River Erosion", "🌧️");
    put("Fire", "🔥");
    put("Storm", "⚡");
    put("Earthquake", "🌍");*/
            put("Floods", "/logo/icons8-flood-53.png");
            put("Cyclones", "/logo/icons8-cyclone-48.png");
            put("Fires", "/logo/icons8-fire-48.png");
            put("Earthquakes", "/logo/icons8-earthquake-64.png");
            put("Storms", "/logo/icons8-storm-48.png");
            put("Landslides", "/logo/icons8-landslide-64.png");
            put("River Erosion", "/logo/icons8-river-50.png");
            put("Swimming", "/logo/icons8-swimming-64.png");
            put("Driving", "/logo/icons8-driving-64.png");
            put("SearchAndRescue", "/logo/icons8-rescue-64.png");
            put("MedicalTraining", "/logo/icons8-medical-64.png");
//     put("Communication", "/logo/icons8-communication-48.png");
//     put("IT", "/logo/icons8-technology-64.png");
//     put("Engineering", "/logo/icons8-technology-64.png");

            put("TechnicalSkill", "/logo/icons8-service-64.png");
            put("LanguageSkill", "/logo/icons8-language-48.png");

        }
    };

    public ViewVolunteerProfile(JDesktopPane desktop, String username, boolean showEditButton) {
        initComponents();

        jScrollPane1.setVerticalScrollBarPolicy(
                javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setHorizontalScrollBarPolicy(
                javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane1.setBackground(Color.WHITE);

        MainInfoPanel.setPreferredSize(null);
        MainPanel.setPreferredSize(null);

// Fix extra space below Edit button
        /*SwingUtilities.invokeLater(() -> {
            int contentHeight = 0;
            for (java.awt.Component c : MainInfoPanel.getComponents()) {
                contentHeight += c.getPreferredSize().height + 18; // 18 = gap
            }
            contentHeight += 30; // small bottom padding after edit button

            MainInfoPanel.setPreferredSize(
                    new java.awt.Dimension(MainInfoPanel.getPreferredSize().width, contentHeight)
            );
            MainPanel.setPreferredSize(
                    new java.awt.Dimension(MainPanel.getPreferredSize().width, contentHeight)
            );
            jScrollPane1.revalidate();
            jScrollPane1.repaint();
        });*/
        // Fix scroll only — don't touch layouts
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        /*JPanel wrapper = new JPanel(new java.awt.BorderLayout());
        wrapper.add(MainPanel, java.awt.BorderLayout.NORTH);
        jScrollPane1.setViewportView(wrapper);*/
        //jScrollPane1.setViewportView(MainPanel);
        jScrollPane1.setPreferredSize(null);
        setWhiteBackground();
        this.setResizable(false);
        Work.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        styleEditButton();

        this.desktop = desktop;
        btnEdit.setVisible(showEditButton);
        Username.setText(username);

        // Keep FlowLayout for badge panels only
        // TypesOfDisasterHandledPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        SkillPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Load data
        loadVolunteerProfile(username);

        // After data loaded, refresh
        SwingUtilities.invokeLater(() -> {
            MainPanel.revalidate();
            MainPanel.repaint();
            //wrapper.revalidate();
            //wrapper.repaint();
        });

        btnEdit.addActionListener(e -> openEditFrame(username));
    }

// do not add it for badges
/*private JPanel createSkillCard(String icon, String category, String skillName) {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(Color.WHITE);
    card.setPreferredSize(new java.awt.Dimension(160, 100));
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
        BorderFactory.createEmptyBorder(12, 12, 12, 12)
    ));

    // Icon
    JLabel iconLabel = new JLabel(icon);
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
    iconLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

    // Category (e.g. "Physical Skill")
    JLabel categoryLabel = new JLabel(category);
    categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    categoryLabel.setForeground(new Color(150, 150, 150));
    categoryLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

    // Skill name (e.g. "Swimming")
    JLabel nameLabel = new JLabel(skillName);
    nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
    nameLabel.setForeground(new Color(30, 30, 30));
    nameLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

    card.add(iconLabel);
    card.add(javax.swing.Box.createVerticalStrut(6));
    card.add(categoryLabel);
    card.add(javax.swing.Box.createVerticalStrut(4));
    card.add(nameLabel);

    return card;
}



     */
// do not add it for badges
    /*private JPanel createSkillCard(String icon, String category, String skillName, Color accentColor) {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(Color.WHITE);
    card.setPreferredSize(new java.awt.Dimension(160, 110));
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
        BorderFactory.createEmptyBorder(12, 12, 12, 12)
    ));

    // Icon
    JLabel iconLabel = new JLabel(icon);
//    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
//    iconLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

try {
    java.net.URL imgURL = getClass().getResource(icon);
    if (imgURL != null) {
        ImageIcon img = new ImageIcon(imgURL);
        java.awt.Image scaled = img.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        iconLabel = new JLabel(new ImageIcon(scaled));
    } else {
        iconLabel = new JLabel("?"); // fallback if path wrong
    }
} catch (Exception e) {
    iconLabel = new JLabel("?");
}
//iconLabel.setAlignmentX(0.3f); // 0.0f = far left, 0.5f = center, lower = more left
//iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0)); // left padding

    // Category (e.g. "Physical Skill") — stays plain
    JPanel iconWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
iconWrapper.setBackground(Color.WHITE);
iconWrapper.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
iconWrapper.add(iconLabel);




    JLabel categoryLabel = new JLabel(category);
    categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    categoryLabel.setForeground(new Color(150, 150, 150));
    categoryLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

    // Skill name inside rounded colorful box
    JLabel nameLabel = new JLabel(skillName, SwingConstants.CENTER) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
            super.paintComponent(g);
        }
    };
    nameLabel.setOpaque(false);
    nameLabel.setBackground(accentColor);
    nameLabel.setForeground(Color.WHITE);
    nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
    nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    nameLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

    card.add(iconWrapper); 
    card.add(javax.swing.Box.createVerticalStrut(6));
    card.add(categoryLabel);
    card.add(javax.swing.Box.createVerticalStrut(6));
    card.add(nameLabel);

    return card;
}*/
    private JPanel createSkillCard(String iconPath, String category, String skillName, Color accentColor) {
        JPanel card = new JPanel();
        card.putClientProperty("skillName", skillName);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new java.awt.Dimension(160, 110));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // ── Icon ──
        JLabel iconLabel;
        if (iconPath.startsWith("/")) {
            try {
                java.net.URL imgURL = getClass().getResource(iconPath);
                if (imgURL != null) {
                    ImageIcon img = new ImageIcon(imgURL);
                    java.awt.Image scaled = img.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
                    iconLabel = new JLabel(new ImageIcon(scaled));
                } else {
                    iconLabel = new JLabel("?");
                }
            } catch (Exception e) {
                iconLabel = new JLabel("?");
            }
        } else {
            iconLabel = new JLabel(iconPath);
            iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        }

        // ── Category label ──
        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        categoryLabel.setForeground(new Color(0, 0, 204));

        // ── Skill name rounded box ──
        JLabel nameLabel = new JLabel(skillName, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        nameLabel.setOpaque(false);
        nameLabel.setBackground(accentColor);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        // ✅ ALL components LEFT_ALIGNMENT — this is the key fix
        iconLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        categoryLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        nameLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        // ✅ nameLabel needs max width to fill card properly
        nameLabel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, nameLabel.getPreferredSize().height + 10));

        card.add(iconLabel);
        card.add(javax.swing.Box.createVerticalStrut(6));
        card.add(categoryLabel);
        card.add(javax.swing.Box.createVerticalStrut(6));
        card.add(nameLabel);

        // ✅ Click to show full details popup
//card.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//card.addMouseListener(new java.awt.event.MouseAdapter() {
//    @Override
//    public void mouseClicked(java.awt.event.MouseEvent e) {
//        // Split by comma and show each skill on its own line
//        String[] skills = skillName.split(",");
//        StringBuilder sb = new StringBuilder("<html><b>" + category + ":</b><br><br>");
//        for (String s : skills) {
//            sb.append("• ").append(s.trim()).append("<br>");
//        }
//        sb.append("</html>");
//
//        JOptionPane.showMessageDialog(
//            card,
//            sb.toString(),
//            category,
//            JOptionPane.INFORMATION_MESSAGE
//        );
//    }
//});
        card.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                e.consume();
                if (e.getSource() != card) {
                    return;
                }
                if (skillName.split(",").length <= 1) {
                    return;
                }

                if (!skillName.contains(",")) {
                    return; // only popup for multiple values
                }
                if (currentPopup != null && currentPopup.isVisible()) {
                    currentPopup.dispose();
                }
                // ── Create dialog ──
                javax.swing.JDialog dialog = new javax.swing.JDialog();
                currentPopup = dialog;
                dialog.setUndecorated(true); // removes default title bar
                dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
                dialog.getContentPane().setBackground(Color.WHITE);

                // ── Title bar ──
                JPanel titleBar = new JPanel();
                titleBar.setBackground(accentColor);
                titleBar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 8));
                titleBar.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                JLabel titleLabel = new JLabel("💻 " + category);
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
                titleLabel.setForeground(Color.WHITE);
                titleBar.add(titleLabel);
                titleBar.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 40));

                // ── Skills list ──
                JPanel skillsPanel = new JPanel();
                skillsPanel.setLayout(new BoxLayout(skillsPanel, BoxLayout.Y_AXIS));
                skillsPanel.setBackground(Color.WHITE);
                skillsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                skillsPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

                String[] skills = skillName.split(",");
                for (String s : skills) {
                    JLabel skillLabel = new JLabel("● " + s.trim());
                    skillLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    skillLabel.setForeground(new Color(40, 40, 40));
                    skillLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
                    skillLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                    skillsPanel.add(skillLabel);
                }

                // ── Close button ──
                JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
                btnPanel.setBackground(Color.WHITE);
                btnPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                JButton closeBtn = new JButton("Close");
                closeBtn.setBackground(accentColor);
                closeBtn.setForeground(Color.WHITE);
                closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
                closeBtn.setFocusPainted(false);
                closeBtn.setBorderPainted(false);
                closeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                closeBtn.addActionListener(ev -> dialog.dispose());
                btnPanel.add(closeBtn);
                btnPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 40));

                // ── Border around whole dialog ──
                ((JPanel) dialog.getContentPane()).setBorder(
                        BorderFactory.createLineBorder(accentColor, 2)
                );

                dialog.add(titleBar);
                dialog.add(skillsPanel);
                dialog.add(btnPanel);

                dialog.pack();
                dialog.setLocationRelativeTo(card); // appears near the card
                dialog.setVisible(true);
            }
        });

        return card;
    }

    private void makeGradientButton(JButton button) {

        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(33, 150, 243),
                        0, c.getHeight(), new Color(3, 169, 244)
                );

                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 30, 30);

                g2.dispose();

                super.paint(g, c);
            }
        });
    }

    private void styleEditButton() {
        btnEdit.setText("Edit Profile");
        btnEdit.setBackground(new Color(253, 126, 20));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEdit.setFocusPainted(false);
        btnEdit.setBorderPainted(false);
        btnEdit.setContentAreaFilled(true);
        btnEdit.setOpaque(true);
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.setPreferredSize(new java.awt.Dimension(160, 40));

        // Hover effect
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnEdit.setBackground(new Color(0, 80, 170));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnEdit.setBackground(new Color(253, 126, 20));
            }
        });
    }

    private void setWhiteBackground() {
        java.awt.Color white = new java.awt.Color(255, 255, 255);

        getContentPane().setBackground(white);

        // set all visible panels to white
        /*jPanel1.setBackground(white);
        jScrollPane1.setBackground(white);
        btnPanel.setBackground(white);
        AddressPanel.setBackground(white);
        ContactPanel.setBackground(white);
        //EducationPanel.setBackground(white);
        MainInfoPanel.setBackground(white);
        MainPanel.setBackground(white);
        PersonalDetailPanel.setBackground(white);
        PicturePanel.setBackground(white);
        SkillPanel.setBackground(white);
        // TypesOfDisasterHandledPanel.setBackground(white);
        //WorkPanel.setBackground(white);*/
        //gradientPanel.setBackground(white); // if exists
    }

    private void openEditFrame(String username) {

        this.dispose(); // close profile

        VolunteerEditProfile edit
                = new VolunteerEditProfile(desktop, username);

        desktop.add(edit);

        int margin = 40;

        edit.setBounds(
                margin,
                margin,
                desktop.getWidth() - 2 * margin,
                desktop.getHeight() - 2 * margin
        );

        edit.setVisible(true);

        try {
            edit.setSelected(true);
            edit.setMaximum(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private JLabel createRoundedBadge(String text, Color bg) {
        JLabel badge = new JLabel(text, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setOpaque(false);
        badge.setBackground(bg);
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        return badge;
    }

    private JLabel createBadge(String text) {
        return createRoundedBadge(text, new Color(33, 150, 243)); // default blue
    }

    /* private void loadSkillBadges(Volunteer v, JPanel panel) {
    panel.removeAll();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Color.WHITE);

    // Title row
    panel.add(createSectionTitle("Skills"));
    panel.add(javax.swing.Box.createVerticalStrut(8)); // gap

    // Badges row
    JPanel badgeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
    badgeRow.setBackground(Color.WHITE);
    badgeRow.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

    boolean added = false;

    if ("Yes".equalsIgnoreCase(v.getSwimming())) {
        badgeRow.add(createRoundedBadge("Swimming", new Color(33, 150, 243)));
        added = true;
    }
    if (v.getDriving() != null && !v.getDriving().equalsIgnoreCase("No")) {
        badgeRow.add(createRoundedBadge("Driving: " + v.getDriving(), new Color(76, 175, 80)));
        added = true;
    }
    if (v.getMedicalTraining() != null && !v.getMedicalTraining().equalsIgnoreCase("No")) {
        badgeRow.add(createRoundedBadge("Medical: " + v.getMedicalTraining(), new Color(244, 67, 54)));
        added = true;
    }
    if ("Yes".equalsIgnoreCase(v.getSearchAndRescue())) {
        badgeRow.add(createRoundedBadge("Search & Rescue", new Color(255, 152, 0)));
        added = true;
    }
    if (v.getLanguageSkills() != null &&
        !v.getLanguageSkills().trim().isEmpty() &&
        !v.getLanguageSkills().equalsIgnoreCase("No")) {
        badgeRow.add(createRoundedBadge("Language: " + v.getLanguageSkills().trim(), new Color(156, 39, 176)));
        added = true;
    }
    if (v.getTechnicalSkills() != null &&
        !v.getTechnicalSkills().trim().isEmpty() &&
        !v.getTechnicalSkills().equalsIgnoreCase("No")) {
        for (String tech : v.getTechnicalSkills().split(",")) {
            badgeRow.add(createRoundedBadge("Technical: " + tech.trim(), new Color(0, 150, 136)));
        }
        added = true;
    }
    if (!added) {
        badgeRow.add(createRoundedBadge("No Skills Added", new Color(158, 158, 158)));
    }

    panel.add(badgeRow);
    panel.revalidate();
    panel.repaint();
}*/
    private void loadSkillBadges(Volunteer v, JPanel panel) {
        panel.removeAll();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 12));
        panel.setBackground(Color.WHITE);
        //panel.add(createSectionTitle("Skills"));
        panel.add(createSectionTitle("Skills"));
        panel.add(javax.swing.Box.createVerticalStrut(8));
        boolean added = false;

        if ("Yes".equalsIgnoreCase(v.getSwimming())) {
            // panel.add(createSkillCard("🏊", "Physical Skill", "Swimming"));
            panel.add(createSkillCard("/logo/icons8-swimming-64.png", "Physical Skill", "Swimming", new Color(255, 152, 0)));
            added = true;
        }
        if (v.getDriving() != null && !v.getDriving().equalsIgnoreCase("No")) {
            //panel.add(createSkillCard("🚗", "Driving Skill", v.getDriving()));
            panel.add(createSkillCard("/logo/icons8-driving-64.png", "Driving Skill", v.getDriving(), new Color(76, 175, 80)));
            added = true;
        }
        if (v.getMedicalTraining() != null && !v.getMedicalTraining().equalsIgnoreCase("No")) {
            ///panel.add(createSkillCard("🏥", "Medical Skill", v.getMedicalTraining()));
        panel.add(createSkillCard("/logo/icons8-medical-48.png", "Medical Skill", v.getMedicalTraining(), new Color(244, 67, 54)));
            added = true;
        }
        if ("Yes".equalsIgnoreCase(v.getSearchAndRescue())) {
            // panel.add(createSkillCard("🔍", "Emergency Skill", "Search & Rescue"));
            panel.add(createSkillCard("/logo/icons8-rescue-64.png", "Emergency Skill", "Search & Rescue", new Color(33, 150, 243)));  // blue
            added = true;
        }
        if (v.getLanguageSkills() != null
                && !v.getLanguageSkills().trim().isEmpty()
                && !v.getLanguageSkills().equalsIgnoreCase("No")) {
            //panel.add(createSkillCard("🌐", "Language Skill", v.getLanguageSkills().trim()));
            panel.add(createSkillCard("/logo/icons8-language-48.png", "Language Skill", v.getLanguageSkills(), new Color(156, 39, 176)));
            added = true;
        }
        if (v.getTechnicalSkills() != null
                && !v.getTechnicalSkills().trim().isEmpty()
                && !v.getTechnicalSkills().equalsIgnoreCase("No")) {
//        for (String tech : v.getTechnicalSkills().split(",")) {
//            //panel.add(createSkillCard("💻", "Technical Skill", tech.trim()));
//            panel.add(createSkillCard("💻", "Technical Skill",tech.trim(),      new Color(0, 150, 136))); 
//        }
            panel.add(createSkillCard("/logo/icons8-services-64.png", "Technical Skill", v.getTechnicalSkills().trim(), new Color(0, 150, 136)));

            added = true;
        }

//    if (!added) {
//        panel.add(createSkillCard("❌", "No Skills", "Not Added"));
//    } 
        if (!added) {
            panel.add(createSkillCard("/logo/icons8-cross-48.png", "No Skills", "Not Added", new Color(158, 158, 158)));
        }
        // panel.add(createSkillCard("🏊", "Physical Skill", "Swimming",       new Color(255, 152, 0)));   // orange
//panel.add(createSkillCard("🚗", "Driving Skill",  v.getDriving(),   new Color(76, 175, 80)));   // green
//panel.add(createSkillCard("🏥", "Medical Skill",  v.getMedicalTraining(),   new Color(244, 67, 54)));   // red
//panel.add(createSkillCard("🔍", "Emergency Skill","Search & Rescue",new Color(33, 150, 243)));  // blue
//panel.add(createSkillCard("🌐", "Language Skill", v.getLanguageSkills(),  new Color(156, 39, 176)));  // purple
//panel.add(createSkillCard("💻", "Technical Skill",tech.trim(),      new Color(0, 150, 136)));   // teal

        panel.revalidate();
        panel.repaint();
    }

    private String makeGradientName(String name) {
        String[] colors = {"#1565C0", "#1976D2", "#1E88E5", "#2196F3", "#42A5F5", "#1E88E5", "#1976D2"};
        StringBuilder sb = new StringBuilder("<html><span style='font-size:24px; font-weight:bold;'>");

        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') {
                sb.append("&nbsp;");
            } else {
                String color = colors[i % colors.length];
                sb.append("<span style='color:").append(color).append(";'>")
                        .append(name.charAt(i))
                        .append("</span>");
            }
        }

        sb.append("</span></html>");
        return sb.toString();
    }

    public void loadVolunteerProfile(String username) {

        try {

            VolunteerDAO dao = new VolunteerDAO();
            Volunteer v = dao.getVolunteerByUsername(username);

            if (v == null) {
                JOptionPane.showMessageDialog(this, "Volunteer not found!");
                return;
            }
            loadSkillBadges(v, SkillPanel);
            // ================= PERSONAL INFO =================
            //Name.setText("Name: " + v.getFullName());
            // Name.setText( v.getFullName());
            Name.setText(makeGradientName(v.getFullName()));
//        String fullName = v.getFullName();
//String initials = "";
//for (String part : fullName.split(" ")) {
//    if (!part.isEmpty()) {
//        initials += part.charAt(0);
//    }
//}
//
//Name.setText("<html>"
//    + "<table><tr>"
//    + "<td><div style='background:#1565C0; color:white; "
//    + "font-size:20px; font-weight:bold; padding:10px 15px; "
//    + "border-radius:50%;'>" + initials + "</div></td>"
//    + "<td>&nbsp;&nbsp;</td>"
//    + "<td><span style='color:#1A237E; font-size:22px; font-weight:bold;'>"
//    + fullName
//    + "</span><br/>"
//    + "<span style='color:#757575; font-size:12px;'>Volunteer</span></td>"
//    + "</tr></table>"
//    + "</html>");
            DateOfBirth.setText(v.getBirthDateFormatted());
            Gender.setText(v.getGender());
            NID.setText(v.getNid());
            BloodGroup.setText(v.getBloodGroup());

            // ================= CONTACT INFO =================
            PhoneNumber.setText(v.getPhoneNumber());
            Email.setText(v.getEmail());
            EmergencyNumber.setText(v.getEmergencyContact());

            // ================= ADDRESS =================
            PresentAddress.setText(v.getPresentAddressFormatted());
            ParmanentAddress.setText(v.getPermanentAddressFormatted());

            // ================= EDUCATION =================
            Education.setText(v.getUniversityName());

            // ================= WORK =================
            // Work.setText("<html><b>" + v.getProfession() + "</b></html>");
// ================= WORK =================
            String profession = v.getProfession();
            String workDisplay = (profession != null && profession.equalsIgnoreCase("Student"))
                    ? "Student"
                    : "Working at " + profession;
            Work.setText(workDisplay);
            // ================= TRAININGS =================
            // addBadgesFromList(v.getTrainings(), DisasterManagementTrainingPanel,
            // "No Formal Training", "");

            // ================= DISASTERS =================
            addBadgesFromList(v.getDisastersHandled(), TypesOfDisasterHandledPanel,
                    "None", "Disasters Handled:");

            // ================= SKILLS =================
            // loadSkillsFromList(v.getSkills());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading profile: " + e.getMessage());
        }
    }

    private JLabel createSectionTitle(String text) {
        JLabel title = new JLabel("  " + text); // spaces for padding from border
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(new Color(30, 30, 30));
        title.setOpaque(true);
        title.setBackground(Color.WHITE);
        title.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(33, 150, 243)), // left blue accent
                BorderFactory.createEmptyBorder(4, 8, 4, 0)
        ));
        title.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        title.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, title.getPreferredSize().height));
        return title;
    }

    /*private void addBadgesFromList(java.util.List<String> items, JPanel panel,
                                String noDataMsg, String headerText) {
    panel.removeAll();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Color.WHITE);

    // Title row
    if (headerText != null && !headerText.isEmpty()) {
        panel.add(createSectionTitle(headerText));
        panel.add(javax.swing.Box.createVerticalStrut(8)); // gap
    }

    // Badges row
    JPanel badgeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
    badgeRow.setBackground(Color.WHITE);
badgeRow.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT); 
    boolean added = false;

    if (items != null && !items.isEmpty()) {
        for (String item : items) {
            if (item != null && !item.trim().isEmpty() && !item.trim().equalsIgnoreCase("No")) {
                badgeRow.add(createRoundedBadge(item, new Color(255, 87, 34)));
                added = true;
            }
        }
    }

    if (!added) {
        badgeRow.add(createRoundedBadge(noDataMsg, new Color(158, 158, 158)));
    }

    panel.add(badgeRow);
    panel.revalidate();
    panel.repaint();
}*/

 /*private void addBadgesFromList(java.util.List<String> items, JPanel panel,
                                String noDataMsg, String headerText) {
    panel.removeAll();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 12));
    panel.setBackground(Color.WHITE);

    // Disaster emoji map
    java.util.Map<String, String> disasterIcons = new java.util.HashMap<>();
    disasterIcons.put("Flood", "🌊");
    disasterIcons.put("Cyclone", "🌀");
    disasterIcons.put("Landslide", "🏔️");
    disasterIcons.put("River Erosion", "🌧️");
    disasterIcons.put("Fire", "🔥");
    disasterIcons.put("Storm", "⚡");
    disasterIcons.put("Earthquake", "🌍");
    
     // Section title
    if (headerText != null && !headerText.isEmpty()) {
        panel.add(createSectionTitle(headerText));
        panel.add(javax.swing.Box.createVerticalStrut(8));
    }

    // Badges row
    JPanel cardRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
    cardRow.setBackground(Color.WHITE);
    cardRow.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

    boolean added = false;

    
    
    
    if (items != null && !items.isEmpty()) {
        for (String item : items) {
            if (item != null && !item.trim().isEmpty() && !item.trim().equalsIgnoreCase("No")) {
                String icon = disasterIcons.getOrDefault(item.trim(), "⚠️");
                //panel.add(createSkillCard(icon, "Disaster Handled", item.trim()));
                panel.add(createSkillCard(icon, "Disaster Handled", item.trim(), new Color(255, 87, 34))); // deep orange
                added = true;
            }
        }
    }

//    if (!added) {
//        panel.add(createSkillCard("✅", "Disasters Handled", noDataMsg));
//    }
if (!added) {
    panel.add(createSkillCard("✅", "Disasters Handled", noDataMsg, new Color(158, 158, 158)));
}

    panel.revalidate();
    panel.repaint();
}  */
    private void addBadgesFromList(java.util.List<String> items, JPanel panel,
            String noDataMsg, String headerText) {
        panel.removeAll();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // ← BoxLayout not FlowLayout
        panel.setBackground(Color.WHITE);

        // Title on its own row
        if (headerText != null && !headerText.isEmpty()) {
            panel.add(createSectionTitle(headerText));
            panel.add(javax.swing.Box.createVerticalStrut(8));
        }

        // Cards go into a separate row panel
        JPanel cardRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        cardRow.setBackground(Color.WHITE);
        cardRow.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        boolean added = false;

        if (items != null && !items.isEmpty()) {
            for (String item : items) {
                if (item != null && !item.trim().isEmpty() && !item.trim().equalsIgnoreCase("No")) {
                    String icon = disasterIcons.getOrDefault(item.trim(), "⚠️");

                    System.out.println("Item: [" + item.trim() + "] → Icon path: [" + icon + "] → Found: " + (getClass().getResource(icon) != null));

                    cardRow.add(createSkillCard(icon, "", item.trim(), new Color(255, 87, 34))); // ← add to cardRow not panel
                    added = true;
                }
            }
        }

        if (!added) {
            cardRow.add(createSkillCard("✅", "Status", noDataMsg, new Color(158, 158, 158)));
        }

        panel.add(cardRow); // ← cardRow added to panel at the end
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Load skills from a List<String> instead of ResultSet
     */
    private void loadSkillsFromList(java.util.List<String> skills) {
        SkillPanel.removeAll();

        boolean hasAny = false;

        if (skills != null && !skills.isEmpty()) {
            for (String skill : skills) {
                if (skill != null && !skill.trim().isEmpty()) {
                    JLabel badge = createBadge(skill);
                    SkillPanel.add(badge);
                    hasAny = true;
                }
            }
        }

        // If no skills at all
        if (!hasAny) {
            JLabel badge = createBadge("No Skills Available");
            SkillPanel.add(badge);
        }

        SkillPanel.revalidate();
        SkillPanel.repaint();
    }

    private String formatAddress(String district, String division) {

        if (district == null) {
            district = "";
        }
        if (division == null) {
            division = "";
        }

        district = district.trim();
        division = division.trim();

        // If same → show only once
        if (district.equalsIgnoreCase(division)) {

            return district + ", Bangladesh";

        } else {

            return district + ", " + division + ", Bangladesh";
        }
    }

    private boolean addBadges(ResultSet rs, JPanel panel,
            String[][] items, String noDataMsg, String headerText) {

        panel.removeAll();
        if (headerText != null && !headerText.isEmpty()) {
            JLabel headerBadge = createBadge(headerText);
            panel.add(headerBadge);
        }
        boolean added = false;

        try {

            for (String[] item : items) {

                String column = item[0];
                String label = item[1];

                String value = rs.getString(column);

                if (value != null
                        && !value.trim().isEmpty()
                        && !value.equalsIgnoreCase("No")) {

                    JLabel badge = createBadge(label);

                    panel.add(badge); // FlowLayout handles position
                    added = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If nothing found → show negative badge
        if (!added) {

            JLabel badge = createBadge(noDataMsg);
            panel.add(badge);
        }

        panel.revalidate();
        panel.repaint();

        return added;
    }

    private void loadSkills(ResultSet rs) {

        SkillPanel.removeAll();
        boolean hasAny = false;

        try {

            /* ===============================
           1) YES / NO SKILLS
        =============================== */
            String[][] yesNoSkills = {
                {"swimming", "Swimming"},
                {"driving", "Driving"},
                {"search_and_rescue", "Search & Rescue"}
            };

            for (String[] skill : yesNoSkills) {

                String column = skill[0];
                String label = skill[1];

                String value = rs.getString(column);

                if ("Yes".equalsIgnoreCase(value)) {

                    JLabel badge = createBadge(label);

                    SkillPanel.add(badge);
                    hasAny = true;
                }
            }


            /* ===============================
           2) MEDICAL / LANGUAGE / TECHNICAL
        =============================== */
            String[][] textSkills = {
                {"medical_training", "Medical"},
                {"language_skills", "Language"},
                {"technical_skills", "Technical"}
            };

            for (String[] skill : textSkills) {

                String column = skill[0];
                String prefix = skill[1];

                String value = rs.getString(column);

                if (value == null
                        || value.trim().isEmpty()
                        || value.equalsIgnoreCase("No")) {

                    continue;
                }

                String[] items = value.split(",");

                for (String s : items) {

                    String name = s.trim();

                    if (name.isEmpty()) {
                        continue;
                    }

                    String badgeText = prefix + ": " + name;

                    JLabel badge = createBadge(badgeText);

                    SkillPanel.add(badge);
                    hasAny = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        /* ===============================
       3) NO SKILLS AT ALL
    =============================== */
        if (!hasAny) {

            JLabel badge = createBadge("No Skills Available");
            SkillPanel.add(badge);
        }

        SkillPanel.revalidate();
        SkillPanel.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        MainPanel = new javax.swing.JPanel();
        MainInfoPanel = new javax.swing.JPanel();
        PersonalDetailPanel = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        DateOfBirth = new javax.swing.JLabel();
        Gender = new javax.swing.JLabel();
        NID = new javax.swing.JLabel();
        BloodGroup = new javax.swing.JLabel();
        TitleDOB = new javax.swing.JLabel();
        TitleGender = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Education = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Work = new javax.swing.JLabel();
        AddressPanel = new javax.swing.JPanel();
        PresentAddress = new javax.swing.JLabel();
        ParmanentAddress = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        SkillPanel = new javax.swing.JPanel();
        SkillTitle = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnPanel = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        TypesOfDisasterHandledPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        Username = new javax.swing.JLabel();
        ContactPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PhoneNumber = new javax.swing.JLabel();
        Email = new javax.swing.JLabel();
        EmergencyNumber = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        ProfilePic = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1200, 700));

        MainPanel.setBackground(new java.awt.Color(255, 255, 255));
        MainPanel.setPreferredSize(new java.awt.Dimension(1288, 748));

        MainInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
        MainInfoPanel.setPreferredSize(new java.awt.Dimension(1100, 700));

        PersonalDetailPanel.setBackground(new java.awt.Color(255, 255, 255));
        PersonalDetailPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        Title.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        Title.setForeground(new java.awt.Color(0, 0, 204));
        Title.setText("Personal Details");

        DateOfBirth.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        DateOfBirth.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        DateOfBirth.setOpaque(true);

        Gender.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        Gender.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        Gender.setOpaque(true);
        Gender.setPreferredSize(new java.awt.Dimension(2, 20));

        NID.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        NID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        NID.setOpaque(true);

        BloodGroup.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        BloodGroup.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        BloodGroup.setOpaque(true);

        TitleDOB.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        TitleDOB.setForeground(new java.awt.Color(0, 0, 204));
        TitleDOB.setText("Date Of Birth");

        TitleGender.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        TitleGender.setForeground(new java.awt.Color(0, 0, 204));
        TitleGender.setText("Gender");

        jLabel4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("NID");

        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 204));
        jLabel7.setText("BloodGroup");

        jLabel5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 204));
        jLabel5.setText("Education");

        Education.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        Education.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        Education.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 204));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Work");

        Work.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        Work.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Work.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        Work.setOpaque(true);
        Work.setPreferredSize(new java.awt.Dimension(300, 145));

        javax.swing.GroupLayout PersonalDetailPanelLayout = new javax.swing.GroupLayout(PersonalDetailPanel);
        PersonalDetailPanel.setLayout(PersonalDetailPanelLayout);
        PersonalDetailPanelLayout.setHorizontalGroup(
            PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonalDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PersonalDetailPanelLayout.createSequentialGroup()
                        .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(TitleGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TitleDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Gender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DateOfBirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PersonalDetailPanelLayout.createSequentialGroup()
                        .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BloodGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(NID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Education, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Work, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE))))
                .addContainerGap())
        );
        PersonalDetailPanelLayout.setVerticalGroup(
            PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonalDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PersonalDetailPanelLayout.createSequentialGroup()
                        .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TitleDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TitleGender, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NID, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BloodGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Education, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonalDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Work, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AddressPanel.setBackground(new java.awt.Color(255, 255, 255));
        AddressPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        PresentAddress.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        PresentAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        PresentAddress.setOpaque(true);

        ParmanentAddress.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        ParmanentAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        ParmanentAddress.setOpaque(true);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("Address Information");

        jLabel13.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 204));
        jLabel13.setText("Present Address");

        jLabel14.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("Permanent Address");

        javax.swing.GroupLayout AddressPanelLayout = new javax.swing.GroupLayout(AddressPanel);
        AddressPanel.setLayout(AddressPanelLayout);
        AddressPanelLayout.setHorizontalGroup(
            AddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addGroup(AddressPanelLayout.createSequentialGroup()
                        .addGroup(AddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddressPanelLayout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(AddressPanelLayout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(25, 25, 25)))
                        .addGroup(AddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PresentAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                            .addComponent(ParmanentAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        AddressPanelLayout.setVerticalGroup(
            AddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresentAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ParmanentAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        SkillPanel.setBackground(new java.awt.Color(255, 255, 255));
        SkillPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        SkillTitle.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        SkillTitle.setForeground(new java.awt.Color(0, 0, 204));
        SkillTitle.setText("Skill");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setOpaque(true);

        javax.swing.GroupLayout SkillPanelLayout = new javax.swing.GroupLayout(SkillPanel);
        SkillPanel.setLayout(SkillPanelLayout);
        SkillPanelLayout.setHorizontalGroup(
            SkillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SkillPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SkillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SkillTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        SkillPanelLayout.setVerticalGroup(
            SkillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SkillPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SkillTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnPanel.setBackground(new java.awt.Color(255, 255, 255));

        btnEdit.setText("Edit");
        btnEdit.addActionListener(this::btnEditActionPerformed);

        javax.swing.GroupLayout btnPanelLayout = new javax.swing.GroupLayout(btnPanel);
        btnPanel.setLayout(btnPanelLayout);
        btnPanelLayout.setHorizontalGroup(
            btnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEdit)
                .addGap(607, 607, 607))
        );
        btnPanelLayout.setVerticalGroup(
            btnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEdit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TypesOfDisasterHandledPanel.setBackground(new java.awt.Color(255, 255, 255));
        TypesOfDisasterHandledPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 204));
        jLabel9.setText("Types Of Disasters Handled");

        javax.swing.GroupLayout TypesOfDisasterHandledPanelLayout = new javax.swing.GroupLayout(TypesOfDisasterHandledPanel);
        TypesOfDisasterHandledPanel.setLayout(TypesOfDisasterHandledPanelLayout);
        TypesOfDisasterHandledPanelLayout.setHorizontalGroup(
            TypesOfDisasterHandledPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TypesOfDisasterHandledPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TypesOfDisasterHandledPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 1134, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        TypesOfDisasterHandledPanelLayout.setVerticalGroup(
            TypesOfDisasterHandledPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TypesOfDisasterHandledPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        Name.setBackground(new java.awt.Color(255, 255, 255));
        Name.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        Name.setForeground(new java.awt.Color(0, 102, 255));
        Name.setOpaque(true);

        Username.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Username.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Username.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        Username.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        ContactPanel.setBackground(new java.awt.Color(255, 255, 255));
        ContactPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Contact Info");
        jLabel1.setOpaque(true);

        PhoneNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        PhoneNumber.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        PhoneNumber.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        PhoneNumber.setOpaque(true);

        Email.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        Email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        Email.setOpaque(true);

        EmergencyNumber.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        EmergencyNumber.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        EmergencyNumber.setOpaque(true);

        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 204));
        jLabel8.setText("Phone Number");

        jLabel11.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 204));
        jLabel11.setText("Email");

        jLabel12.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 204));
        jLabel12.setText("Emergency Phone No.");

        javax.swing.GroupLayout ContactPanelLayout = new javax.swing.GroupLayout(ContactPanel);
        ContactPanel.setLayout(ContactPanelLayout);
        ContactPanelLayout.setHorizontalGroup(
            ContactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ContactPanelLayout.createSequentialGroup()
                        .addGroup(ContactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ContactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EmergencyNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                            .addComponent(PhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        ContactPanelLayout.setVerticalGroup(
            ContactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(PhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmergencyNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ProfilePic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/ChatGPT Image1 Feb 17, 2026, 07_22_39 PM.png"))); // NOI18N
        ProfilePic.setText("jLabel1");

        javax.swing.GroupLayout MainInfoPanelLayout = new javax.swing.GroupLayout(MainInfoPanel);
        MainInfoPanel.setLayout(MainInfoPanelLayout);
        MainInfoPanelLayout.setHorizontalGroup(
            MainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainInfoPanelLayout.createSequentialGroup()
                        .addComponent(ProfilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(MainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Username, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(MainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, MainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(SkillPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TypesOfDisasterHandledPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, MainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(MainInfoPanelLayout.createSequentialGroup()
                                        .addComponent(PersonalDetailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                        .addGroup(MainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(AddressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ContactPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(32, Short.MAX_VALUE))
                                    .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 1163, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(MainInfoPanelLayout.createSequentialGroup()
                        .addComponent(btnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13))))
        );
        MainInfoPanelLayout.setVerticalGroup(
            MainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainInfoPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(MainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainInfoPanelLayout.createSequentialGroup()
                        .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Username, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ProfilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MainInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(MainInfoPanelLayout.createSequentialGroup()
                        .addComponent(ContactPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AddressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PersonalDetailPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(TypesOfDisasterHandledPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SkillPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addComponent(MainInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(MainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddressPanel;
    private javax.swing.JLabel BloodGroup;
    private javax.swing.JPanel ContactPanel;
    private javax.swing.JLabel DateOfBirth;
    private javax.swing.JLabel Education;
    private javax.swing.JLabel Email;
    private javax.swing.JLabel EmergencyNumber;
    private javax.swing.JLabel Gender;
    private javax.swing.JPanel MainInfoPanel;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JLabel NID;
    private javax.swing.JLabel Name;
    private javax.swing.JLabel ParmanentAddress;
    private javax.swing.JPanel PersonalDetailPanel;
    private javax.swing.JLabel PhoneNumber;
    private javax.swing.JLabel PresentAddress;
    private javax.swing.JLabel ProfilePic;
    private javax.swing.JPanel SkillPanel;
    private javax.swing.JLabel SkillTitle;
    private javax.swing.JLabel Title;
    private javax.swing.JLabel TitleDOB;
    private javax.swing.JLabel TitleGender;
    private javax.swing.JPanel TypesOfDisasterHandledPanel;
    private javax.swing.JLabel Username;
    private javax.swing.JLabel Work;
    private javax.swing.JButton btnEdit;
    private javax.swing.JPanel btnPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
