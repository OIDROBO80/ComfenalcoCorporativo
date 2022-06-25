-- --------------------------------------------------------
-- Host:                         10.125.64.3
-- Versión del servidor:         5.7.18 - MySQL Community Server (GPL)
-- SO del servidor:              Linux
-- HeidiSQL Versión:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura para tabla corporativos_smarfit.codigo_descuento
DROP TABLE IF EXISTS `codigo_descuento`;
CREATE TABLE IF NOT EXISTS `codigo_descuento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(45) NOT NULL,
  `asignado` tinyint(1) DEFAULT '0',
  `empresa_empleador` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_UNIQUE` (`codigo`),
  KEY `fk_codigo_descuento_empresa_empleador_idx` (`empresa_empleador`),
  CONSTRAINT `fk_codigo_descuento_empresa_empleador_idx` FOREIGN KEY (`empresa_empleador`) REFERENCES `empresa_empleador` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1251 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla corporativos_smarfit.codigo_descuento: ~10 rows (aproximadamente)
/*!40000 ALTER TABLE `codigo_descuento` DISABLE KEYS */;
/*!40000 ALTER TABLE `codigo_descuento` ENABLE KEYS */;

-- Volcando estructura para tabla corporativos_smarfit.empresa_afiliado
DROP TABLE IF EXISTS `empresa_afiliado`;
CREATE TABLE IF NOT EXISTS `empresa_afiliado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empresa_empleador` int(11) NOT NULL,
  `documento_tipo` int(11) NOT NULL,
  `documento_numero` varchar(20) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `fecha_creacion` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_empresa_afiliado` (`empresa_empleador`,`documento_tipo`,`documento_numero`),
  KEY `fk_empresa_afiliado_documento_tipo_idx` (`documento_tipo`),
  KEY `fk_empresa_afiliado_empresa_empleador_idx` (`empresa_empleador`),
  CONSTRAINT `fk_empresa_afiliado_documento_tipo` FOREIGN KEY (`documento_tipo`) REFERENCES `tipo_documento_identidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_empresa_afiliado_empresa_empleador` FOREIGN KEY (`empresa_empleador`) REFERENCES `empresa_empleador` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=567 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla corporativos_smarfit.empresa_afiliado: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `empresa_afiliado` DISABLE KEYS */;
/*!40000 ALTER TABLE `empresa_afiliado` ENABLE KEYS */;

-- Volcando estructura para tabla corporativos_smarfit.empresa_afiliado_x_codigo_descuento
DROP TABLE IF EXISTS `empresa_afiliado_x_codigo_descuento`;
CREATE TABLE IF NOT EXISTS `empresa_afiliado_x_codigo_descuento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empresa_afiliado` int(11) NOT NULL,
  `codigo_descuento` int(11) NOT NULL,
  `asignado` tinyint(1) DEFAULT '0',
  `fecha_asignacion` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_empresa_afiliado_x_codigo_descuento` (`empresa_afiliado`,`codigo_descuento`),
  UNIQUE KEY `uk_codigo_descuento_unique` (`codigo_descuento`),
  KEY `fk_empresa_afiliado_x_codigo_descuento_idx` (`codigo_descuento`),
  KEY `fk_codigo_descuento_x_empresa_afiliado_idx` (`empresa_afiliado`),
  CONSTRAINT `fk_codigo_descuento_x_empresa_afiliado` FOREIGN KEY (`empresa_afiliado`) REFERENCES `empresa_afiliado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_empresa_afiliado_x_codigo_descuento` FOREIGN KEY (`codigo_descuento`) REFERENCES `codigo_descuento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla corporativos_smarfit.empresa_afiliado_x_codigo_descuento: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `empresa_afiliado_x_codigo_descuento` DISABLE KEYS */;
/*!40000 ALTER TABLE `empresa_afiliado_x_codigo_descuento` ENABLE KEYS */;

-- Volcando estructura para tabla corporativos_smarfit.empresa_empleador
DROP TABLE IF EXISTS `empresa_empleador`;
CREATE TABLE IF NOT EXISTS `empresa_empleador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `razon_social` varchar(120) NOT NULL,
  `documento_tipo` int(11) NOT NULL,
  `documento_numero` varchar(20) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `email` varchar(45) NOT NULL,
  `activa` tinyint(1) NOT NULL DEFAULT '1',
  `representante_nombre` varchar(120) DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `membresia` int(11) NOT NULL,
  `logo` varchar(250) NOT NULL,
  `textoEmail` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_empresa_empleador` (`documento_tipo`,`documento_numero`,`membresia`),
  KEY `fk_empresa_empleador_documento_tipo_idx` (`documento_tipo`),
  KEY `fk_empresa_empleador_membresia` (`membresia`),
  CONSTRAINT `fk_empresa_empleador_documento_tipo` FOREIGN KEY (`documento_tipo`) REFERENCES `tipo_documento_identidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_empresa_empleador_membresia` FOREIGN KEY (`membresia`) REFERENCES `membresia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla corporativos_smarfit.empresa_empleador: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `empresa_empleador` DISABLE KEYS */;
