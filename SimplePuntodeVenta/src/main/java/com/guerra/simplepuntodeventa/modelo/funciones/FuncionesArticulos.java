package com.guerra.simplepuntodeventa.modelo.funciones;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Parameter;
import com.guerra.simplepuntodeventa.modelo.dao.ArticuloDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Articulo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jaasiel
 */
public class FuncionesArticulos {

    private static ArticuloDAOImpl articuloDAO;

    private FuncionesArticulos() {

    }

    /**
     * Valida si alguno de los tres codigos ya existen
     *
     * @param cod1
     * @param cod2
     * @param cod3
     * @return true en caso de que algun codigo exista
     */
    public static boolean codigoExistente(String cod1, String cod2, String cod3) {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("cod1", cod1));
        p.add(new Parameter("cod2", cod2));
        p.add(new Parameter("cod3", cod3));
        return articuloDAO.readByQuery(
                "SELECT a "
                + "FROM Articulo a "
                + "WHERE a.cod1 IN(:cod1, :cod2, :cod3) "
                + "OR a.cod2 IN(:cod1, :cod2, :cod3) "
                + "OR a.cod3 IN(:cod1, :cod2, :cod3)", p
        ).size() > 0;
    }

    /**
     * Valida si algun codigo ya existe, ignorando los codigos de la entidad
     * indicada en su parametro
     *
     * @param cod1
     * @param cod2
     * @param cod3
     * @param exclusion la entidad que se excluye
     * @return true en caso de que algun codigo exista
     */
    public static boolean codigoExistente(String cod1, String cod2, String cod3,
            Articulo exclusion) {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("cod1", cod1));
        p.add(new Parameter("cod2", cod2));
        p.add(new Parameter("cod3", cod3));
        p.add(new Parameter("idArticulo", exclusion.getIdArticulo()));
        return articuloDAO.readByQuery(
                "SELECT a "
                + "FROM Articulo a "
                + "WHERE (a.cod1 IN(:cod1, :cod2, :cod3) "
                + "OR a.cod2 IN(:cod1, :cod2, :cod3) "
                + "OR a.cod3 IN(:cod1, :cod2, :cod3))"
                + "AND NOT a.idArticulo = :idArticulo", p
        ).size() > 0;
    }

    /**
     * Buscar coincidencias con la clausula LIKE por codigo y estado
     *
     * @param estado
     * @param busqueda
     * @return listado de resultado de la consulta
     */
    public static List<Articulo> buscarArticulosPorCodigoEstado(int estado,
            String busqueda) {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("estado", estado));
        p.add(new Parameter("busqueda", "%".concat(busqueda).concat("%")));
        return articuloDAO.readByQuery("SELECT a "
                + "FROM Articulo a "
                + "WHERE a.estado = :estado "
                + "AND a.cod1 LIKE :busqueda", p);
    }

    /**
     * Buscar coincidencias con la clausula LIKE por nombre y estado
     *
     * @param estado
     * @param busqueda
     * @return listado de resultado de la consulta
     */
    public static List<Articulo> buscarArticulosPorNombreEstado(int estado,
            String busqueda) {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("estado", estado));
        p.add(new Parameter("busqueda", "%".concat(busqueda).concat("%")));
        return articuloDAO.readByQuery("SELECT a "
                + "FROM Articulo a "
                + "WHERE a.estado = :estado "
                + "AND a.nombre LIKE :busqueda", p);
    }

    /**
     * Buscar coincidencias con la clausa LIKE por descripcion y estado
     *
     * @param estado
     * @param busqueda
     * @return listado de resultado de la consulta
     */
    public static List<Articulo> buscarArticulosPorDescripcionEstado(int estado,
            String busqueda) {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("estado", estado));
        p.add(new Parameter("busqueda", "%".concat(busqueda).concat("%")));
        return articuloDAO.readByQuery("SELECT a "
                + "FROM Articulo a "
                + "WHERE a.estado = :estado "
                + "AND a.descripcion LIKE :busqueda", p);
    }

    /**
     * Buscar coincidencias con la clausa LIKE por marca y estado
     *
     * @param estado
     * @param busqueda
     * @return listado de resultado de la consulta
     */
    public static List<Articulo> buscarArticulosPorMarcaEstado(int estado,
            String busqueda) {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("estado", estado));
        p.add(new Parameter("busqueda", "%".concat(busqueda).concat("%")));
        return articuloDAO.readByQuery("SELECT a "
                + "FROM Articulo a "
                + "WHERE a.estado = :estado "
                + "AND a.idMarca.nombre LIKE :busqueda", p);
    }

    /**
     * Buscar coincidencias con la clausa LIKE por categoria y estado
     *
     * @param estado
     * @param busqueda
     * @return listado de resultado de la consulta
     */
    public static List<Articulo> buscarArticulosPorCategoriaEstado(int estado,
            String busqueda) {
        if (articuloDAO == null) {
            articuloDAO = DAOManager.getInstancia().getArticuloDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("estado", estado));
        p.add(new Parameter("busqueda", "%".concat(busqueda).concat("%")));
        return articuloDAO.readByQuery("SELECT a "
                + "FROM Articulo a "
                + "WHERE a.estado = :estado "
                + "AND a.idCategoria.nombre LIKE :busqueda", p);
    }
}
