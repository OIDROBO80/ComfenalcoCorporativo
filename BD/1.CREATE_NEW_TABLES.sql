CREATE TABLE `planes` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `nombre` varchar(45) NOT NULL,
                          `periocidad` int NOT NULL,
                          `dias_validacion` int NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3629 DEFAULT CHARSET=utf8mb3;


CREATE TABLE `empresa_empleador_x_plan` (
                                            `id` int NOT NULL AUTO_INCREMENT,
                                            `id_empresa_empleador` int NOT NULL,
                                            `id_plan` int NOT NULL,
                                            PRIMARY KEY (`id`),
                                            KEY `fk_planes_idx` (`id_plan`),
                                            CONSTRAINT `fk_planes` FOREIGN KEY (`id_plan`) REFERENCES `planes` (`id`),
                                            KEY `fk_empresa_empleador_idx` (`id_empresa_empleador`),
                                            CONSTRAINT `fk_empresa_afiliado` FOREIGN KEY (`id_empresa_empleador`) REFERENCES `empresa_empleador` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3629 DEFAULT CHARSET=utf8mb3;

ALTER TABLE codigo_descuento ADD id_empresa_plan int ;
ALTER TABLE codigo_descuento ADD KEY `fk_empresa_empleador_x_plan_idx` (`id_empresa_plan`);
ALTER TABLE codigo_descuento ADD
    CONSTRAINT fk_empresa_empleador_x_plan FOREIGN KEY (`id_empresa_plan`)
        REFERENCES `empresa_empleador_x_plan` (`id`) ON UPDATE CASCADE ;


## ALTER TABLE codigo_descuento DROP COLUMN id_empresa_plan;
## ALTER TABLE codigo_descuento DROP KEY `fk_empresa_empleador_x_plan_idx`;
## ALTER TABLE codigo_descuento DROP CONSTRAINT `fk_empresa_empleador_x_plan`;