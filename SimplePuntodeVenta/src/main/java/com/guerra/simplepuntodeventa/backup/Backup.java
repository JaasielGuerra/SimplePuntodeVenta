/*
 * Clase encargada de realizar la copia de seguridad de la base de datos
 */
package com.guerra.simplepuntodeventa.backup;

import java.io.IOException;

/**
 *
 * @author jaasiel
 */
public class Backup {

    public Backup() {

    }

    public void hacerBackup() throws IOException {
//        //USANDO API DE JAVA NIO PARA COPIAR LA BASE DE DATOS
//        String fecha = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
//        String hora = LocalTime.now().format(DateTimeFormatter.ISO_TIME);
//        String nombreBackup = "backup-" + fecha + "-" + hora + ".db";
//        String rutaBackup = System.getProperty("user.home") + File.separator + "backup_spv";
//
//        Path BDOriginal = Paths.get(EMFactory.getRutaDB());
//        Path BDBackup = Paths.get(rutaBackup + File.separator + nombreBackup);
//
//        //crear directorio si no existe
//        if (!Files.exists(Paths.get(rutaBackup))) {
//            Files.createDirectory(Paths.get(rutaBackup));
//        }
//
//        //hacer la copia en disco
//        Files.copy(BDOriginal, BDBackup,
//                StandardCopyOption.REPLACE_EXISTING);
    }

}
