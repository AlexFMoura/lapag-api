CREATE SEQUENCE public.sq_pk_usuarios START 1;

CREATE TABLE usuarios (
    id BIGINT NOT NULL DEFAULT nextval('public.sq_pk_usuarios'),
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    altered_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

ALTER TABLE parcelamento
ADD COLUMN usuario_id BIGINT NOT NULL;

ALTER TABLE parcelamento
ADD CONSTRAINT fk_parcelamento_usuario
FOREIGN KEY (usuario_id) REFERENCES usuarios (id);