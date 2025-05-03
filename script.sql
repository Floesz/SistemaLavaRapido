CREATE DATABASE IF NOT EXISTS lava_rapido;
USE lava_rapido;

-- Criar tabela de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    telefone VARCHAR(15),
    email VARCHAR(100),
    placaVeiculo VARCHAR(7)
);

-- Criar tabela de tipos de serviços
CREATE TABLE IF NOT EXISTS tipo_servicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10, 2)
);

-- Criar tabela de agendamentos
CREATE TABLE IF NOT EXISTS agendamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    tipo_servico_id INT,
    data_hora DATETIME NOT NULL,
    status VARCHAR(20),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (tipo_servico_id) REFERENCES tipo_servicos(id)
);

-- Inserir dados iniciais em clientes
INSERT INTO clientes (nome, cpf, telefone, email, placaVeiculo) 
VALUES 
    ('João Silva', '12345678901', '999999999', 'joao@email.com', 'ABC1234'),
    ('Maria Souza', '98765432100', '988888888', 'maria@email.com', 'XYZ5678');

-- Inserir dados iniciais em tipo_servicos
INSERT INTO tipo_servicos (nome, descricao, preco) 
VALUES 
    ('Lavagem', 'Lavagem de veículo com produtos especializados', 50.00),
    ('Polimento', 'Polimento e proteção de pintura', 150.00);

-- Inserir dados iniciais em agendamentos
INSERT INTO agendamentos (cliente_id, tipo_servico_id, data_hora, status) 
VALUES 
    (1, 1, '2025-05-01 10:00:00', 'Agendado'),
    (2, 2, '2025-05-02 14:00:00', 'Agendado');
