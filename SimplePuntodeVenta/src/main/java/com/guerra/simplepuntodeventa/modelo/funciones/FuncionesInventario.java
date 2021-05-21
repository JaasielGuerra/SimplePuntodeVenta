package com.guerra.simplepuntodeventa.modelo.funciones;

import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Parameter;
import com.guerra.simplepuntodeventa.modelo.dao.ArticuloDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.KardexDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Articulo;
import com.guerra.simplepuntodeventa.modelo.entidades.Kardex;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.utilerias.FechaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.NumeroUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Jaasiel
 */
public class FuncionesInventario {

    private static ArticuloDAOImpl articuloDAO;
    private static KardexDAOImpl kardexDAO;

    public static final int MOVIMIENTO_ENTRADA = 1;
    public static final int MOVIMIENTO_SALIDA = 2;
    public static final int MOVIMIENTO_AJUSTE = 3;
    public static final int MOVIMIENTO_DEVOLUCION = 4;

    private FuncionesInventario() {

    }

    /**
     * Consultar el kardex en un rango de fecha
     *
     * @param min
     * @param max
     * @return
     */
    public static List<Kardex> filtrarKardexRangoFecha(Date min, Date max) {
        if (kardexDAO == null) {
            kardexDAO = DAOManager.getInstancia().getKardexDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("min", FechaUtil.formatearFechaTextoSqlite3(min)));
        p.add(new Parameter("max", FechaUtil.formatearFechaTextoSqlite3(max)));
        return kardexDAO.readByQuery(
                "SELECT k FROM Kardex k "
                + "WHERE k.fecha BETWEEN :min AND :max", p
        );
    }

    /**
     * Buscar un articulo en la tabla del kardex
     *
     * @param busqueda
     * @return
     */
    public static List<Kardex> buscarArticuloKarde(String busqueda) {
        if (kardexDAO == null) {
            kardexDAO = DAOManager.getInstancia().getKardexDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("busqueda", "%" + busqueda + "%"));
        return kardexDAO.readByQuery(
                "SELECT k FROM Kardex k "
                + "WHERE k.idArticulo.nombre LIKE :busqueda", p
        );
    }

    /**
     * Devuelve la suma de la existencia de todo el inventario
     *
     * @return long de la suma
     */
    public static long getSumaInventarioTotal() {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }

        EntityManager em = articuloDAO.getEntityManager();
        Integer suma = em.createQuery("SELECT FUNCTION('inventario_total') FROM Articulo a", Integer.class)
                .getSingleResult();

        return suma;
    }

    /**
     * Devuelve la suma del costo total del inventario
     *
     * @return Double de la suma
     */
    public static double getSumaCostoTotalInventario() {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }

        EntityManager em = articuloDAO.getEntityManager();
        Double suma = em.createQuery("SELECT FUNCTION('costo_inventario') FROM Articulo a", Double.class)
                .getSingleResult();
        return NumeroUtil.redondear(suma, 2);
    }

    /**
     * Devuelve la suma total de la ganancia de todo el inventario
     *
     * @return Double de la suma
     */
    public static double getSumaGananciaTotalInventario() {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }

        EntityManager em = articuloDAO.getEntityManager();
        Double suma = em.createQuery("SELECT FUNCTION('ganancia_inventario') FROM Articulo a", Double.class)
                .getSingleResult();
        return NumeroUtil.redondear(suma, 2);
    }

    /**
     * Buscar un articulo por su codigo y con estado 1 (activo)
     *
     * @param codigo
     * @return devuelve el articulo o null si no se encotro nada
     */
    public static Articulo getArticuloPorCodigo(String codigo) {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }
        Articulo a = null;
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("codigo", codigo.toUpperCase()));
        List<Articulo> resultado = articuloDAO.readByQuery(
                "SELECT a "
                + "FROM Articulo a "
                + "WHERE a.estado = 1 AND"
                + " (a.cod1 = :codigo "
                + "OR a.cod2 = :codigo "
                + "OR a.cod3 = :codigo)", p
        );
        for (Articulo art : resultado) {
            a = art;
        }
        return a;
    }

    /**
     * Registrar un movimiento en el Kardex
     *
     * @param concepto
     * @param existenciaAnterior
     * @param tipo
     * @param cantidad
     * @param existenciaPosterior
     * @param articulo
     * @return devuelve true si se registro con exito
     */
    public static boolean registrarKardex(String concepto, int existenciaAnterior,
            int tipo, int cantidad, int existenciaPosterior, Articulo articulo) {

        Kardex k = new Kardex();

        k.setFecha(FechaUtil.formatearFechaTextoSqlite3(new Date()));
        k.setConcepto(concepto);
        k.setExistenciaAnterior(existenciaAnterior);
        k.setTipo(tipo);
        k.setCantidad(cantidad);
        k.setExistenciaPosterior(existenciaPosterior);
        k.setIdUsuario((Usuario) Session.getInstancia().getAttribute("user"));
        k.setIdArticulo(articulo);
        k.setFechaCommit(new Date());
        k.setHoraCommit(new Date());
        try {
            if (kardexDAO == null) {
                kardexDAO = DAOManager.getInstancia().getKardexDAO();
            }
            kardexDAO.create(k);
        } catch (Exception e) {
            MsjException.msjErrorGuardar(null, "Kardex: " + e.getMessage());
            return false;
        }
        return true;
    }
}
