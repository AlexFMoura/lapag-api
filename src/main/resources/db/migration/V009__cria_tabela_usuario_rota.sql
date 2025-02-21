CREATE TABLE IF NOT EXISTS usuario_rota (
    usuario_id BIGINT NOT NULL,
    rota_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, rota_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (rota_id) REFERENCES rota(id) ON DELETE CASCADE
);