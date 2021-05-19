package com.guerra.simplepuntodeventa.modelo.funciones;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Parameter;
import com.guerra.simplepuntodeventa.modelo.dao.DetalleVentaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.VentaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.DetalleVenta;
import com.guerra.simplepuntodeventa.modelo.entidades.Venta;
import com.guerra.simplepuntodeventa.recursos.utilerias.FechaUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jaasiel
 */
public class FuncionesVentas {

    private static VentaDAOImpl ventaDAO;
    private static DetalleVentaDAOImpl detalleVentaDAOImpl;

    private FuncionesVentas() {

    }

    /**
     * obtener ventas por tipo contado o credito
     *
     * @param tipo
     * @return
     */
    public static List<Venta> obtenerVentasTipo(int tipo) {
        if (ventaDAO == null) {
            ventaDAO = DAOManager.getInstancia().getVentaDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("tipo", tipo));
        List<Venta> ventas = ventaDAO.readByQuery("SELECT v FROM Venta v "
                + "WHERE v.tipoVenta = :tipo", p);
        return ventas;
    }

    /**
     * Obtener las ventas por estado
     *
     * @param estado
     * @return
     */
    public static List<Venta> obtenerVentasEstado(int estado) {
        if (ventaDAO == null) {
            ventaDAO = DAOManager.getInstancia().getVentaDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("estado", estado));
        List<Venta> ventas = ventaDAO.readByQuery("SELECT v FROM Venta v "
                + "WHERE v.estado = :estado", p);
        return ventas;
    }

    /**
     * Obtener los detalles de una venta
     *
     * @param v
     * @return
     */
    public static List<DetalleVenta> obtenerDetalleVenta(Venta v) {
        if (detalleVentaDAOImpl == null) {
            detalleVentaDAOImpl = DAOManager.getInstancia().getDetalleVentaDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("idVenta", v));
        List<DetalleVenta> detalle = detalleVentaDAOImpl.readByQuery("SELECT d FROM DetalleVenta d "
                + "WHERE d.idVenta = :idVenta", p);
        return detalle;
    }

    /**
     * Obtener todas las ventas de la fecha indicada con estado en 1 o 0
     *
     * @param fecha
     * @return
     */
    public static List<Venta> obtenerVentasFecha(Date fecha) {
        if (ventaDAO == null) {
            ventaDAO = DAOManager.getInstancia().getVentaDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("fecha", FechaUtil.formatearFechaTextoSqlite3(fecha)));
        List<Venta> ventas = ventaDAO.readByQuery("SELECT v FROM Venta v "
                + "WHERE v.fecha = :fecha AND v.estado IN(1,0)", p);
        return ventas;
    }

    /**
     * Obtener ventas con estado 1/0 y rango de fecha
     *
     * @param fechaMin
     * @param fechaMax
     * @return
     */
    public static List<Venta> obtenerVentasRangoFecha(Date fechaMin, Date fechaMax) {
        if (ventaDAO == null) {
            ventaDAO = DAOManager.getInstancia().getVentaDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("fechaMin", FechaUtil.formatearFechaTextoSqlite3(fechaMin)));
        p.add(new Parameter("fechaMax", FechaUtil.formatearFechaTextoSqlite3(fechaMax)));
        List<Venta> ventas = ventaDAO.readByQuery("SELECT v FROM Venta v "
                + "WHERE v.fecha BETWEEN :fechaMin AND :fechaMax AND v.estado IN(1,0)", p);
        return ventas;
    }

}
