/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dg.spadmindashboard;

/**
 *
 * @author samih
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public abstract class SearchFrameBase extends javax.swing.JInternalFrame {

    /**
     * Creates new form SearchAdminFrame
     */
    public SearchFrameBase() {
        initComponents();

        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClear.setBackground(Color.decode("#495057")); // Darker red on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClear.setBackground(Color.decode("#6C757D"));
            }
        });

        btnView.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnView.setBackground(Color.decode("#0056B3"));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnView.setBackground(Color.decode("#007BFF"));
            }
        });

        // scrollbar invisible
        scrollResults.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollResults.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // clean look
        scrollResults.setBorder(null);

        setupTableStyle();
        panelTable.setVisible(false);
        // Step 1: Watermark text
        SwingUtilities.invokeLater(() -> {
            setupSearchWatermark();
            // Force repaint to ensure it renders
            txtSearch.repaint();
        });
        // 3️⃣ Optional: prevent JInternalFrame from stealing focus at startup
        // This ensures watermark shows initially without beep

    }

    protected abstract void performSearch(String query);
    // To be implemented by child classes

    protected abstract void openSelectedProfile();

    // Optional: common helper method to show table
    protected void showResults() {
        panelTable.setVisible(true);
    }

    public JTable getTblResults() {
        return tblResults;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }
    // Getter for View button

    public JButton getBtnView() {
        return btnView;
    }

// Getter for Clear button
    public JButton getBtnClear() {
        return btnClear;
    }

    public JPanel getPanelTable() {
        return panelTable;
    }

    protected void clearSearch() {
        txtSearch.setText("Search by name or username...");
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setFont(txtSearch.getFont().deriveFont(Font.ITALIC));

        DefaultTableModel model = (DefaultTableModel) tblResults.getModel();
        model.setRowCount(0);         // clear table
        panelTable.setVisible(false); // hide table panel
        tblResults.clearSelection();  // unselect any row
        getBtnView().setVisible(false); // hide View button
    }

    protected void setupSearchWatermark() {
        String watermarkText = "Search by name or username...";

        // Set initial watermark
        txtSearch.setText(watermarkText);
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setFont(txtSearch.getFont().deriveFont(Font.ITALIC));
        txtSearch.setCaretPosition(0);

        txtSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (txtSearch.getText().equals(watermarkText)) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                    txtSearch.setFont(txtSearch.getFont().deriveFont(Font.PLAIN));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Remove watermark on focus gained
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtSearch.getText().equals(watermarkText)) {
                    //txtSearch.setText("");
                    //txtSearch.setForeground(Color.BLACK);
                    //txtSearch.setFont(txtSearch.getFont().deriveFont(Font.PLAIN));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText(watermarkText);
                    txtSearch.setForeground(Color.GRAY);
                    txtSearch.setFont(txtSearch.getFont().deriveFont(Font.ITALIC));
                }
            }
        });
    }

    private void setupTableStyle() {

        tblResults.setBackground(Color.WHITE);
        tblResults.setOpaque(true);
        tblResults.setShowGrid(false);
        tblResults.setFillsViewportHeight(true);
        scrollResults.getViewport().setBackground(Color.WHITE);

        // Header style
        JTableHeader header = tblResults.getTableHeader();
        header.setBackground(new Color(10, 28, 54));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        tblResults.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setBackground(new Color(10, 28, 54));
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setOpaque(true);

                return label;
            }
        });

        // Row height
        tblResults.setRowHeight(30);

        // Zebra row renderer
        DefaultTableCellRenderer zebraRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                JLabel c = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                c.setOpaque(true);
                c.setHorizontalAlignment(JLabel.CENTER);

                if (isSelected) {
                    c.setBackground(new Color(8, 114, 138));
                    c.setForeground(Color.WHITE);
                } else {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(245, 249, 255));
                    }
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        /*for (int i = 0; i < tblResults.getColumnCount(); i++) {
        tblResults.getColumnModel().getColumn(i).setCellRenderer(zebraRenderer);
    }*/
        tblResults.setDefaultRenderer(Object.class, zebraRenderer);

        tblResults.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelTable = new javax.swing.JPanel();
        scrollResults = new javax.swing.JScrollPane();
        tblResults = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnView = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        searchPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel1.setText("Search");

        txtSearch.addActionListener(this::txtSearchActionPerformed);

        btnClear.setBackground(new java.awt.Color(108, 117, 125));
        btnClear.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("CLEAR");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo/users-35.png"))); // NOI18N
        jLabel2.setText("USERS");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Serach and view user profile");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 883, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnClear)
                .addContainerGap(216, Short.MAX_VALUE))
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );

        panelTable.setBackground(new java.awt.Color(255, 255, 255));
        panelTable.setPreferredSize(new java.awt.Dimension(452, 700));

        scrollResults.setBackground(new java.awt.Color(255, 255, 255));
        scrollResults.setPreferredSize(new java.awt.Dimension(452, 700));
        scrollResults.setViewportView(tblResults);

        tblResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        scrollResults.setViewportView(tblResults);

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTableLayout.createSequentialGroup()
                .addComponent(scrollResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(0, 102, 102)));

        btnView.setBackground(new java.awt.Color(0, 123, 255));
        btnView.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        btnView.setForeground(new java.awt.Color(255, 255, 255));
        btnView.setText("View Profile");
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTable, javax.swing.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
        //performSearch(txtSearch.getText()); // Call subclass search
        //showResults(); // Make table visible
    }//GEN-LAST:event_txtSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelTable;
    private javax.swing.JScrollPane scrollResults;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTable tblResults;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
