package com.guerra.simplepuntodeventa.controlador.proveedores;

import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import static com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil.cambiarPanel;
import com.guerra.simplepuntodeventa.vista.clientes.IfrmMenuClientes;
import com.guerra.simplepuntodeventa.vista.proveedores.IfrmMenuProveedores;
import com.guerra.simplepuntodeventa.vista.proveedores.PanEditarProveedor;
import com.guerra.simplepuntodeventa.vista.proveedores.PanEstadoCuentaProveedor;
import com.guerra.simplepuntodeventa.vista.proveedores.PanListProveedores;
import com.guerra.simplepuntodeventa.vista.proveedores.PanNuevoProveedor;

/**
 *
 * @author jaasiel
 */
public class MenuProveedores {

    ////////////vistas////////////////
    private final IfrmMenuProveedores ifrmMenuProveedores;
    private final PanListProveedores panListProveedores = new PanListProveedores();
    private final PanNuevoProveedor panNuevoProveedor = new PanNuevoProveedor();
    private final PanEditarProveedor panEditarProveedor = new PanEditarProveedor();
    private final PanEstadoCuentaProveedor panEstadoCuenta = new PanEstadoCuentaProveedor();

    //////////////controladores//////////////
    private final ControladorProveedores controladorProveedores
            = new ControladorProveedores(panEditarProveedor, panEstadoCuenta,
                    panListProveedores, panNuevoProveedor);

    public MenuProveedores(IfrmMenuProveedores ifrmMenuProveedores) {
        this.ifrmMenuProveedores = ifrmMenuProveedores;
        initOyentes();
    }

    private void initOyentes() {
        ifrmMenuProveedores.btnConsultar.addActionListener((e) -> {
            consultarProveedores();
        });
        ifrmMenuProveedores.btnNuevoProveedor.addActionListener((e) -> {
            nuevoProveedor();
        });
    }

    /////////////////eventos///////
    private void consultarProveedores() {
        controladorProveedores.consultarProveedores();
        PanelUtil.cambiarPanel(IfrmMenuProveedores.PANEL_CAMBIANTE, panListProveedores);
    }

    private void nuevoProveedor() {
        PanelUtil.cambiarPanel(IfrmMenuProveedores.PANEL_CAMBIANTE, panNuevoProveedor);
    }

}
