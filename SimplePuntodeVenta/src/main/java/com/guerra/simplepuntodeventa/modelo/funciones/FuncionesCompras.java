/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.modelo.funciones;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Parameter;
import com.guerra.simplepuntodeventa.modelo.dao.CompraDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.DetalleCompraDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Compra;
import com.guerra.simplepuntodeventa.modelo.entidades.DetalleCompra;
import com.guerra.simplepuntodeventa.recursos.utilerias.FechaUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jaasiel
 */
public class FuncionesCompras {

    private static CompraDAOImpl compraDAO;
    private static DetalleCompraDAOImpl detalleCompraDAO;

    private FuncionesCompras() {

    }

    /**
     * Buscar una factura por numero con la clausula LIKE
     *
     * @param noFacturaBuscar
     * @return
     */
    public static List<Compra> buscarCompraNoFactura(String noFacturaBuscar) {
        if (compraDAO == null) {
            compraDAO = DAOManager.getInstancia().getCompraDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("noFactura", "%" + noFacturaBuscar + "%"));

        List<Compra> compras = compraDAO.readByQuery("SELECT c FROM Compra c "
                + "WHERE c.noFactura LIKE :noFactura", p);
        return compras;
    }

    /**
     * Filtrar compras por rango de fecha
     *
     * @param min
     * @param max
     * @return
     */
    public static List<Compra> filtrarComprasRangoFecha(Date min, Date max) {
        if (compraDAO == null) {
            compraDAO = DAOManager.getInstancia().getCompraDAO();
        }

        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("minFecha", FechaUtil.formatearFechaTextoSqlite3(min)));
        p.add(new Parameter("maxFecha", FechaUtil.formatearFechaTextoSqlite3(max)));

        List<Compra> compras = compraDAO.readByQuery("SELECT c FROM Compra c "
                + "WHERE c.fecha BETWEEN :minFecha AND :maxFecha", p);
        return compras;
    }

    /**
     * Consultar los detalles que pertenecen a una compra
     *
     * @param c
     * @return
     */
    public static List<DetalleCompra> obtenerDetalleCompra(Compra c) {
        if (detalleCompraDAO == null) {
            detalleCompraDAO = DAOManager.getInstancia().getDetalleCompraDAO();
        }

        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("idCompra", c));

        List<DetalleCompra> detalles = detalleCompraDAO.readByQuery("SELECT d FROM DetalleCompra d "
                + "WHERE d.idCompra = :idCompra", p);
        return detalles;
    }
}
