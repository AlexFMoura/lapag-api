ALTER TABLE parcelas
ADD COLUMN juros double precision NOT NULL DEFAULT 0.0333,
ADD COLUMN multa double precision NOT NULL DEFAULT 2,
ADD COLUMN valor_multa numeric(10,2),
ADD COLUMN valor_juros numeric(10,2);