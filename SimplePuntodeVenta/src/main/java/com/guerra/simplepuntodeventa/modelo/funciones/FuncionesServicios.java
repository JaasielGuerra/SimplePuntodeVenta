package com.guerra.simplepuntodeventa.modelo.funciones;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Parameter;
import com.guerra.simplepuntodeventa.modelo.dao.ServicioDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Servicio;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jaasiel
 */
public class FuncionesServicios {

    private static ServicioDAOImpl servicioDAO;

    private FuncionesServicios() {

    }

    /**
     * Buscar un servicio por su codigo y con estado 1 (activo)
     *
     * @param codigo
     * @return devuelve el servicio o null si no se encotro nada
     */
    public static Servicio getServicioPorCodigo(String codigo) {
        if (servicioDAO == null) {
            servicioDAO = DAOManager.getInstancia().getServicioDAO();
        }
        Servicio s = null;
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("codigo", codigo.toUpperCase()));
        List<Servicio> resultado = servicioDAO.readByQuery(
                "SELECT s "
                + "FROM Servicio s "
                + "WHERE s.estado = 1 "
                + "AND s.codigo = :codigo", p
        );
        for (Servicio ser : resultado) {
            s = ser;
        }
        return s;
    }

    
     /**
     * Buscar coincidencias con la clausa LIKE por descripcion y estado
     *
     * @param estado
     * @param busqueda
     * @return listado de resultado de la consulta
     */
    public static List<Servicio> buscarServicioPorDescripcionEstado(int estado,
            String busqueda) {
        if (servicioDAO == null) {
            servicioDAO = DAOManager.getInstancia().getServicioDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("estado", estado));
        p.add(new Parameter("busqueda", "%".concat(busqueda).concat("%")));
        return servicioDAO.readByQuery("SELECT s "
                + "FROM Servicio s "
                + "WHERE s.estado = :estado "
                + "AND s.descripcion LIKE :busqueda", p);
    }
}
