package com.guerra.simplepuntodeventa.vista.clientes;

/**
 *
 * @author Jaasiel
 */
public class VistaClientesBean {

    private PanEditarCliente panEditarCliente;
    private PanEstadoCuentaCliente panEstadoCuenta;
    private PanListClientes panListClientes;
    private PanNuevoCliente panNuevoCliente;

    public VistaClientesBean(PanEditarCliente panEditarCliente,
            PanEstadoCuentaCliente panEstadoCuenta, PanListClientes panListClientes,
            PanNuevoCliente panNuevoCliente) {
        this.panEditarCliente = panEditarCliente;
        this.panEstadoCuenta = panEstadoCuenta;
        this.panListClientes = panListClientes;
        this.panNuevoCliente = panNuevoCliente;
    }

    public PanEditarCliente getPanEditarCliente() {
        return panEditarCliente;
    }

    public void setPanEditarCliente(PanEditarCliente panEditarCliente) {
        this.panEditarCliente = panEditarCliente;
    }

    public PanEstadoCuentaCliente getPanEstadoCuenta() {
        return panEstadoCuenta;
    }

    public void setPanEstadoCuenta(PanEstadoCuentaCliente panEstadoCuenta) {
        this.panEstadoCuenta = panEstadoCuenta;
    }

    public PanListClientes getPanListClientes() {
        return panListClientes;
    }

    public void setPanListClientes(PanListClientes panListClientes) {
        this.panListClientes = panListClientes;
    }

    public PanNuevoCliente getPanNuevoCliente() {
        return panNuevoCliente;
    }

    public void setPanNuevoCliente(PanNuevoCliente panNuevoCliente) {
        this.panNuevoCliente = panNuevoCliente;
    }

}
