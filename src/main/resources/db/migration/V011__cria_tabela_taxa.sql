--
-- PRIMEIRO, CRIA-SE A SEQUENCE MANUALMENTE
--
CREATE SEQUENCE public.sq_pk_taxa START 1;

--
-- CRIAÇÃO DA TABELA COM VALOR DEFAULT
--
CREATE TABLE public.taxa (
    id INTEGER NOT NULL DEFAULT nextval('public.sq_pk_taxa'),
    juros DOUBLE PRECISION,
    multa DOUBLE PRECISION,
    data_inativo DATE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),

    PRIMARY KEY (id)
);