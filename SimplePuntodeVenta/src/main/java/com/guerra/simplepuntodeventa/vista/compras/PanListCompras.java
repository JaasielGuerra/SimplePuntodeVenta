/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.vista.compras;

import com.guerra.simplepuntodeventa.recursos.componentes.extensiones.PlaceHolderSupport;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;


/**
 *
 * @author Jaasiel
 */
public class PanListCompras extends javax.swing.JPanel {

    /**
     * Creates new form PanListArticulos
     */
    public PanListCompras() {
        initComponents();
        TablaUtil.ocultarCol(tblCompras, 0);
        PlaceHolderSupport.setPlaceHolder("Número de factura...", txtBuscarFactura);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCompras = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        fechaMin = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        fechaMax = new com.toedter.calendar.JDateChooser();
        btnIr = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtBuscarFactura = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnDetalles = new javax.swing.JButton();
        btnCancelarCompra = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("COMPRAS");

        tblCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "entity", "No. Compra", "Fecha", "No. Factura", "Serie", "Total", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCompras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblCompras);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel4.setText("Estado");
        jPanel1.add(jLabel4);

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxEstado);

        jLabel9.setText("Desde");
        jPanel1.add(jLabel9);

        fechaMin.setPreferredSize(new java.awt.Dimension(135, 29));
        jPanel1.add(fechaMin);

        jLabel10.setText("Hasta");
        jPanel1.add(jLabel10);

        fechaMax.setPreferredSize(new java.awt.Dimension(135, 29));
        jPanel1.add(fechaMax);

        btnIr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-search-16.png"))); // NOI18N
        btnIr.setText("Ir");
        btnIr.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnIr.setFocusPainted(false);
        jPanel1.add(btnIr);

        jLabel3.setText("Buscar");
        jPanel1.add(jLabel3);

        txtBuscarFactura.setBackground(new java.awt.Color(255, 255, 0));
        txtBuscarFactura.setPreferredSize(new java.awt.Dimension(150, 24));
        jPanel1.add(txtBuscarFactura);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnDetalles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-show-property-16.png"))); // NOI18N
        btnDetalles.setText("Ver detalles");
        btnDetalles.setFocusPainted(false);
        jPanel2.add(btnDetalles);

        btnCancelarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-cancel-16.png"))); // NOI18N
        btnCancelarCompra.setText("Cancelar compra");
        btnCancelarCompra.setFocusPainted(false);
        jPanel2.add(btnCancelarCompra);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancelarCompra;
    public javax.swing.JButton btnDetalles;
    public javax.swing.JButton btnIr;
    public javax.swing.JComboBox<Object> cbxEstado;
    public com.toedter.calendar.JDateChooser fechaMax;
    public com.toedter.calendar.JDateChooser fechaMin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblCompras;
    public javax.swing.JTextField txtBuscarFactura;
    // End of variables declaration//GEN-END:variables
}
