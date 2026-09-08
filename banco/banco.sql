CREATE TABLE competencias (
    id SERIAL PRIMARY KEY,
    competencia VARCHAR(15) NOT NULL
);

CREATE TABLE candidatos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    sobrenome VARCHAR(50) NOT NULL,
    nascimento DATE NOT NULL,
    email VARCHAR(50) NOT NULL,
    cpf VARCHAR(12) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    estado CHAR(2) NOT NULL,
    cidade VARCHAR(33) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    senha VARCHAR(50) NOT NULL CHECK (length(senha) >= 6)
);

CREATE TABLE empresa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    cnpj VARCHAR(15) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    estado CHAR(2) NOT NULL,
    cidade VARCHAR(33) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    senha VARCHAR(50) NOT NULL CHECK (length(senha) >= 6)
);

CREATE TABLE vagas (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(50) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    estado CHAR(2) NOT NULL,
    cidade VARCHAR(33) NOT NULL,
    id_empresa INT NOT NULL REFERENCES empresa(id)
);

CREATE TABLE candidato_competencia (
    id_candidatos INT NOT NULL REFERENCES candidatos(id),
    id_competencias INT NOT NULL REFERENCES competencias(id),
    PRIMARY KEY (id_candidatos, id_competencias)
);

CREATE TABLE vagas_competencias (
    id_vagas INT NOT NULL REFERENCES vagas(id),
    id_competencias INT NOT NULL REFERENCES competencias(id),
    PRIMARY KEY (id_vagas, id_competencias)
);

INSERT INTO competencias (competencia) VALUES
('Java'),
('Spring Boot'),
('PostgreSQL'),
('Angular'),
('TypeScript'),
('CSS'),
('Python'),
('SQL'),
('Airflow'),
('React'),
('Node.js'),
('MongoDB'),
('Docker'),
('Kubernetes');

INSERT INTO candidatos (nome, sobrenome, nascimento, email, cpf, descricao, pais, estado, cidade, cep, senha) VALUES
('Alan', 'Oliveira', '1994-03-15', 'alana@mail.com', '10403796542', 'Desenvolvedor Java', 'BRA', 'CE', 'Fortaleza', '60450-340', 'senha123'),
('Beatriz', 'Souza', '2004-07-22', 'beatriz@mail.com', '11122233344', 'Desenvolvedora frontend', 'BRA', 'SP', 'São Paulo', '01310-100', 'senha123'),
('Carlos', 'Mendes', '1991-01-10', 'carlos@mail.com', '22233344455', 'Engenheiro de dados', 'BRA', 'MG', 'Belo Horizonte', '30130-010', 'senha123'),
('Daniela', 'Ramos', '1999-11-05', 'daniela@mail.com', '33344455566', 'Desenvolvedora fullstack', 'BRA', 'BA', 'Salvador', '40010-000', 'senha123'),
('Eduardo', 'Lima', '1982-06-30', 'eduardo@mail.com', '44455566677', 'Analista de sistemas', 'BRA', 'DF', 'Brasília', '70040-010', 'senha123');

INSERT INTO empresa (nome, email, cnpj, descricao, pais, estado, cidade, cep, senha) VALUES
('Arroz Gostoso', 'contato@arrozgostoso.com', '12345678000199', 'Empresa do setor alimentício.', 'BRA', 'CE', 'Fortaleza', '60175-047', 'senha123'),
('Império do Boliche', 'rh@imperiodoboliche.com', '98765432000111', 'Rede de casas de entretenimento e lazer.', 'BRA', 'SP', 'São Paulo', '01452-000', 'senha123'),
('TechNova Soluções', 'contato@technova.com', '11223344000155', 'Consultoria em transformação digital.', 'BRA', 'MG', 'Belo Horizonte', '30140-071', 'senha123'),
('Boliche Champions', 'contato@bolichechampions.com', '22334455000166', 'Franquia de centros de boliche.', 'BRA', 'BA', 'Salvador', '40110-010', 'senha123'),
('DataFlow Analytics', 'rh@dataflow.com', '33445566000177', 'Empresa de análise de dados e BI.', 'BRA', 'DF', 'Brasília', '70070-100', 'senha123');

INSERT INTO vagas (titulo, descricao, estado, cidade, id_empresa) VALUES
('Desenvolvedor(a) Backend Java', 'Vaga para desenvolvimento de sistemas internos em Java e Spring.', 'CE', 'Fortaleza', 1),
('Desenvolvedor(a) Frontend Angular', 'Vaga para manutenção e evolução do sistema de reservas.', 'SP', 'São Paulo', 2),
('Engenheiro(a) de Dados Python', 'Vaga para construção de pipelines de dados em nuvem.', 'MG', 'Belo Horizonte', 3),
('Desenvolvedor(a) Fullstack React', 'Vaga para o time de produto digital.', 'BA', 'Salvador', 4),
('Desenvolvedor(a) Java Sênior', 'Vaga para arquitetura de sistemas distribuídos.', 'DF', 'Brasília', 5);

INSERT INTO candidato_competencia (id_candidatos, id_competencias) VALUES
(1, 1), (1, 2), (1, 3),
(2, 4), (2, 5), (2, 6),
(3, 7), (3, 8), (3, 9),
(4, 10), (4, 11), (4, 12),
(5, 1), (5, 13), (5, 14);

INSERT INTO vagas_competencias (id_vagas, id_competencias) VALUES
(1, 1), (1, 2), (1, 8),
(2, 4), (2, 11), (2, 12),
(3, 7), (3, 3), (3, 13),
(4, 10), (4, 12), (4, 6),
(5, 1), (5, 14), (5, 3);
