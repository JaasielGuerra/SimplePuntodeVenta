/*
 * Controlador para configuraciones de la empresa
 */
package com.guerra.simplepuntodeventa.controlador.configuracionempresa;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.dao.EmpresaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Empresa;
import com.guerra.simplepuntodeventa.global.ConfiguracionEmpresa;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.vista.empresa.IfrmMenuEmpresa;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author jaasiel
 */
public class ControladorConfiguracion {

    private final IfrmMenuEmpresa ifrmEmpresa;

    private final EmpresaDAOImpl empresaDAO = DAOManager.getInstancia().getEmpresaDAO();
    ConfiguracionEmpresa configuracion = ConfiguracionEmpresa.getInstancia();
    private Empresa empresa;

    public ControladorConfiguracion(IfrmMenuEmpresa ifrmMenuEmpresa) {
        this.ifrmEmpresa = ifrmMenuEmpresa;
        init();
    }

    private void init() {
        ifrmEmpresa.btnGuardar.addActionListener((ae) -> {
            guardarCambios();
        });
    }

    /////////////////metodos publicos/////////////////
    public void consultarDatos() {
        empresa = configuracion.getEmpresa();

        ifrmEmpresa.logo.setImagePath(configuracion.getLogo());
        ifrmEmpresa.txtNombre.setText(empresa.getNombre());
        ifrmEmpresa.txtDireccion.setText(empresa.getDireccion());
        ifrmEmpresa.txtTel.setText(empresa.getTelefono());

    }

    ///////////////////eventos////////////////////////
    private void guardarCambios() {

        boolean camposVacios = ValidarJTextField.camposVacios(
                ifrmEmpresa.txtNombre,
                ifrmEmpresa.txtDireccion,
                ifrmEmpresa.txtTel
        );

        if (!camposVacios) {

            String[] split = ifrmEmpresa.logo.getImagePath().split(File.separator);
            String logo = split[split.length - 1];
            String imagePath = ifrmEmpresa.logo.getImagePath();

            Path origen = Paths.get(imagePath);
            Path destino = Paths.get(configuracion.getRutaData() + logo);

            try {
                Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                MsjException.msjErrorEstandar(ifrmEmpresa, ex.getMessage());
                return;
            }

            empresa.setNombre(ifrmEmpresa.txtNombre.getText());
            empresa.setDireccion(ifrmEmpresa.txtDireccion.getText());
            empresa.setTelefono(ifrmEmpresa.txtTel.getText());
            empresa.setLogo(logo);

            try {
                empresaDAO.update(empresa);

                configuracion.setEmpresa(empresa);//actualizo los datos globales
                
                MsjInfo.msjActualizacionExitosa(ifrmEmpresa);

            } catch (Exception e) {
                MsjException.msjErrorActualizar(ifrmEmpresa, e.getMessage());
            }

        } else {
            MsjValidacion.msjJTextFieldRequeridos(ifrmEmpresa);
        }

    }

}
