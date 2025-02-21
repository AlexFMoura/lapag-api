INSERT INTO rota (id, nome) VALUES (1, 'admin')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO rota (id, nome) VALUES (2, 'basic')
    ON CONFLICT (id) DO NOTHING;