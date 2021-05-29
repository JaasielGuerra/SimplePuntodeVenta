/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.vista.usuarios;

import javax.swing.JPanel;

/**
 *
 * @author Jaasiel
 */
public class IfrmMenuUsuarios extends javax.swing.JInternalFrame {

    public static JPanel PANEL_CAMBIANTE;

    /**
     * Creates new form ifrmVentas
     */
    public IfrmMenuUsuarios() {
        initComponents();
        IfrmMenuUsuarios.PANEL_CAMBIANTE = panCentral;
        btnPrivilegios.setVisible(false);
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
        btnConsultarUsuarios = new javax.swing.JButton();
        btnNuevoUsuario = new javax.swing.JButton();
        btnPrivilegios = new javax.swing.JButton();
        panCentral = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setMinimumSize(new java.awt.Dimension(960, 540));
        setPreferredSize(new java.awt.Dimension(960, 540));

        jPanel1.setBackground(new java.awt.Color(241, 246, 249));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panMenu.setBackground(new java.awt.Color(241, 246, 249));
        panMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnConsultarUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-staff-skin-type-7-16.png"))); // NOI18N
        btnConsultarUsuarios.setText("Consultar usuarios");
        btnConsultarUsuarios.setFocusPainted(false);
        panMenu.add(btnConsultarUsuarios);

        btnNuevoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-customer-skin-type-7-16.png"))); // NOI18N
        btnNuevoUsuario.setText("Nuevo usuario");
        btnNuevoUsuario.setFocusPainted(false);
        panMenu.add(btnNuevoUsuario);

        btnPrivilegios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-lock-16.png"))); // NOI18N
        btnPrivilegios.setText("Privilegios");
        btnPrivilegios.setFocusPainted(false);
        panMenu.add(btnPrivilegios);

        jPanel1.add(panMenu, java.awt.BorderLayout.PAGE_START);

        panCentral.setLayout(new java.awt.BorderLayout());
        jPanel1.add(panCentral, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnConsultarUsuarios;
    public javax.swing.JButton btnNuevoUsuario;
    public javax.swing.JButton btnPrivilegios;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel panCentral;
    private javax.swing.JPanel panMenu;
    // End of variables declaration//GEN-END:variables
}
