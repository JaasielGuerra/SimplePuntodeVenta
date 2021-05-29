package com.guerra.simplepuntodeventa.modelo.funciones;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Parameter;
import com.guerra.simplepuntodeventa.modelo.dao.UsuarioDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Proveedor;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jaasiel
 */
public class FuncionesUsuarios {

    private static UsuarioDAOImpl usuarioDAO;

    private FuncionesUsuarios() {

    }

    /**
     * Consultar usuarios con estado
     *
     * @param estado
     * @return
     */
    public static List<Usuario> consultarUsuariosEstado(int estado) {
        if (usuarioDAO == null) {
            usuarioDAO = DAOManager.getInstancia().getUsuarioDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("estado", estado));

        List<Usuario> usuarios = usuarioDAO.readByQuery(
                "SELECT u FROM Usuario u "
                + "WHERE u.estado = :estado ",
                p
        );
        return usuarios;
    }

}
