--
-- PRIMEIRO, CRIA-SE A SEQUENCE MANUALMENTE
--
CREATE SEQUENCE public.sq_pk_cliente START 1;

--
-- CRIACAO DA TABELA COM VALOR DEFAULT
--
create table cliente (
     id BIGINT NOT NULL DEFAULT nextval('public.sq_pk_cliente'),
     name varchar(80) not null,
     email varchar(255) not null,
     telefone varchar(20) not null,

     PRIMARY KEY (id)
);

alter table cliente add constraint uk_cliente unique (email);
