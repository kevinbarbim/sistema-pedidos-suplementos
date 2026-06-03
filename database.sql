-- =======================================================
-- CRIAÇÃO DA ESTRUTURA DO BANCO DE DADOS
-- =======================================================
CREATE DATABASE sistema_pedidos;
GO 

USE sistema_pedidos;
GO

CREATE TABLE Pessoa (
    codigo INT PRIMARY KEY IDENTITY(1,1), 
    cpf VARCHAR(14) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    fone VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE Categoria (
    codigo INT PRIMARY KEY IDENTITY(1,1), 
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE Cliente (
    codigo_pessoa INT PRIMARY KEY,
    status VARCHAR(50),
    FOREIGN KEY (codigo_pessoa) REFERENCES Pessoa(codigo) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

CREATE TABLE Funcionario (
    codigo_pessoa INT PRIMARY KEY,
    salario DECIMAL(10, 2),
    FOREIGN KEY (codigo_pessoa) REFERENCES Pessoa(codigo) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

CREATE TABLE Entregador (
    codigo_pessoa INT PRIMARY KEY,
    obs VARCHAR(MAX), 
    FOREIGN KEY (codigo_pessoa) REFERENCES Pessoa(codigo) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

CREATE TABLE Endereco (
    id INT PRIMARY KEY IDENTITY(1,1), 
    cep VARCHAR(10),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    status VARCHAR(50),
    codigo_cliente INT NOT NULL,
    FOREIGN KEY (codigo_cliente) REFERENCES Cliente(codigo_pessoa)
);

CREATE TABLE Produto (
    codigo INT PRIMARY KEY IDENTITY(1,1), 
    descricao VARCHAR(255) NOT NULL,
    estoque INT DEFAULT 0,
    status VARCHAR(50),
    preco DECIMAL(10, 2) NOT NULL,
    codigo_categoria INT NOT NULL,
    FOREIGN KEY (codigo_categoria) REFERENCES Categoria(codigo)
);

CREATE TABLE Pedido (
    nr INT PRIMARY KEY IDENTITY(1,1), 
    data_hora DATETIME NOT NULL,
    status VARCHAR(50),
    total DECIMAL(10, 2),
    codigo_cliente INT NOT NULL,
    codigo_funcionario INT NOT NULL,
    FOREIGN KEY (codigo_cliente) REFERENCES Cliente(codigo_pessoa),
    FOREIGN KEY (codigo_funcionario) REFERENCES Funcionario(codigo_pessoa)
);

CREATE TABLE Entrega (
    nr INT PRIMARY KEY IDENTITY(1,1), 
    valor_entrega DECIMAL(10, 2),
    data_saida DATETIME,
    status VARCHAR(50),
    nr_pedido INT NOT NULL,
    codigo_entregador INT NOT NULL,
    FOREIGN KEY (nr_pedido) REFERENCES Pedido(nr),
    FOREIGN KEY (codigo_entregador) REFERENCES Entregador(codigo_pessoa)
);

CREATE TABLE Item_Pedido (
    nr_pedido INT NOT NULL,
    codigo_produto INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    qtd_vendida INT NOT NULL,
    PRIMARY KEY (nr_pedido, codigo_produto),
    FOREIGN KEY (nr_pedido) REFERENCES Pedido(nr) ON DELETE CASCADE,
    FOREIGN KEY (codigo_produto) REFERENCES Produto(codigo)
);

-- =======================================================
-- 1.1 INJEÇÃO DE DADOS (INSERTS)
-- =======================================================

-- Inserindo 30 Pessoas (1 a 10 Clientes, 11 a 20 Funcionários, 21 a 30 Entregadores)
INSERT INTO Pessoa (cpf, nome, fone, email) VALUES
('111.111.111-01', 'Ana Silva', '11999991111', 'ana@email.com'),
('111.111.111-02', 'Bruno Souza', '11999991112', 'bruno@email.com'),
('111.111.111-03', 'Carlos Eduardo', '11999991113', 'carlos@email.com'),
('111.111.111-04', 'Daniela Costa', '11999991114', 'daniela@email.com'),
('111.111.111-05', 'Eduardo Lima', '11999991115', 'eduardo@email.com'),
('111.111.111-06', 'Fernanda Alves', '11999991116', 'fernanda@email.com'),
('111.111.111-07', 'Gabriel Gomes', '11999991117', 'gabriel@email.com'),
('111.111.111-08', 'Heloísa Dias', '11999991118', 'heloisa@email.com'),
('111.111.111-09', 'Igor Rocha', '11999991119', 'igor@email.com'),
('111.111.111-10', 'Juliana Castro', '11999991120', 'juliana@email.com'),
('222.222.222-01', 'Kleber Santos', '11988882221', 'kleber.func@email.com'),
('222.222.222-02', 'Larissa Mendes', '11988882222', 'larissa.func@email.com'),
('222.222.222-03', 'Marcos Paulo', '11988882223', 'marcos.func@email.com'),
('222.222.222-04', 'Natália Pires', '11988882224', 'natalia.func@email.com'),
('222.222.222-05', 'Otávio Nunes', '11988882225', 'otavio.func@email.com'),
('222.222.222-06', 'Patrícia Moura', '11988882226', 'patricia.func@email.com'),
('222.222.222-07', 'Quintino Faria', '11988882227', 'quintino.func@email.com'),
('222.222.222-08', 'Renata Vieira', '11988882228', 'renata.func@email.com'),
('222.222.222-09', 'Sérgio Ramos', '11988882229', 'sergio.func@email.com'),
('222.222.222-10', 'Tatiane Moraes', '11988882230', 'tatiane.func@email.com'),
('333.333.333-01', 'Ubirajara Silva', '11977773331', 'ubirajara.ent@email.com'),
('333.333.333-02', 'Valdir Mota', '11977773332', 'valdir.ent@email.com'),
('333.333.333-03', 'Wagner Lopes', '11977773333', 'wagner.ent@email.com'),
('333.333.333-04', 'Xuxa Meneghel', '11977773334', 'xuxa.ent@email.com'),
('333.333.333-05', 'Yuri Martins', '11977773335', 'yuri.ent@email.com'),
('333.333.333-06', 'Zeca Pagodinho', '11977773336', 'zeca.ent@email.com'),
('333.333.333-07', 'Arthur Aguiar', '11977773337', 'arthur.ent@email.com'),
('333.333.333-08', 'Bárbara Borges', '11977773338', 'barbara.ent@email.com'),
('333.333.333-09', 'Cauã Reymond', '11977773339', 'caua.ent@email.com'),
('333.333.333-10', 'Dira Paes', '11977773340', 'dira.ent@email.com');

-- Inserindo 10 Categorias
INSERT INTO Categoria (nome) VALUES
('Creatina'),
('Whey'),
('Pré-treino'),
('Energético'),
('Diurético'),
('Barrinha proteica'),
('Carb Up'),
('Cafeína'),
('Aminoácidos'),
('Termogênico');

-- Inserindo 10 Clientes (Pessoas de 1 a 10)
INSERT INTO Cliente (codigo_pessoa, status) VALUES
(1, 'Ativo'),
(2, 'Ativo'),
(3, 'Ativo'),
(4, 'Inativo'),
(5, 'Ativo'),
(6, 'Ativo'),
(7, 'Ativo'),
(8, 'Inativo'),
(9, 'Ativo'),
(10, 'Ativo');

-- Inserindo 10 Funcionários (Pessoas de 11 a 20)
INSERT INTO Funcionario (codigo_pessoa, salario) VALUES
(11, 2500.00),
(12, 2800.50),
(13, 3100.00),
(14, 1900.00),
(15, 2100.00),
(16, 4500.00),
(17, 2300.00),
(18, 3000.00),
(19, 2750.00),
(20, 2200.00);

-- Inserindo 10 Entregadores (Pessoas de 21 a 30)
INSERT INTO Entregador (codigo_pessoa, obs) VALUES
(21, 'Moto Honda CG 160'),
(22, 'Moto Yamaha YBR'),
(23, 'Bicicleta Elétrica'),
(24, 'Carro Fiat Uno'),
(25, 'Moto Honda Biz'),
(26, 'Moto Yamaha NMAX'),
(27, 'Bicicleta Convencional'),
(28, 'Moto Honda Bros'),
(29, 'Patinete Elétrico'),
(30, 'Carro VW Gol');

-- Inserindo 10 Endereços para os clientes (Clientes 1 a 10)
INSERT INTO Endereco (cep, cidade, estado, status, codigo_cliente) VALUES
('01000-000', 'São Paulo', 'SP', 'Principal', 1),
('02000-000', 'São Paulo', 'SP', 'Principal', 2),
('03000-000', 'Campinas', 'SP', 'Principal', 3),
('04000-000', 'Bauru', 'SP', 'Secundário', 4),
('05000-000', 'Ribeirão Preto', 'SP', 'Principal', 5),
('06000-000', 'Osasco', 'SP', 'Principal', 6),
('07000-000', 'Guarulhos', 'SP', 'Principal', 7),
('08000-000', 'São José dos Campos', 'SP', 'Principal', 8),
('09000-000', 'Santo André', 'SP', 'Secundário', 9),
('10000-000', 'São Bernardo do Campo', 'SP', 'Principal', 10);

-- Inserindo 10 Produtos
INSERT INTO Produto (descricao, estoque, status, preco, codigo_categoria) VALUES
('Creatina Max Titanium', 50, 'Disponível', 45.90, 1),
('Whey Shark Pro', 100, 'Disponível', 25.50, 2),
('Monster Zero', 200, 'Disponível', 9.00, 3),
('Pré-treino Diabo Verde', 150, 'Disponível', 12.00, 4),
('Cafeína', 30, 'Disponível', 15.00, 5),
('X Diu', 80, 'Disponível', 22.90, 6),
('X Fire', 40, 'Disponível', 28.00, 7),
('Emagrecedor Fogo no Cachorro', 20, 'Disponível', 65.00, 8),
('Termogênico Efedrax', 60, 'Disponível', 20.00, 9),
('Whey Toddy Best Whey', 35, 'Disponível', 29.90, 10);

-- Inserindo 10 Pedidos (Associando clientes de 1-10 e funcionários de 11-20)
INSERT INTO Pedido (data_hora, status, total, codigo_cliente, codigo_funcionario) VALUES
('2023-10-01 19:30:00', 'Concluído', 57.90, 1, 11),
('2023-10-02 20:00:00', 'Concluído', 25.50, 2, 12),
('2023-10-03 12:15:00', 'Concluído', 41.00, 3, 13),
('2023-10-04 18:45:00', 'Cancelado', 0.00, 4, 14),
('2023-10-05 21:10:00', 'Concluído', 80.00, 5, 15),
('2023-10-06 19:05:00', 'Em Andamento', 45.90, 6, 16),
('2023-10-07 13:20:00', 'Concluído', 28.00, 7, 17),
('2023-10-08 22:30:00', 'Concluído', 65.00, 8, 18),
('2023-10-09 20:50:00', 'Concluído', 42.90, 9, 19),
('2023-10-10 14:00:00', 'Em Andamento', 29.90, 10, 20);

-- Inserindo 10 Itens de Pedido (Associando os pedidos de 1-10 aos produtos criados)
INSERT INTO Item_Pedido (nr_pedido, codigo_produto, preco_unitario, qtd_vendida) VALUES
(1, 1, 45.90, 1),
(1, 4, 12.00, 1),
(2, 2, 25.50, 1),
(3, 7, 28.00, 1),
(3, 3, 13.00, 1),
(5, 8, 65.00, 1),
(5, 5, 15.00, 1),
(6, 1, 45.90, 1),
(8, 8, 65.00, 1),
(10, 10, 29.90, 1);

-- Inserindo 10 Entregas (Associando pedidos 1-10 aos entregadores 21-30)
INSERT INTO Entrega (valor_entrega, data_saida, status, nr_pedido, codigo_entregador) VALUES
(5.00, '2023-10-01 20:00:00', 'Entregue', 1, 21),
(7.50, '2023-10-02 20:30:00', 'Entregue', 2, 22),
(4.00, '2023-10-03 12:40:00', 'Entregue', 3, 23),
(6.00, NULL, 'Cancelada', 4, 24),
(10.00, '2023-10-05 21:50:00', 'Entregue', 5, 25),
(5.00, '2023-10-06 19:40:00', 'Em Rota', 6, 26),
(8.00, '2023-10-07 13:50:00', 'Entregue', 7, 27),
(12.00, '2023-10-08 23:10:00', 'Entregue', 8, 28),
(4.50, '2023-10-09 21:20:00', 'Entregue', 9, 29),
(7.00, '2023-10-10 14:30:00', 'Aguardando Coleta', 10, 30);
