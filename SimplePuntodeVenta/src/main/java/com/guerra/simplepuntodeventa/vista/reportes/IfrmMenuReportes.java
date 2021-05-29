/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.vista.reportes;

/**
 *
 * @author Jaasiel
 */
public class IfrmMenuReportes extends javax.swing.JInternalFrame {

    /**
     * Creates new form ifrmVentas
     */
    public IfrmMenuReportes() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panCentral = new javax.swing.JPanel();
        btnReporteInventario = new javax.swing.JButton();
        btnEstadoCuenta = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnCompras = new javax.swing.JButton();
        btnCreditosPendientes = new javax.swing.JButton();
        btnInventarioBajo = new javax.swing.JButton();
        btnCatalogoArticulos = new javax.swing.JButton();
        btnGanancias = new javax.swing.JButton();
        btnMovimientos = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("REPORTES");
        setMinimumSize(new java.awt.Dimension(960, 540));
        setPreferredSize(new java.awt.Dimension(960, 540));

        panCentral.setLayout(new java.awt.GridLayout(3, 3, 30, 30));

        btnReporteInventario.setBackground(new java.awt.Color(245, 127, 23));
        btnReporteInventario.setForeground(new java.awt.Color(255, 255, 255));
        btnReporteInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-warehouse-100.png"))); // NOI18N
        btnReporteInventario.setText("<html><b style=\"font-size: 14px;\">Inventario.</b> <p>Reporte de inventario de artículos activos.</p>");
        btnReporteInventario.setFocusPainted(false);
        panCentral.add(btnReporteInventario);

        btnEstadoCuenta.setBackground(new java.awt.Color(1, 87, 155));
        btnEstadoCuenta.setForeground(new java.awt.Color(255, 255, 255));
        btnEstadoCuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-circled-user-100.png"))); // NOI18N
        btnEstadoCuenta.setText("<html><b style=\"font-size: 14px;\">Estado de cuenta.</b> <p>Reporte de estado de cuenta de un Cliente en específico.</p>");
        panCentral.add(btnEstadoCuenta);

        btnVentas.setBackground(new java.awt.Color(0, 96, 100));
        btnVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-sales-performance-100.png"))); // NOI18N
        btnVentas.setText("<html><b style=\"font-size: 14px;\">Ventas.</b> <p>Reporte de ventas realizadas en un rango de fecha especificado.</p>");
        panCentral.add(btnVentas);

        btnCompras.setBackground(new java.awt.Color(74, 20, 140));
        btnCompras.setForeground(new java.awt.Color(255, 255, 255));
        btnCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-shopping-cart-100.png"))); // NOI18N
        btnCompras.setText("<html><b style=\"font-size: 14px;\">Compras.</b> <p>Reporte de compras realizadas en un rango de fecha indicado.</p>");
        panCentral.add(btnCompras);

        btnCreditosPendientes.setBackground(new java.awt.Color(130, 119, 23));
        btnCreditosPendientes.setForeground(new java.awt.Color(255, 255, 255));
        btnCreditosPendientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-profile-100.png"))); // NOI18N
        btnCreditosPendientes.setText("<html><b style=\"font-size: 14px;\">Créditos pendientes.</b> <p>Reporte de Clientes que tienen créditos pendientes y su respectivo saldo.</p>");
        panCentral.add(btnCreditosPendientes);

        btnInventarioBajo.setBackground(new java.awt.Color(183, 28, 28));
        btnInventarioBajo.setForeground(new java.awt.Color(255, 255, 255));
        btnInventarioBajo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-descending-sorting-100.png"))); // NOI18N
        btnInventarioBajo.setText("<html><b style=\"font-size: 14px;\">Inventario bajo.</b> <p>Reporte de los artículos que están bajos en inventario.</p>");
        panCentral.add(btnInventarioBajo);

        btnCatalogoArticulos.setBackground(new java.awt.Color(26, 35, 126));
        btnCatalogoArticulos.setForeground(new java.awt.Color(255, 255, 255));
        btnCatalogoArticulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-show-property-100.png"))); // NOI18N
        btnCatalogoArticulos.setText("<html><b style=\"font-size: 14px;\">Catálogo de artículos.</b> <p>Reporte de catálogo de artículos registrados en el sistema.</p>");
        panCentral.add(btnCatalogoArticulos);

        btnGanancias.setBackground(new java.awt.Color(27, 94, 32));
        btnGanancias.setForeground(new java.awt.Color(255, 255, 255));
        btnGanancias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-total-sales-100.png"))); // NOI18N
        btnGanancias.setText("<html><b style=\"font-size: 14px;\">Ganancias.</b> <p>Reporte de las ganancias obtenidas de las ventas realizadas.</p>");
        panCentral.add(btnGanancias);

        btnMovimientos.setBackground(new java.awt.Color(136, 14, 79));
        btnMovimientos.setForeground(new java.awt.Color(255, 255, 255));
        btnMovimientos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-pay-date-100.png"))); // NOI18N
        btnMovimientos.setText("<html><b style=\"font-size: 14px;\">Movimientos.</b> <p>Reporte de movimientos del inventario.</p>");
        panCentral.add(btnMovimientos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panCentral, javax.swing.GroupLayout.PREFERRED_SIZE, 786, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panCentral, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCatalogoArticulos;
    public javax.swing.JButton btnCompras;
    public javax.swing.JButton btnCreditosPendientes;
    public javax.swing.JButton btnEstadoCuenta;
    public javax.swing.JButton btnGanancias;
    public javax.swing.JButton btnInventarioBajo;
    public javax.swing.JButton btnMovimientos;
    public javax.swing.JButton btnReporteInventario;
    public javax.swing.JButton btnVentas;
    public javax.swing.JPanel panCentral;
    // End of variables declaration//GEN-END:variables
}
