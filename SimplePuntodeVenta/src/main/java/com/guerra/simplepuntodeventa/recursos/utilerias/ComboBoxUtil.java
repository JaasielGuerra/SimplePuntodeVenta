package com.guerra.simplepuntodeventa.recursos.utilerias;


import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.Tipo;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author Jaasiel
 */
public class ComboBoxUtil {

    private ComboBoxUtil() {

    }

    /**
     * Devuelve el item seleccionado del JComboBox
     *
     * @param <T>
     * @param cmb
     * @param indiceNoSeleccion El indice que se excluye de las selecciones
     * válidas
     * @param tipo
     * @return El objeto que esta seleccionado, o null en caso de estar
     * seleccionado el indice indicado en <code>indiceNoSeleccion</code>
     */
    public static <T> T getSelectedItem(JComboBox cmb, int indiceNoSeleccion,
            Class<T> tipo) {
        T item = cmb.getSelectedIndex() == indiceNoSeleccion ? (T) null : (T) cmb.getSelectedItem();
        return item;
    }

    /**
     * Llenar un combobox con los estados REALIZADO, CANCELADO
     *
     * @param combo
     */
    public static void llenarComboEstadoCompraVenta(JComboBox combo) {
        combo.removeAllItems();
        combo.addItem(new Estado("REALIZADO", 1));
        combo.addItem(new Estado("CANCELADO", 0));
        combo.setSelectedIndex(0);

    }

    /**
     * Llenar un combobox con los tipos CONTADO, CREDITO
     *
     * @param combo
     */
    public static void llenarComboTipoCompraVenta(JComboBox combo) {
        combo.removeAllItems();
        combo.addItem(new Tipo("CONTADO", 1));
        combo.addItem(new Tipo("CRÉDITO", 2));
        combo.setSelectedIndex(0);

    }

    /**
     * Llenar un combobox con los estados ACTIVADO, DESACTIVADO y un primer
     * elemento personalizado
     *
     * @param combo
     * @param primerElemento
     */
    public static void llenarComboEstado(JComboBox combo, Object primerElemento) {
        combo.removeAllItems();
        combo.addItem(primerElemento);
        combo.addItem(new Estado("ACTIVADO", 1));
        combo.addItem(new Estado("DESACTIVADO", 0));
        combo.setSelectedIndex(0);

    }

    /**
     * Llenar un combobox con los estados ACTIVADO, DESACTIVADO
     *
     * @param combo
     */
    public static void llenarComboEstado(JComboBox combo) {
        combo.removeAllItems();
        combo.addItem(new Estado("ACTIVADO", 1));
        combo.addItem(new Estado("DESACTIVADO", 0));
        combo.setSelectedIndex(0);

    }

    /**
     * Llenar un combo con una lista de datos
     *
     * @param combo
     * @param datos
     */
    public static void llenarComboDatos(JComboBox combo, List datos) {
        combo.removeAllItems();
        combo.addItem("- SELECCIONAR -");
        datos.forEach((d) -> {
            combo.addItem(d);
        });
    }

    /**
     * Llenar un combo con una lista de datos y un primer elemento personalizado
     *
     * @param combo
     * @param datos
     * @param primerElemento
     */
    public static void llenarComboDatos(JComboBox combo, List datos, Object primerElemento) {

        combo.removeAllItems();
        combo.addItem(primerElemento);
        datos.forEach((d) -> {
            combo.addItem(d);
        });
    }
}
