/*
 * Generacion de los distintos reportes del sistema
 */
package com.guerra.simplepuntodeventa.controlador.reportes;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.CategoriaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.MarcaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Categoria;
import com.guerra.simplepuntodeventa.modelo.entidades.Marca;
import com.guerra.simplepuntodeventa.global.ConfiguracionEmpresa;
import com.guerra.simplepuntodeventa.modelo.entidades.Cliente;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesClientes;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.FechaUtil;
import com.guerra.simplepuntodeventa.vista.reportes.DlgReporteArticulos;
import com.guerra.simplepuntodeventa.vista.reportes.DlgReporteCompras;
import com.guerra.simplepuntodeventa.vista.reportes.DlgReporteEstadoCuenta;
import com.guerra.simplepuntodeventa.vista.reportes.DlgReporteGanancias;
import com.guerra.simplepuntodeventa.vista.reportes.DlgReporteInventario;
import com.guerra.simplepuntodeventa.vista.reportes.DlgReporteVentas;
import com.guerra.simplepuntodeventa.vista.reportes.IfrmMenuReportes;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Jaasiel
 */
public class ControladorReportes {

    private final IfrmMenuReportes menuReportes;
    private final DlgReporteInventario dlgReporteInventario;
    private final DlgReporteArticulos dlgReporteArticulos;
    private final DlgReporteEstadoCuenta dlgReporteEstadoCuenta;
    private final DlgReporteVentas dlgReporteVentas;
    private final DlgReporteCompras dlgReporteCompras;
    private final DlgReporteGanancias dlgReporteGanancias;

    private final CategoriaDAOImpl categoriaDAO = DAOManager.getInstancia().getCategoriaDAO();
    private final MarcaDAOImpl marcaDAO = DAOManager.getInstancia().getMarcaDAO();

    public ControladorReportes(IfrmMenuReportes menu) {
        this.menuReportes = menu;
        this.dlgReporteInventario = new DlgReporteInventario(null, true);
        this.dlgReporteArticulos = new DlgReporteArticulos(null, true);
        this.dlgReporteEstadoCuenta = new DlgReporteEstadoCuenta(null, true);
        this.dlgReporteVentas = new DlgReporteVentas(null, true);
        this.dlgReporteCompras = new DlgReporteCompras(null, true);
        this.dlgReporteGanancias = new DlgReporteGanancias(null, true);
        init();
    }

    private void init() {
        menuReportes.btnReporteInventario.addActionListener((e) -> {
            mostrarDlgReporteInventario();
        });
        dlgReporteInventario.btnGenerarReporte.addActionListener((e) -> {
            generarReporteInventario();
        });
        dlgReporteInventario.btnSalir.addActionListener((e) -> {
            dlgReporteInventario.dispose();
        });

        menuReportes.btnCatalogoArticulos.addActionListener((e) -> {
            mostrarDlgReporteArticulos();
        });
        dlgReporteArticulos.btnGenerarReporte.addActionListener((e) -> {
            generarReporteArtculos();
        });
        dlgReporteArticulos.btnSalir.addActionListener((e) -> {
            dlgReporteArticulos.dispose();
        });

        menuReportes.btnEstadoCuenta.addActionListener((ae) -> {
            mostrarDlgEstadoCuenta();
        });
        dlgReporteEstadoCuenta.btnGenerarReporte.addActionListener((ae) -> {
            generarReporteEstadoCuenta();
        });

        menuReportes.btnVentas.addActionListener((ae) -> {
            mostrarDlgReporteVentas();
        });
        dlgReporteVentas.btnGenerarReporte.addActionListener((ae) -> {
            generarReporteVenta();
        });

        menuReportes.btnCompras.addActionListener((ae) -> {
            mostrarDlgReporteCompras();
        });
        dlgReporteCompras.btnGenerarReporte.addActionListener((ae) -> {
            generarReporteCompras();
        });

        menuReportes.btnCreditosPendientes.addActionListener((ae) -> {
            generarReporteCreditosPendientes();
        });

        menuReportes.btnInventarioBajo.addActionListener((ae) -> {
            generarReporteInventarioBajo();
        });

        menuReportes.btnGanancias.addActionListener((ae) -> {
            mostrarDlgReporteGanancias();
        });
        dlgReporteGanancias.btnGenerarReporte.addActionListener((ae) -> {
            generarReporteGanancias();
        });

        menuReportes.btnMovimientos.addActionListener((ae) -> {
            generarReporteMovimientos();
        });
    }

    ///////////////eventos ////////////////
    private void mostrarDlgReporteInventario() {
        ComboBoxUtil.llenarComboDatos(
                dlgReporteInventario.cmbMarca,
                marcaDAO.readByNameQuery("Marca.findByEstado", "estado", 1),
                "Todas las marcas"
        );
        ComboBoxUtil.llenarComboDatos(
                dlgReporteInventario.cmbCategoria,
                categoriaDAO.readByNameQuery("Categoria.findByEstado", "estado", 1),
                "Todas las categorías"
        );
        dlgReporteInventario.setLocationRelativeTo(menuReportes);
        dlgReporteInventario.setVisible(true);
    }

