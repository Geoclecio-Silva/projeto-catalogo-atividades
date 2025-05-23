CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    perfil VARCHAR(20) NOT NULL
);

spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true