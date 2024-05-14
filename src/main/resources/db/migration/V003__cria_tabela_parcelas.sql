CREATE SEQUENCE public.sq_pk_parcelas START 1;

--
-- CRIACAO DA TABELA COM VALOR DEFAULT
--
create table parcelas (
                          id BIGINT NOT NULL DEFAULT nextval('public.sq_pk_parcelas'),
                          parcelamento_id BIGINT not null,
                          parcela integer not null,
                          valor_parcela numeric(10, 2) not null,
                          data_vencimento Date not null,
                          data_pagamento Date,
                          created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),

                          PRIMARY KEY (id)
);

alter table parcelas add constraint fk_parcelas_parcelamento
    foreign key (parcelamento_id) references parcelamento (id);