    private void generarReporteInventario() {

        Marca m = ComboBoxUtil.getSelectedItem(dlgReporteInventario.cmbMarca, 0, Marca.class);
        Categoria c = ComboBoxUtil.getSelectedItem(dlgReporteInventario.cmbCategoria, 0, Categoria.class);
        final String logo = ConfiguracionEmpresa.getInstancia().getLogo();

        HashMap p = new HashMap();
        p.put("LOGO", logo);
        p.put("MARCA", m != null ? m.getIdMarca() : null);
        p.put("CATEGORIA", c != null ? c.getIdCategoria() : null);
        p.put("CATEGORIA_NOMBRE", c != null ? c.getNombre() : "Todas las categorías");
        p.put("MARCA_NOMBRE", m != null ? m.getNombre() : "Todas las marcas");

        GeneradorReporte rp = new GeneradorReporte(dlgReporteInventario, GeneradorReporte.RPT.INVENTARIO, p);
        rp.execute();
    }

    private void mostrarDlgReporteArticulos() {
        ComboBoxUtil.llenarComboDatos(
                dlgReporteArticulos.cmbMarcas,
                marcaDAO.readByNameQuery("Marca.findByEstado", "estado", 1),
                "Todas las marcas"
        );
        ComboBoxUtil.llenarComboDatos(
                dlgReporteArticulos.cmbCategorias,
                categoriaDAO.readByNameQuery("Categoria.findByEstado", "estado", 1),
                "Todas las categorías"
        );
        ComboBoxUtil.llenarComboEstado(dlgReporteArticulos.cmbEstado, "Todos");
        dlgReporteArticulos.setLocationRelativeTo(menuReportes);
        dlgReporteArticulos.setVisible(true);
    }

    private void generarReporteArtculos() {
        Marca m = ComboBoxUtil.getSelectedItem(dlgReporteArticulos.cmbMarcas, 0, Marca.class);
        Categoria c = ComboBoxUtil.getSelectedItem(dlgReporteArticulos.cmbCategorias, 0, Categoria.class);
        Estado e = ComboBoxUtil.getSelectedItem(dlgReporteArticulos.cmbEstado, 0, Estado.class);
        final String logo = ConfiguracionEmpresa.getInstancia().getLogo();

        HashMap p = new HashMap();
        p.put("LOGO", logo);
        p.put("ESTADO", e != null ? e.getValor() : null);
        p.put("ID_MARCA", m != null ? m.getIdMarca() : null);
        p.put("ID_CATEGORIA", c != null ? c.getIdCategoria() : null);
        p.put("NOMBRE_CATEGORIA", c != null ? c.getNombre() : "Todas las categorías");
        p.put("NOMBRE_MARCA", m != null ? m.getNombre() : "Todas las marcas");
        p.put("NOMBRE_ESTADO", e != null ? e.getNombre() : "Todos");

        GeneradorReporte rp = new GeneradorReporte(dlgReporteArticulos, GeneradorReporte.RPT.ARTICULOS, p);
        rp.execute();
    }

    private void mostrarDlgEstadoCuenta() {
        List<Cliente> clientes = FuncionesClientes.consultarClientesEstado(1);
        dlgReporteEstadoCuenta.cmbCliente.removeAllItems();
        clientes.forEach((c) -> {
            dlgReporteEstadoCuenta.cmbCliente.addItem(c);
        });
        dlgReporteEstadoCuenta.setLocationRelativeTo(menuReportes);
        dlgReporteEstadoCuenta.setVisible(true);
    }

    private void generarReporteEstadoCuenta() {
        Cliente c = ComboBoxUtil.getSelectedItem(dlgReporteEstadoCuenta.cmbCliente, -1, Cliente.class);
        final String logo = ConfiguracionEmpresa.getInstancia().getLogo();

        HashMap p = new HashMap();
        p.put("LOGO", logo);
        p.put("ID_CLIENTE", c.getIdCliente());

        GeneradorReporte rp = new GeneradorReporte(dlgReporteEstadoCuenta, GeneradorReporte.RPT.ESTADO_CUENTA, p);
        rp.execute();
    }

    private void mostrarDlgReporteVentas() {
        dlgReporteVentas.txtFMin.setDate(new Date());
        dlgReporteVentas.txtFMax.setDate(new Date());
        dlgReporteVentas.cmbTipo.removeAllItems();
        dlgReporteVentas.cmbTipo.addItem("CONTADO");
        dlgReporteVentas.cmbTipo.addItem("CREDITO");
        dlgReporteVentas.setLocationRelativeTo(menuReportes);
        dlgReporteVentas.setVisible(true);
    }

