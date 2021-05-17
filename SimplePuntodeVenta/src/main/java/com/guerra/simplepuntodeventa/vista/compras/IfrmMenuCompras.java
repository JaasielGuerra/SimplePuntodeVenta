/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.vista.compras;

import javax.swing.JPanel;

/**
 *
 * @author Jaasiel
 */
public class IfrmMenuCompras extends javax.swing.JInternalFrame {
    
    public static JPanel PANEL_CAMBIANTE;

    /**
     * Creates new form ifrmVentas
     */
    public IfrmMenuCompras() {
        initComponents();
        IfrmMenuCompras.PANEL_CAMBIANTE = panCentral;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panMenu = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnConsultarCompras = new javax.swing.JButton();
        btnNuevaCompra = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        PROCESO = new javax.swing.JLabel();
        panCentral = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("COMPRAS");
        setMinimumSize(new java.awt.Dimension(960, 540));
        setPreferredSize(new java.awt.Dimension(960, 540));

        jPanel1.setBackground(new java.awt.Color(241, 246, 249));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panMenu.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnConsultarCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-shopping-cart-16.png"))); // NOI18N
        btnConsultarCompras.setText("Consultar compras");
        btnConsultarCompras.setFocusPainted(false);
        jPanel2.add(btnConsultarCompras);

        btnNuevaCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-add-shopping-cart-16.png"))); // NOI18N
        btnNuevaCompra.setText("Nueva compra");
        btnNuevaCompra.setFocusPainted(false);
        jPanel2.add(btnNuevaCompra);

        panMenu.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        PROCESO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ezgif.com-gif-maker-28.gif"))); // NOI18N
        jPanel3.add(PROCESO);

        panMenu.add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel1.add(panMenu, java.awt.BorderLayout.PAGE_START);

        panCentral.setLayout(new java.awt.BorderLayout());
        jPanel1.add(panCentral, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel PROCESO;
    public javax.swing.JButton btnConsultarCompras;
    public javax.swing.JButton btnNuevaCompra;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JPanel panCentral;
    private javax.swing.JPanel panMenu;
    // End of variables declaration//GEN-END:variables
}
