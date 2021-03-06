/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.recursos.componentes.animacion;

import java.awt.Component;

/**
 *
 * @author Jaasiel
 */
public class DlgProcesoCierreSistema extends javax.swing.JDialog {

    private static DlgProcesoCierreSistema dlgProceso;

    /**
     * Creates new form DlgProceso
     */
    private DlgProcesoCierreSistema(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * muestra un dialogo indicando un proceso en marcha y se le da el
     * componente padre como argumento
     *
     * @param padre
     */
    public static void mostrarDlgProceso(Component padre) {
        if (dlgProceso == null) {
            dlgProceso = new DlgProcesoCierreSistema(null, true);
        }

        dlgProceso.setLocationRelativeTo(padre);
        dlgProceso.setVisible(true);
    }

    /**
     * muestra un dialogo indicando un proceso en marcha y se le da el
     * componente padre y el texto que aparece en dialogo y el titulo
     *
     * @param padre
     * @param titulo
     * @param txtInicial
     */
    public static void mostrarDlgProceso(Component padre, String titulo, String txtInicial) {
        if (dlgProceso == null) {
            dlgProceso = new DlgProcesoCierreSistema(null, true);
        }

        dlgProceso.lblProceso.setText(txtInicial);
        dlgProceso.setTitle(titulo);
        dlgProceso.setLocationRelativeTo(padre);
        dlgProceso.setVisible(true);
    }

    /**
     * Publicar un proceso
     *
     * @param txt
     * @param porcentaje
     */
    public static void publicar(String txt, int porcentaje) {
        if (dlgProceso == null) {
            dlgProceso = new DlgProcesoCierreSistema(null, true);
        }

        dlgProceso.lblProceso.setText(txt);
        dlgProceso.jProgressBar1.setValue(porcentaje);
    }

    /**
     * ocultar el dialogo del proceso
     */
    public static void ocultarDlgProceso() {
        if (dlgProceso == null) {
            dlgProceso = new DlgProcesoCierreSistema(null, true);
        }
        dlgProceso.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblProceso = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Procesando...");
        setMinimumSize(new java.awt.Dimension(400, 80));
        setPreferredSize(new java.awt.Dimension(400, 80));
        setResizable(false);

        lblProceso.setText("Publicar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProceso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProceso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblProceso;
    // End of variables declaration//GEN-END:variables
}
