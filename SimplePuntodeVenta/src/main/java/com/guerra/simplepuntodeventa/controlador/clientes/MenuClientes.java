package com.guerra.simplepuntodeventa.controlador.clientes;

import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.vista.clientes.IfrmMenuClientes;
import com.guerra.simplepuntodeventa.vista.clientes.PanEditarCliente;
import com.guerra.simplepuntodeventa.vista.clientes.PanEstadoCuentaCliente;
import com.guerra.simplepuntodeventa.vista.clientes.PanListClientes;
import com.guerra.simplepuntodeventa.vista.clientes.PanNuevoCliente;
import com.guerra.simplepuntodeventa.vista.clientes.VistaClientesBean;

/**
 *
 * @author Jaasiel
 */
public class MenuClientes {

    ////////////vistas////////
    private final IfrmMenuClientes panMenuClientes;
    private final PanListClientes panListClientes = new PanListClientes();
    private final PanNuevoCliente panNuevoCliente = new PanNuevoCliente();
    private final PanEditarCliente panEditarCliente = new PanEditarCliente();
    private final PanEstadoCuentaCliente panEstadoCuenta = new PanEstadoCuentaCliente();

    ////////////controladores//////////////
    private final ControladorClientes controladorClientes = new ControladorClientes(
            new VistaClientesBean(panEditarCliente, panEstadoCuenta, panListClientes, panNuevoCliente)
    );

    public MenuClientes(IfrmMenuClientes panMenuClientes) {
        this.panMenuClientes = panMenuClientes;
        initOyentes();
    }

    private void initOyentes() {
        panMenuClientes.btnConsultarClientes.addActionListener((e) -> {
            consultarClientes();
        });
        panMenuClientes.btnNuevoCliente.addActionListener((e) -> {
            nuevoCliente();
        });
    }

    /////////////////eventos///////
    private void consultarClientes() {
        controladorClientes.consultarClientes();
        PanelUtil.cambiarPanel(IfrmMenuClientes.PANEL_CAMBIANTE, panListClientes);
    }

    private void nuevoCliente() {
        PanelUtil.cambiarPanel(IfrmMenuClientes.PANEL_CAMBIANTE, panNuevoCliente);
    }

}
