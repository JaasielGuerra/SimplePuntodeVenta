-- MySQL Script generated by MySQL Workbench
-- dom 16 may 2021 22:58:04
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_spv
-- -----------------------------------------------------
-- demo

-- -----------------------------------------------------
-- Schema db_spv
--
-- demo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_spv` DEFAULT CHARACTER SET utf8 ;
USE `db_spv` ;

-- -----------------------------------------------------
-- Table `db_spv`.`privilegio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`privilegio` (
  `id_privilegio` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(45) NOT NULL,
  `estado` INT NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `ventas` INT NOT NULL,
  `articulos` INT NOT NULL,
  `inventario` INT NOT NULL,
  `compras` INT NOT NULL,
  `clientes` INT NOT NULL,
  `reportes` INT NOT NULL,
  `configuracion` INT NOT NULL,
  `servicio` INT NOT NULL,
  `proveedores` INT NOT NULL,
  PRIMARY KEY (`id_privilegio`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`usuario` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(150) NOT NULL,
  `user` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `estado` INT NOT NULL,
  `id_privilegio` INT NOT NULL,
  PRIMARY KEY (`id_usuario`),
  INDEX `fk_usuario_privilegio1_idx` (`id_privilegio` ASC),
  CONSTRAINT `fk_usuario_privilegio1`
    FOREIGN KEY (`id_privilegio`)
    REFERENCES `db_spv`.`privilegio` (`id_privilegio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`categoria` (
  `id_categoria` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `descripcion` VARCHAR(255) NOT NULL,
  `estado` INT NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_categoria`),
  INDEX `fk_categoria_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_categoria_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`marca`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`marca` (
  `id_marca` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `descripcion` VARCHAR(255) NOT NULL,
  `estado` INT NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_marca`),
  INDEX `fk_marca_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_marca_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`ubicacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`ubicacion` (
  `id_ubicacion` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `descripcion` VARCHAR(255) NOT NULL,
  `estado` INT NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_ubicacion`),
  INDEX `fk_ubicacion_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_ubicacion_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`articulo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`articulo` (
  `id_articulo` INT NOT NULL AUTO_INCREMENT,
  `cod1` VARCHAR(100) NOT NULL,
  `cod2` VARCHAR(100) NULL,
  `cod3` VARCHAR(100) NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `descripcion` VARCHAR(255) NOT NULL,
  `cantidad` INT NOT NULL,
  `min_existencia` INT NOT NULL,
  `precio_compra` DOUBLE NOT NULL,
  `precio_venta` DOUBLE NOT NULL,
  `porcentaje_ganancia` DOUBLE NOT NULL,
  `ganancia` DOUBLE NOT NULL,
  `estado` INT NOT NULL,
  `id_marca` INT NOT NULL,
  `id_categoria` INT NOT NULL,
  `id_ubicacion` INT NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_articulo`),
  INDEX `fk_articulo_categoria_idx` (`id_categoria` ASC),
  INDEX `fk_articulo_marca1_idx` (`id_marca` ASC),
  INDEX `fk_articulo_ubicacion1_idx` (`id_ubicacion` ASC),
  INDEX `fk_articulo_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_articulo_categoria`
    FOREIGN KEY (`id_categoria`)
    REFERENCES `db_spv`.`categoria` (`id_categoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_articulo_marca1`
    FOREIGN KEY (`id_marca`)
    REFERENCES `db_spv`.`marca` (`id_marca`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_articulo_ubicacion1`
    FOREIGN KEY (`id_ubicacion`)
    REFERENCES `db_spv`.`ubicacion` (`id_ubicacion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_articulo_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`cliente` (
  `id_cliente` INT NOT NULL AUTO_INCREMENT,
  `dpi` VARCHAR(45) NULL,
  `nombre_completo` VARCHAR(100) NOT NULL,
  `direccion` VARCHAR(100) NULL,
  `telefono` VARCHAR(45) NULL,
  `limite` DOUBLE NOT NULL,
  `estado` INT NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_cliente`),
  INDEX `fk_cliente_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_cliente_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`venta` (
  `id_venta` INT NOT NULL AUTO_INCREMENT,
  `fecha` VARCHAR(45) NOT NULL,
  `total` DOUBLE NOT NULL,
  `total_ganancia` DOUBLE NOT NULL,
  `tipo_venta` INT NOT NULL COMMENT 'VENTA_CONTADO = 1;\nVENTA_CREDITO = 2;',
  `id_cliente` INT NOT NULL,
  `id_usuario` INT NOT NULL,
  `estado` INT NOT NULL COMMENT '1: venta realizadq\n0: venta cancelada',
  `saldo` DOUBLE NULL COMMENT 'Puede ser null porque solo usa cuando es una venta al credito',
  `nombre` VARCHAR(100) NULL COMMENT 'Nombre del tab de venta',
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  PRIMARY KEY (`id_venta`),
  INDEX `fk_venta_cliente1_idx` (`id_cliente` ASC),
  INDEX `fk_venta_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_venta_cliente1`
    FOREIGN KEY (`id_cliente`)
    REFERENCES `db_spv`.`cliente` (`id_cliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_venta_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`servicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`servicio` (
  `id_servicio` INT NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(100) NOT NULL,
  `descripcion` VARCHAR(100) NOT NULL,
  `precio_a` VARCHAR(45) NOT NULL,
  `precio_b` VARCHAR(45) NOT NULL,
  `precio_c` VARCHAR(45) NOT NULL,
  `estado` INT NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_servicio`),
  INDEX `fk_servicio_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_servicio_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`detalle_venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`detalle_venta` (
  `id_detalle_venta` INT NOT NULL AUTO_INCREMENT,
  `cantidad` INT NOT NULL,
  `precio_unitario` DOUBLE NOT NULL,
  `sub_total` DOUBLE NOT NULL,
  `ganancia` DOUBLE NOT NULL,
  `tipo` INT NOT NULL COMMENT '1: articulo\n2: servicio',
  `estado` INT NOT NULL COMMENT '0: venta cancelada\n1: venta realizada',
  `id_venta` INT NOT NULL,
  `id_articulo` INT NULL,
  `id_servicio` INT NULL,
  PRIMARY KEY (`id_detalle_venta`),
  INDEX `fk_detalle_venta_venta1_idx` (`id_venta` ASC),
  INDEX `fk_detalle_venta_articulo1_idx` (`id_articulo` ASC),
  INDEX `fk_detalle_venta_servicio1_idx` (`id_servicio` ASC),
  CONSTRAINT `fk_detalle_venta_venta1`
    FOREIGN KEY (`id_venta`)
    REFERENCES `db_spv`.`venta` (`id_venta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_venta_articulo1`
    FOREIGN KEY (`id_articulo`)
    REFERENCES `db_spv`.`articulo` (`id_articulo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_venta_servicio1`
    FOREIGN KEY (`id_servicio`)
    REFERENCES `db_spv`.`servicio` (`id_servicio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`empresa` (
  `id_empresa` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `direccion` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(45) NOT NULL,
  `logo` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_empresa`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`cuenta_cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`cuenta_cliente` (
  `id_cuenta_cliente` INT NOT NULL AUTO_INCREMENT,
  `fecha` VARCHAR(45) NOT NULL,
  `comentario` VARCHAR(100) NULL,
  `cargo` DOUBLE NOT NULL,
  `abono` DOUBLE NOT NULL,
  `saldo` DOUBLE NOT NULL,
  `id_venta` INT NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_cuenta_cliente`),
  INDEX `fk_cuenta_cliente_venta1_idx` (`id_venta` ASC),
  INDEX `fk_cuenta_cliente_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_cuenta_cliente_venta1`
    FOREIGN KEY (`id_venta`)
    REFERENCES `db_spv`.`venta` (`id_venta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cuenta_cliente_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`historial_abono_cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`historial_abono_cliente` (
  `id_historial_abono_cliente` INT NOT NULL AUTO_INCREMENT,
  `id_venta` INT NOT NULL,
  `fecha` VARCHAR(45) NOT NULL,
  `abono` DOUBLE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_historial_abono_cliente`),
  INDEX `fk_historial_abono_cliente_venta1_idx` (`id_venta` ASC),
  INDEX `fk_historial_abono_cliente_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_historial_abono_cliente_venta1`
    FOREIGN KEY (`id_venta`)
    REFERENCES `db_spv`.`venta` (`id_venta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_historial_abono_cliente_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`kardex`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`kardex` (
  `id_kardex` INT NOT NULL AUTO_INCREMENT,
  `fecha` VARCHAR(45) NOT NULL,
  `concepto` VARCHAR(100) NOT NULL,
  `existencia_anterior` INT NOT NULL,
  `tipo` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `existencia_posterior` INT NOT NULL,
  `id_usuario` INT NOT NULL,
  `id_articulo` INT NOT NULL,
  `hora_commit` TIME NOT NULL,
  `fecha_commit` DATE NOT NULL,
  PRIMARY KEY (`id_kardex`),
  INDEX `fk_kardex_articulo1_idx` (`id_articulo` ASC),
  INDEX `fk_kardex_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_kardex_articulo1`
    FOREIGN KEY (`id_articulo`)
    REFERENCES `db_spv`.`articulo` (`id_articulo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kardex_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`proveedor` (
  `id_proveedor` INT NOT NULL AUTO_INCREMENT,
  `nit` VARCHAR(45) NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `direccion` VARCHAR(100) NULL,
  `telefono` VARCHAR(45) NULL,
  `estado` INT NOT NULL,
  `hora_commit` TIME NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_proveedor`),
  INDEX `fk_proveedor_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_proveedor_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`compra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`compra` (
  `id_compra` INT NOT NULL AUTO_INCREMENT,
  `fecha` VARCHAR(45) NOT NULL,
  `no_factura` VARCHAR(45) NOT NULL,
  `serie_factura` VARCHAR(45) NOT NULL,
  `total` DOUBLE NOT NULL,
  `id_proveedor` INT NOT NULL,
  `id_usuario` INT NOT NULL,
  `estado` INT NOT NULL COMMENT '0: compra cancelada\n1: compra realizada',
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `saldo` DOUBLE NULL COMMENT 'puede ser null porque solo se usa si la compra es al crédito',
  PRIMARY KEY (`id_compra`),
  INDEX `fk_compra_proveedor1_idx` (`id_proveedor` ASC),
  INDEX `fk_compra_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_compra_proveedor1`
    FOREIGN KEY (`id_proveedor`)
    REFERENCES `db_spv`.`proveedor` (`id_proveedor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_compra_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`detalle_compra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`detalle_compra` (
  `id_detalle_compra` INT NOT NULL AUTO_INCREMENT,
  `cantidad` INT NOT NULL,
  `sub_total` DOUBLE NOT NULL,
  `precio_unitario` DOUBLE NOT NULL,
  `estado` INT NOT NULL COMMENT '0: compra cancelada\n1: compra realizada',
  `id_articulo` INT NOT NULL,
  `id_compra` INT NOT NULL,
  PRIMARY KEY (`id_detalle_compra`),
  INDEX `fk_detalle_compra_compra1_idx` (`id_compra` ASC),
  INDEX `fk_detalle_compra_articulo1_idx` (`id_articulo` ASC),
  CONSTRAINT `fk_detalle_compra_compra1`
    FOREIGN KEY (`id_compra`)
    REFERENCES `db_spv`.`compra` (`id_compra`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_compra_articulo1`
    FOREIGN KEY (`id_articulo`)
    REFERENCES `db_spv`.`articulo` (`id_articulo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`cuenta_proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`cuenta_proveedor` (
  `id_cuenta_proveedor` INT NOT NULL AUTO_INCREMENT,
  `fecha` VARCHAR(45) NOT NULL,
  `comentario` VARCHAR(100) NULL,
  `cargo` DOUBLE NOT NULL,
  `abono` DOUBLE NOT NULL,
  `saldo` DOUBLE NOT NULL,
  `id_compra` INT NOT NULL,
  `hora_commit` TIME NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_cuenta_proveedor`),
  INDEX `fk_cuenta_proveedor_compra1_idx` (`id_compra` ASC),
  INDEX `fk_cuenta_proveedor_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_cuenta_proveedor_compra1`
    FOREIGN KEY (`id_compra`)
    REFERENCES `db_spv`.`compra` (`id_compra`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cuenta_proveedor_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_spv`.`historial_abono_proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_spv`.`historial_abono_proveedor` (
  `id_historial_abono_proveedor` INT NOT NULL AUTO_INCREMENT,
  `fecha` VARCHAR(45) NOT NULL,
  `abono` DOUBLE NOT NULL,
  `id_compra` INT NOT NULL,
  `fecha_commit` DATE NOT NULL,
  `hora_commit` TIME NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_historial_abono_proveedor`),
  INDEX `fk_historial_abono_proveedor_compra1_idx` (`id_compra` ASC),
  INDEX `fk_historial_abono_proveedor_usuario1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_historial_abono_proveedor_compra1`
    FOREIGN KEY (`id_compra`)
    REFERENCES `db_spv`.`compra` (`id_compra`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_historial_abono_proveedor_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `db_spv`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Datos de prueba
-- -----------------------------------------------------
INSERT INTO `db_spv`.`privilegio` VALUES (1, 'ADMINISTRADOR', '1', '2021/05/17', '09:48', '1', '1', '1', '1', '1', '1', '1', '1', '1');
INSERT INTO `db_spv`.`usuario` VALUES (1, 'Jaasiel Ebiezer Guerra González', 'admin', 'admin', '2021/05/17', '09:48', '1', '1');
INSERT INTO `db_spv`.`cliente` VALUES (1, '-', 'PÚBLICO GENERAL', '-', '-', '0', '1', '2021/05/17', '09:48', '1');
INSERT INTO `db_spv`.`proveedor` VALUES (1, '-', 'PROVEEDOR PRINCIPAL', '-', '-', '1', '9:48', '2021/05/17', '1');

