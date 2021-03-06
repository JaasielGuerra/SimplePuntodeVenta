/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.vista.ventas;

import com.guerra.simplepuntodeventa.modelo.entidades.DetalleVenta;
import com.guerra.simplepuntodeventa.modelo.entidades.Venta;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import java.util.List;

/**
 *
 * @author Jaasiel
 */
public class PanDetalleVenta extends javax.swing.JPanel {

    //para validar si la venta fue guardada como pendiente
    private boolean ventaGuardadaBD = false;

    // servira para cargar la venta que se guardo como pendiente en la BD
    private Venta venta;
    private List<DetalleVenta> detalleVenta;

    /**
     * Creates new form PanDetalleVenta
     */
    public PanDetalleVenta() {
        initComponents();
        TablaUtil.ocultarCol(tblDetalleVenta, 0);
        TablaUtil.anchoMinimoColumna(tblDetalleVenta, 2, 350);
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
        tblDetalleVenta = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        tblDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "entity", "CÓDIGO", "DESCRIPCIÓN", "P. UNITARIO", "CANTIDAD", "GANANCIA", "SUBTOTAL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
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
        tblDetalleVenta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDetalleVenta.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblDetalleVenta);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblDetalleVenta;
    // End of variables declaration//GEN-END:variables

    public boolean isVentaGuardadaBD() {
        return ventaGuardadaBD;
    }

    public void setVentaGuardadaBD(boolean ventaGuardadaBD) {
        this.ventaGuardadaBD = ventaGuardadaBD;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<DetalleVenta> getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(List<DetalleVenta> detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

}