/*!40000 ALTER TABLE `empresa_empleador` ENABLE KEYS */;

-- Volcando estructura para tabla corporativos_smarfit.membresia
DROP TABLE IF EXISTS `membresia`;
CREATE TABLE IF NOT EXISTS `membresia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL DEFAULT '0',
  KEY `PRIMARY KEY` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla corporativos_smarfit.membresia: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `membresia` DISABLE KEYS */;
REPLACE INTO `membresia` (`id`, `nombre`) VALUES
	(1, 'Convencional'),
	(2, 'VIP');
/*!40000 ALTER TABLE `membresia` ENABLE KEYS */;

-- Volcando estructura para tabla corporativos_smarfit.tipo_documento_identidad
DROP TABLE IF EXISTS `tipo_documento_identidad`;
CREATE TABLE IF NOT EXISTS `tipo_documento_identidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(4) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla corporativos_smarfit.tipo_documento_identidad: ~12 rows (aproximadamente)
/*!40000 ALTER TABLE `tipo_documento_identidad` DISABLE KEYS */;
REPLACE INTO `tipo_documento_identidad` (`id`, `codigo`, `descripcion`) VALUES
	(1, 'CC01', 'Cedula'),
	(2, 'TI01', 'Tarjeta de Identidad'),
	(3, 'RC01', 'Registro Civil'),
	(4, 'S', 'Sec Educ'),
	(5, 'CE01', 'Cedula Extranjeria'),
	(6, 'NT01', 'NIT'),
	(7, 'U', 'NUIP'),
	(8, 'P', 'Pasaporte'),
	(9, 'D', 'Carnet Diplomatico'),
	(10, 'V', 'Permiso Especial'),
	(11, 'PPN', 'Pasaporte'),
	(12, 'PPNS', 'Pasapor');
/*!40000 ALTER TABLE `tipo_documento_identidad` ENABLE KEYS */;

-- Volcando estructura para tabla corporativos_smarfit.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `id_user_role` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk_users_user_roles_idx` (`id_user_role`),
  CONSTRAINT `fk_users_user_roles` FOREIGN KEY (`id_user_role`) REFERENCES `user_roles` (`user_role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla corporativos_smarfit.users: ~22 rows (aproximadamente)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
REPLACE INTO `users` (`id_user`, `id_user_role`, `username`, `password`, `enabled`) VALUES
	(1, 1, 'smartfit', '123456', 1),
	(2, 2, 'admin1', '#Sm2o19', 1),
	(3, 4, 'corp1', '#Sm2o19', 1),
	(4, 5, 'user1', '#Sm2o19', 1),
	(5, 6, 'user2', '#Sm2o19', 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Volcando estructura para tabla corporativos_smarfit.user_roles
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `uni_username_role` (`role`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla corporativos_smarfit.user_roles: ~22 rows (aproximadamente)
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
REPLACE INTO `user_roles` (`user_role_id`, `username`, `role`) VALUES
	(2, 'admin1', 'ROLE_ADMIN'),
	(1, 'smartfit', 'ROLE_ADMIN'),
	(4, 'corp1', 'ROLE_CORP'),
	(3, 'smartfit', 'ROLE_CORP'),
	(5, 'user1', 'ROLE_USER'),
	(6, 'user2', 'ROLE_USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
