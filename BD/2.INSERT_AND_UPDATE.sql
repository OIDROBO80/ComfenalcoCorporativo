## Crear Planes
INSERT INTO `dbcorposmartfit`.`planes` (`nombre`, `periocidad`,`dias_validacion`) VALUES ('MENSUAL', '30', '2');
INSERT INTO `dbcorposmartfit`.`planes` (`nombre`, `periocidad`,`dias_validacion`) VALUES ('TRIMESTRE', '90', '2');
INSERT INTO `dbcorposmartfit`.`planes` (`nombre`, `periocidad`,`dias_validacion`) VALUES ('ANUAL', '365', '2');

## Asigna periodos a empresa  800182281

INSERT INTO `dbcorposmartfit`.`empresa_empleador_x_plan` (`id_empresa_empleador`,`id_plan`)
VALUES ((SELECT id FROM dbcorposmartfit.empresa_empleador WHERE documento_numero=800182281),
        (SELECT ID FROM dbcorposmartfit.planes WHERE nombre='MENSUAL'));

INSERT INTO `dbcorposmartfit`.`empresa_empleador_x_plan` (`id_empresa_empleador`,`id_plan`)
VALUES ((SELECT id FROM dbcorposmartfit.empresa_empleador WHERE documento_numero=800182281),
        (SELECT ID FROM dbcorposmartfit.planes WHERE nombre='TRIMESTRE'));

INSERT INTO `dbcorposmartfit`.`empresa_empleador_x_plan` (`id_empresa_empleador`,`id_plan`)
VALUES ((SELECT id FROM dbcorposmartfit.empresa_empleador WHERE documento_numero=800182281),
        (SELECT ID FROM dbcorposmartfit.planes WHERE nombre='ANUAL'));

## Asigna periodos a empresa  1152221128

INSERT INTO `dbcorposmartfit`.`empresa_empleador_x_plan` (`id_empresa_empleador`,`id_plan`)
VALUES ((SELECT id FROM dbcorposmartfit.empresa_empleador WHERE documento_numero=1152221128),
        (SELECT ID FROM dbcorposmartfit.planes WHERE nombre='MENSUAL'));

INSERT INTO `dbcorposmartfit`.`empresa_empleador_x_plan` (`id_empresa_empleador`,`id_plan`)
VALUES ((SELECT id FROM dbcorposmartfit.empresa_empleador WHERE documento_numero=1152221128),
        (SELECT ID FROM dbcorposmartfit.planes WHERE nombre='TRIMESTRE'));

INSERT INTO `dbcorposmartfit`.`empresa_empleador_x_plan` (`id_empresa_empleador`,`id_plan`)
VALUES ((SELECT id FROM dbcorposmartfit.empresa_empleador WHERE documento_numero=1152221128),
        (SELECT ID FROM dbcorposmartfit.planes WHERE nombre='ANUAL'));

# Actualiza campo id_empresa_plan en codigo_descuento para empresa 800182281

UPDATE dbcorposmartfit.codigo_descuento AS CD
    INNER JOIN (SELECT EEP.ID,P.nombre, EE.ID AS id_empresa_empleador
                FROM dbcorposmartfit.empresa_empleador_x_plan AS EEP
                         INNER JOIN dbcorposmartfit.empresa_empleador AS EE ON EE.ID=EEP.id_empresa_empleador
                         INNER JOIN dbcorposmartfit.planes AS P ON P.ID=EEP.id_plan
                WHERE P.nombre='MENSUAL' AND EE.documento_numero='800182281') AS T1 ON T1.id_empresa_empleador=CD.empresa_empleador
SET id_empresa_plan = T1.ID;

UPDATE dbcorposmartfit.codigo_descuento AS CD
    INNER JOIN (SELECT EEP.ID,P.nombre, EE.ID AS id_empresa_empleador
                FROM dbcorposmartfit.empresa_empleador_x_plan AS EEP
                         INNER JOIN dbcorposmartfit.empresa_empleador AS EE ON EE.ID=EEP.id_empresa_empleador
                         INNER JOIN dbcorposmartfit.planes AS P ON P.ID=EEP.id_plan
                WHERE P.nombre='TRIMESTRE' AND EE.documento_numero='800182281') AS T1 ON T1.id_empresa_empleador=CD.empresa_empleador
SET id_empresa_plan = T1.ID;

UPDATE dbcorposmartfit.codigo_descuento AS CD
    INNER JOIN (SELECT EEP.ID,P.nombre, EE.ID AS id_empresa_empleador
                FROM dbcorposmartfit.empresa_empleador_x_plan AS EEP
                         INNER JOIN dbcorposmartfit.empresa_empleador AS EE ON EE.ID=EEP.id_empresa_empleador
                         INNER JOIN dbcorposmartfit.planes AS P ON P.ID=EEP.id_plan
                WHERE P.nombre='ANUAL' AND EE.documento_numero='800182281') AS T1 ON T1.id_empresa_empleador=CD.empresa_empleador
SET id_empresa_plan = T1.ID;

# Actualiza campo id_empresa_plan en codigo_descuento para empresa 1152221128

UPDATE dbcorposmartfit.codigo_descuento AS CD
    INNER JOIN (SELECT EEP.ID,P.nombre AS periodicidad, EE.ID AS id_empresa_empleador
                FROM dbcorposmartfit.empresa_empleador_x_plan AS EEP
                         INNER JOIN dbcorposmartfit.empresa_empleador AS EE ON EE.ID=EEP.id_empresa_empleador
                         INNER JOIN dbcorposmartfit.planes AS P ON P.ID=EEP.id_plan
                WHERE P.nombre='MENSUAL' AND EE.documento_numero='1152221128') AS T1
    ON T1.id_empresa_empleador=CD.empresa_empleador AND
       T1.periodicidad=CD.periodicidad
SET CD.id_empresa_plan = T1.ID;

UPDATE dbcorposmartfit.codigo_descuento AS CD
    INNER JOIN (SELECT EEP.ID,P.nombre AS periodicidad, EE.ID AS id_empresa_empleador
                FROM dbcorposmartfit.empresa_empleador_x_plan AS EEP
                         INNER JOIN dbcorposmartfit.empresa_empleador AS EE ON EE.ID=EEP.id_empresa_empleador
                         INNER JOIN dbcorposmartfit.planes AS P ON P.ID=EEP.id_plan
                WHERE P.nombre='TRIMESTRE' AND EE.documento_numero='1152221128') AS T1
    ON T1.id_empresa_empleador=CD.empresa_empleador AND
       T1.periodicidad=CD.periodicidad
SET CD.id_empresa_plan = T1.ID;

UPDATE dbcorposmartfit.codigo_descuento AS CD
    INNER JOIN (SELECT EEP.ID,P.nombre AS periodicidad, EE.ID AS id_empresa_empleador
                FROM dbcorposmartfit.empresa_empleador_x_plan AS EEP
                         INNER JOIN dbcorposmartfit.empresa_empleador AS EE ON EE.ID=EEP.id_empresa_empleador
                         INNER JOIN dbcorposmartfit.planes AS P ON P.ID=EEP.id_plan
                WHERE P.nombre='ANUAL' AND EE.documento_numero='1152221128') AS T1
    ON T1.id_empresa_empleador=CD.empresa_empleador AND
       T1.periodicidad=CD.periodicidad
SET CD.id_empresa_plan = T1.ID;