    private void generarReporteVenta() {
        final String logo = ConfiguracionEmpresa.getInstancia().getLogo();
        HashMap p = new HashMap();
        p.put("LOGO", logo);
        p.put("STR_MIN", dlgReporteVentas.txtFMin.getDate().toString());
        p.put("STR_MAX", dlgReporteVentas.txtFMax.getDate().toString());
        p.put("STR_TIPO", dlgReporteVentas.cmbTipo.getSelectedItem().toString());
        p.put("TIPO", dlgReporteVentas.cmbTipo.getSelectedIndex() + 1);
        p.put("MIN", FechaUtil.formatearFechaTextoSqlite3(dlgReporteVentas.txtFMin.getDate()));
        p.put("MAX", FechaUtil.formatearFechaTextoSqlite3(dlgReporteVentas.txtFMax.getDate()));

        GeneradorReporte rp = new GeneradorReporte(dlgReporteVentas, GeneradorReporte.RPT.VENTAS, p);
        rp.execute();
    }

    private void mostrarDlgReporteCompras() {
        dlgReporteCompras.txtFMin.setDate(new Date());
        dlgReporteCompras.txtFMax.setDate(new Date());
        dlgReporteCompras.cmbTipo.removeAllItems();
        dlgReporteCompras.cmbTipo.addItem("CONTADO");
        dlgReporteCompras.cmbTipo.addItem("CREDITO");
        dlgReporteCompras.setLocationRelativeTo(menuReportes);
        dlgReporteCompras.setVisible(true);
    }

    private void generarReporteCompras() {
        final String logo = ConfiguracionEmpresa.getInstancia().getLogo();
        HashMap p = new HashMap();
        p.put("LOGO", logo);
        p.put("STR_MIN", dlgReporteCompras.txtFMin.getDate().toString());
        p.put("STR_MAX", dlgReporteCompras.txtFMax.getDate().toString());
        p.put("STR_TIPO", dlgReporteCompras.cmbTipo.getSelectedItem().toString());
        p.put("TIPO", dlgReporteCompras.cmbTipo.getSelectedIndex() + 1);
        p.put("MIN", FechaUtil.formatearFechaTextoSqlite3(dlgReporteCompras.txtFMin.getDate()));
        p.put("MAX", FechaUtil.formatearFechaTextoSqlite3(dlgReporteCompras.txtFMax.getDate()));

        GeneradorReporte rp = new GeneradorReporte(dlgReporteCompras, GeneradorReporte.RPT.COMPRAS, p);
        rp.execute();
    }

    private void generarReporteCreditosPendientes() {
        final String logo = ConfiguracionEmpresa.getInstancia().getLogo();
        HashMap p = new HashMap();
        p.put("LOGO", logo);

        GeneradorReporte rp = new GeneradorReporte(menuReportes, GeneradorReporte.RPT.CREDITOS_PENDIENTES, p);
        rp.execute();
    }

    private void generarReporteInventarioBajo() {
        final String logo = ConfiguracionEmpresa.getInstancia().getLogo();
        HashMap p = new HashMap();
        p.put("LOGO", logo);

        GeneradorReporte rp = new GeneradorReporte(menuReportes, GeneradorReporte.RPT.INVENTARIO_BAJO, p);
        rp.execute();
    }

    private void mostrarDlgReporteGanancias() {
        dlgReporteGanancias.txtFMin.setDate(new Date());
        dlgReporteGanancias.txtFMax.setDate(new Date());
        dlgReporteGanancias.cmbTipo.removeAllItems();
        dlgReporteGanancias.cmbTipo.addItem("CONTADO");
        dlgReporteGanancias.cmbTipo.addItem("CREDITO");
        dlgReporteGanancias.setLocationRelativeTo(menuReportes);
        dlgReporteGanancias.setVisible(true);
    }

    private void generarReporteGanancias() {
        final String logo = ConfiguracionEmpresa.getInstancia().getLogo();
        HashMap p = new HashMap();
        p.put("LOGO", logo);
        p.put("STR_MIN", dlgReporteGanancias.txtFMin.getDate().toString());
        p.put("STR_MAX", dlgReporteGanancias.txtFMax.getDate().toString());
        p.put("STR_TIPO", dlgReporteGanancias.cmbTipo.getSelectedItem().toString());
        p.put("TIPO", dlgReporteGanancias.cmbTipo.getSelectedIndex() + 1);
        p.put("MIN", FechaUtil.formatearFechaTextoSqlite3(dlgReporteGanancias.txtFMin.getDate()));
        p.put("MAX", FechaUtil.formatearFechaTextoSqlite3(dlgReporteGanancias.txtFMax.getDate()));

        GeneradorReporte rp = new GeneradorReporte(dlgReporteGanancias, GeneradorReporte.RPT.GANANCIAS, p);
        rp.execute();
    }

    private void generarReporteMovimientos() {
        final String logo = ConfiguracionEmpresa.getInstancia().getLogo();
        HashMap p = new HashMap();
        p.put("LOGO", logo);

        GeneradorReporte rp = new GeneradorReporte(menuReportes, GeneradorReporte.RPT.MOVIMIENTOS, p);
        rp.execute();
    }
}
