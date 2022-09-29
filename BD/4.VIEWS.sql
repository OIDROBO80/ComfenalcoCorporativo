CREATE VIEW `planes_por_empresa` AS
SELECT
    UUID() AS `id`,
    `EE`.`id` AS `idEmpresa`,
    `EE`.`razon_social` AS `nombreEmpresa`,
    `P`.`id` AS `idPlan`,
    `P`.`nombre` AS `nombrePlan`
FROM
    ((`empresa_empleador_x_plan` `EEP`
        JOIN `empresa_empleador` `EE` ON ((`EEP`.`id_empresa_empleador` = `EE`.`id`)))
        JOIN `planes` `P` ON ((`EEP`.`id_plan` = `P`.`id`)))