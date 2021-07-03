package com.guerra.simplepuntodeventa.controlador.usuarios;

import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.vista.usuarios.IfrmMenuUsuarios;
import com.guerra.simplepuntodeventa.vista.usuarios.PanCrearUsuario;
import com.guerra.simplepuntodeventa.vista.usuarios.PanEditarUsuario;
import com.guerra.simplepuntodeventa.vista.usuarios.PanListUsuarios;
import com.guerra.simplepuntodeventa.vista.usuarios.PanPrivilegios;

/**
 *
 * @author jaasiel
 */
public class MenuUsuarios {

    ////////Vistas//////////////
    private final IfrmMenuUsuarios ifrmMenuUsuarios;
    private final PanListUsuarios panListUsuarios = new PanListUsuarios();
    private final PanCrearUsuario panCrearUsuario = new PanCrearUsuario();
    private final PanEditarUsuario panEditarUsuario = new PanEditarUsuario();
    private final PanPrivilegios panPrivilegios = new PanPrivilegios();

    //////////controlasdores/////////////////////
    private final ControladorUsuarios controladorUsuarios = new ControladorUsuarios(panCrearUsuario,
            panEditarUsuario, panListUsuarios);
    private final ControladorPrivilegios controladorPrivilegios = new ControladorPrivilegios(panPrivilegios);

    public MenuUsuarios(IfrmMenuUsuarios ifrmMenuUsuarios) {
        this.ifrmMenuUsuarios = ifrmMenuUsuarios;
        initOyentes();
    }

    private void initOyentes() {
        ifrmMenuUsuarios.btnConsultarUsuarios.addActionListener((e) -> {
            consultarUsuarios();
        });
        ifrmMenuUsuarios.btnNuevoUsuario.addActionListener((e) -> {
            nuevoUsuario();
        });
        ifrmMenuUsuarios.btnPrivilegios.addActionListener((e) -> {
            privilegio();
        });
    }

    //////////eventos//////////////
    private void consultarUsuarios() {
        controladorUsuarios.consultarUsuarios();
        PanelUtil.cambiarPanel(IfrmMenuUsuarios.PANEL_CAMBIANTE, panListUsuarios);
    }

    private void nuevoUsuario() {
        controladorUsuarios.resetFormularioNuevoUsuario();
        PanelUtil.cambiarPanel(IfrmMenuUsuarios.PANEL_CAMBIANTE, panCrearUsuario);
    }

    private void privilegio() {
        controladorPrivilegios.resetFormulario();
        controladorPrivilegios.consultarPrivilegios();
        PanelUtil.cambiarPanel(IfrmMenuUsuarios.PANEL_CAMBIANTE, panPrivilegios);
    }

}
