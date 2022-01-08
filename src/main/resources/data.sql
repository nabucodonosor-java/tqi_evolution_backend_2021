INSERT INTO Roles (authority) VALUES ('ROLE_CLIENTE');
INSERT INTO Roles (authority) VALUES ('ROLE_ADMIN');

INSERT INTO Clientes (nome, email, cpf, rg, renda, senha) VALUES
('Nabucodonosor', 'admin@mapin.com', '1', '1', '1000', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_cliente_role (cliente_id, role_id) VALUES (1, 1);
INSERT INTO tb_cliente_role (cliente_id, role_id) VALUES (1, 2);