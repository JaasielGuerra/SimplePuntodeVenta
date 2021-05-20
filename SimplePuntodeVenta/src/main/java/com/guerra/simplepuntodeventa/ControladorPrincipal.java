/*
 * Controlador principal para el JFrame base
 */
package com.guerra.simplepuntodeventa;

import com.guerra.simplepuntodeventa.controlador.articulos.MenuArticulos;
import com.guerra.simplepuntodeventa.controlador.inventario.MenuInventario;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.entidades.Cliente;
import com.guerra.simplepuntodeventa.modelo.entidades.Proveedor;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.recursos.utilerias.PantallaUtil;
import com.guerra.simplepuntodeventa.vista.FrmPrincipal;
import com.guerra.simplepuntodeventa.vista.articulos.IfrmMenuArticulos;
import com.guerra.simplepuntodeventa.vista.clientes.IfrmMenuClientes;
import com.guerra.simplepuntodeventa.vista.compras.IfrmMenuCompras;
import com.guerra.simplepuntodeventa.vista.empresa.IfrmMenuEmpresa;
import com.guerra.simplepuntodeventa.vista.inventario.IfrmMenuInventario;
import com.guerra.simplepuntodeventa.vista.reportes.IfrmMenuReportes;
import com.guerra.simplepuntodeventa.vista.ventas.IfrmVentas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private final IfrmVentas ifrmMenuVentas1 = new IfrmVentas();
    private final IfrmMenuEmpresa ifrmMenuEmpresa = new IfrmMenuEmpresa();

    /////////////controladores/////////////
    private final MenuArticulos menuArticulos = new MenuArticulos(ifrmMenuArticulos);
    private final MenuInventario menuInventario = new MenuInventario(ifrmMenuInventario);

    public ControladorPrincipal() {
        init();
        initOyentes();
    }

    private void init() {

        //crear cache en la sesion
        Usuario user = DAOManager.getInstancia().getUsuarioDAO().readOne(1);
        Cliente cliente = DAOManager.getInstancia().getClienteDAO().readOne(1);
        Proveedor prov = DAOManager.getInstancia().getProveedorDAO().readOne(1);
        Session.getInstancia().setAttribute("user", user);
        Session.getInstancia().setAttribute("cliente", cliente);
        Session.getInstancia().setAttribute("proveedor", prov);

        frmPrincipal.setVisible(true);
        ifrmMenuVentas.hide();
        ifrmMenuArticulos.hide();
        ifrmMenuInventario.hide();
        ifrmMenuCompras.hide();
        ifrmMenuClientes.hide();
        ifrmMenuReportes.hide();
        ifrmMenuVentas1.hide();
        ifrmMenuEmpresa.hide();

        agregarIFRM(ifrmMenuVentas);
        agregarIFRM(ifrmMenuArticulos);
        agregarIFRM(ifrmMenuInventario);
        agregarIFRM(ifrmMenuCompras);
        agregarIFRM(ifrmMenuClientes);
        agregarIFRM(ifrmMenuReportes);
        agregarIFRM(ifrmMenuVentas1);
        agregarIFRM(ifrmMenuEmpresa);
    }

    private void initOyentes() {//listar eventos

        frmPrincipal.btnCerrarSistema.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cerrarSistema();
            }
        });
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
    }

    private void agregarIFRM(JInternalFrame ifrm) {
        frmPrincipal.panPrincipal.add(ifrm);
    }

    ////////////eventos////////////////
    private void cerrarSistema() {

    }

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

    }

}
