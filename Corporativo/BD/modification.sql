ALTER TABLE `dbcorposmartfit`.`codigo_descuento`
    ADD COLUMN `periodicidad` VARCHAR(45) NOT NULL AFTER `empresa_empleador`;
SET SQL_SAFE_UPDATES = 0;
UPDATE dbcorposmartfit.codigo_descuento SET periodicidad='TRIMESTRE';
SET SQL_SAFE_UPDATES = 1;