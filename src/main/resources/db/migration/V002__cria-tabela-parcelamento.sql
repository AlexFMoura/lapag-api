CREATE SEQUENCE public.sq_pk_parcelamento START 1;

--
-- CRIACAO DA TABELA COM VALOR DEFAULT
--
create table parcelamento (
                              id BIGINT NOT NULL DEFAULT nextval('public.sq_pk_parcelamento'),
                              cliente_id BIGINT not null,
                              venda_id BIGINT	not null,
                              status varchar(20) not null,
                              valor_total numeric(10, 2) not null,
                              qtd_parcelas integer,
                              primeiro_vencimento Date not null,
                              created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),

                              PRIMARY KEY (id)
);

alter table parcelamento add constraint fk_parcelamento_cliente
    foreign key (cliente_id) references cliente (id);