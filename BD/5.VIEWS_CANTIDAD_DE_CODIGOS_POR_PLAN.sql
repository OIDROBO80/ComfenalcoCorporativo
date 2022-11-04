CREATE
    ALGORITHM = UNDEFINED
    DEFINER = `user_app`@`localhost`
    SQL SECURITY DEFINER
    VIEW `cantidad_codigos_por_plan` AS
SELECT
    UUID() AS `id`,
    COUNT(`CD`.`asignado`) AS `cantidadDeCodigos`,
    `P`.`nombre` AS `nombrePlan`,
    `EE`.`documento_numero` AS `documentoEmpresa`,
    (CASE
         WHEN (`CD`.`asignado` IS NULL) THEN 0
         ELSE `CD`.`asignado`
        END) AS `asignado`
FROM
    (((`empresa_empleador_x_plan` `EEP`
        LEFT JOIN `codigo_descuento` `CD` ON ((`EEP`.`id` = `CD`.`id_empresa_plan`)))
        JOIN `empresa_empleador` `EE` ON ((`EEP`.`id_empresa_empleador` = `EE`.`id`)))
        JOIN `planes` `P` ON ((`EEP`.`id_plan` = `P`.`id`)))
GROUP BY `P`.`nombre` , `EE`.`id` , `CD`.`asignado`