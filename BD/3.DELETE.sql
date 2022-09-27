# Table Codigo Descuento
ALTER TABLE dbcorposmartfit.codigo_descuento
    DROP CONSTRAINT fk_codigo_descuento_empresa_empleador_idx;

ALTER TABLE dbcorposmartfit.codigo_descuento
    DROP COLUMN empresa_empleador;

ALTER TABLE dbcorposmartfit.codigo_descuento
    DROP COLUMN periodicidad;
