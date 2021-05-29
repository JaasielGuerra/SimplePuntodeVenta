/*
 * Controlador principal para el JFrame base
 */
package com.guerra.simplepuntodeventa;

import com.guerra.simplepuntodeventa.controlador.articulos.MenuArticulos;
import com.guerra.simplepuntodeventa.controlador.clientes.MenuClientes;
import com.guerra.simplepuntodeventa.controlador.compras.MenuCompras;
import com.guerra.simplepuntodeventa.controlador.configuracionempresa.ControladorConfiguracion;
import com.guerra.simplepuntodeventa.controlador.inventario.MenuInventario;
import com.guerra.simplepuntodeventa.controlador.proveedores.MenuProveedores;
import com.guerra.simplepuntodeventa.controlador.reportes.ControladorReportes;
import com.guerra.simplepuntodeventa.controlador.servicios.ControladorServicios;
import com.guerra.simplepuntodeventa.controlador.usuarios.MenuUsuarios;
import com.guerra.simplepuntodeventa.controlador.ventas.ControladorVenta;
import com.guerra.simplepuntodeventa.global.ConfiguracionEmpresa;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.recursos.utilerias.PantallaUtil;
import com.guerra.simplepuntodeventa.vista.FrmPrincipal;
import com.guerra.simplepuntodeventa.vista.articulos.IfrmMenuArticulos;
import com.guerra.simplepuntodeventa.vista.clientes.IfrmMenuClientes;
import com.guerra.simplepuntodeventa.vista.compras.IfrmMenuCompras;
import com.guerra.simplepuntodeventa.vista.empresa.IfrmMenuEmpresa;
import com.guerra.simplepuntodeventa.vista.inventario.IfrmMenuInventario;
import com.guerra.simplepuntodeventa.vista.proveedores.IfrmMenuProveedores;
import com.guerra.simplepuntodeventa.vista.reportes.IfrmMenuReportes;
import com.guerra.simplepuntodeventa.vista.servicios.IfrmServicios;
import com.guerra.simplepuntodeventa.vista.usuarios.IfrmMenuUsuarios;
import com.guerra.simplepuntodeventa.vista.ventas.IfrmVentas;
import javax.swing.JInternalFrame;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControladorPrincipal {

    private final int MARGEN_X = 150;
    private final int MARGEN_Y = 50;

    //////////////vistas/////////////
    private final FrmPrincipal frmPrincipal = new FrmPrincipal();
    private final IfrmVentas ifrmMenuVentas = new IfrmVentas();
    private final IfrmMenuArticulos ifrmMenuArticulos = new IfrmMenuArticulos();
    private final IfrmMenuInventario ifrmMenuInventario = new IfrmMenuInventario();
    private final IfrmMenuCompras ifrmMenuCompras = new IfrmMenuCompras();
    private final IfrmMenuClientes ifrmMenuClientes = new IfrmMenuClientes();
    private final IfrmMenuReportes ifrmMenuReportes = new IfrmMenuReportes();
    private final IfrmMenuEmpresa ifrmMenuEmpresa = new IfrmMenuEmpresa();
    private final IfrmMenuProveedores ifrmMenuProveedores = new IfrmMenuProveedores();
    private final IfrmServicios ifrmServicios = new IfrmServicios();
    private final IfrmMenuUsuarios ifrmMenuUsuarios = new IfrmMenuUsuarios();

    /////////////controladores/////////////
    private final MenuArticulos menuArticulos = new MenuArticulos(ifrmMenuArticulos);
    private final MenuInventario menuInventario = new MenuInventario(ifrmMenuInventario);
    private final MenuClientes menuClientes = new MenuClientes(ifrmMenuClientes);
    private final ControladorVenta controladorVenta = new ControladorVenta(ifrmMenuVentas);
    private final MenuProveedores menuProveedores = new MenuProveedores(ifrmMenuProveedores);
    private final MenuCompras menuCompras = new MenuCompras(ifrmMenuCompras);
    private final ControladorConfiguracion configuracion = new ControladorConfiguracion(ifrmMenuEmpresa);
    private final ControladorServicios controladorServicios = new ControladorServicios(ifrmServicios);
    private final MenuUsuarios menuUsuarios = new MenuUsuarios(ifrmMenuUsuarios);
    private final ControladorReportes controladorReportes = new ControladorReportes(ifrmMenuReportes);

    public ControladorPrincipal() {
        init();
        initOyentes();
    }

    private void init() {
        
        ifrmMenuVentas.hide();
        ifrmMenuArticulos.hide();
        ifrmMenuInventario.hide();
        ifrmMenuCompras.hide();
        ifrmMenuClientes.hide();
        ifrmMenuReportes.hide();
        ifrmMenuEmpresa.hide();
        ifrmMenuProveedores.hide();
        ifrmServicios.hide();
        ifrmMenuUsuarios.hide();

        agregarIFRM(ifrmMenuVentas);
        agregarIFRM(ifrmMenuArticulos);
        agregarIFRM(ifrmMenuInventario);
        agregarIFRM(ifrmMenuCompras);
        agregarIFRM(ifrmMenuClientes);
        agregarIFRM(ifrmMenuReportes);
        agregarIFRM(ifrmMenuEmpresa);
        agregarIFRM(ifrmMenuProveedores);
        agregarIFRM(ifrmServicios);
        agregarIFRM(ifrmMenuUsuarios);
    }

    private void initOyentes() {//listar eventos

        frmPrincipal.btnVentas.addActionListener((e) -> {
            mostrarVentanaVentas();
        });
        frmPrincipal.btnArticulos.addActionListener((e) -> {
            mostrarVentanaArticulos();
        });
        frmPrincipal.btnInventario.addActionListener((e) -> {
            mostrarVentanaInventario();
        });
        frmPrincipal.btnCompras.addActionListener((e) -> {
            mostrarVentanaCompras();
        });
        frmPrincipal.btnClientes.addActionListener((e) -> {
            mostrarVentanaClientes();
        });
        frmPrincipal.btnReportes.addActionListener((e) -> {
            mostrarVentanaReportes();
        });
        frmPrincipal.btnConfiguracion.addActionListener((e) -> {
            mostrarVentanConfiguracion();
        });
        frmPrincipal.btnProveedores.addActionListener((ae) -> {
            mostrarVentanaProveedores();
        });
        frmPrincipal.btnServicios.addActionListener((ae) -> {
            mostrarVentanaServicios();
        });
        frmPrincipal.btnUsuarios.addActionListener((ae) -> {
            mostrarVentanaUsuarios();
        });
    }

    private void agregarIFRM(JInternalFrame ifrm) {
        frmPrincipal.panPrincipal.add(ifrm);
    }

    ////////metodos publicos/////////
    public void initPrograma() {

        String empresa = ConfiguracionEmpresa.getInstancia().getNombre();
        Usuario user = (Usuario) Session.getInstancia().getAttribute("user");

        frmPrincipal.lblEmpresa.setText(""
                + "<html>"
                + "<span style=\"font-size: 17px;\"\\>"
                + empresa
                + "</span>"
                + "<br/>"
                + "Simple punto de Venta</html>"
                + "");
        frmPrincipal.lblUsuario.setText(user.getNombre());
        frmPrincipal.setVisible(true);
    }

    ////////////eventos////////////////
    private void mostrarVentanaVentas() {
        PantallaUtil.centrarIFRM(ifrmMenuVentas, frmPrincipal.panPrincipal, MARGEN_X, MARGEN_Y);
        ifrmMenuVentas.toFront();
        ifrmMenuVentas.show();
    }

    private void mostrarVentanaArticulos() {
        PantallaUtil.centrarIFRM(ifrmMenuArticulos, frmPrincipal.panPrincipal, MARGEN_X, MARGEN_Y);
        ifrmMenuArticulos.toFront();
        ifrmMenuArticulos.show();
    }

    private void mostrarVentanaInventario() {
        PantallaUtil.centrarIFRM(ifrmMenuInventario, frmPrincipal.panPrincipal, MARGEN_X, MARGEN_Y);
        ifrmMenuInventario.toFront();
        ifrmMenuInventario.show();
    }

    private void mostrarVentanaCompras() {
        PantallaUtil.centrarIFRM(ifrmMenuCompras, frmPrincipal.panPrincipal, MARGEN_X, MARGEN_Y);
        ifrmMenuCompras.toFront();
        ifrmMenuCompras.show();
    }

    private void mostrarVentanaClientes() {
        PantallaUtil.centrarIFRM(ifrmMenuClientes, frmPrincipal.panPrincipal, MARGEN_X, MARGEN_Y);
        ifrmMenuClientes.toFront();
        ifrmMenuClientes.show();
    }

    private void mostrarVentanaReportes() {
        PantallaUtil.centrarIFRM(ifrmMenuReportes, frmPrincipal.panPrincipal, MARGEN_X, MARGEN_Y);
        ifrmMenuReportes.toFront();
        ifrmMenuReportes.show();
    }

    private void mostrarVentanConfiguracion() {
        configuracion.consultarDatos();
        PantallaUtil.centrarIFRM(ifrmMenuEmpresa, frmPrincipal.panPrincipal, MARGEN_X, MARGEN_Y);
        ifrmMenuEmpresa.toFront();
        ifrmMenuEmpresa.show();
    }

    private void mostrarVentanaProveedores() {
        PantallaUtil.centrarIFRM(ifrmMenuProveedores, frmPrincipal.panPrincipal, MARGEN_X, MARGEN_Y);
        ifrmMenuProveedores.toFront();
        ifrmMenuProveedores.show();
    }

    private void mostrarVentanaServicios() {
        controladorServicios.resetFormulario();
        controladorServicios.consultarServicios();
        PantallaUtil.centrarIFRM(ifrmServicios, frmPrincipal.panPrincipal, MARGEN_X, MARGEN_Y);
        ifrmServicios.toFront();
        ifrmServicios.show();
    }

    private void mostrarVentanaUsuarios() {
        PantallaUtil.centrarIFRM(ifrmMenuUsuarios, frmPrincipal.panPrincipal, MARGEN_X, MARGEN_Y);
        ifrmMenuUsuarios.toFront();
        ifrmMenuUsuarios.show();
    }

}
