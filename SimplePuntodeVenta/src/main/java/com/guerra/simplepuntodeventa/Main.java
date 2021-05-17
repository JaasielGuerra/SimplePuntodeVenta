/*
 * Proyecto control de inventario y servicios para car audio
 */
package com.guerra.simplepuntodeventa;

import com.guerra.simplepuntodeventa.vista.FrmPrincipal;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author jaasiel
 */
public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            ToolTipManager.sharedInstance().setInitialDelay(100);
            SwingUtilities.invokeLater(() -> {
                FrmPrincipal frmPrincipal = new FrmPrincipal();
                frmPrincipal.setLocationRelativeTo(null);
                frmPrincipal.setVisible(true);
            });
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException
                | InstantiationException | IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, "Imposible iniciar tema visual, error:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

}
