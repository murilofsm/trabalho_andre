package persistencia;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Murilo
 */
public class codigos_criacao_banco {
    /*
    
    CREATE DATABASE db_faculdadefinal;

USE db_faculdadefinal;

CREATE TABLE `db_faculdadefinal`.`endereco` (
  `id` INT(12) NOT NULL AUTO_INCREMENT,
  `cidade` VARCHAR(45) NULL,
  `rua` VARCHAR(45) NULL,
  `numero` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `db_faculdadefinal`.`curso` (
  `id` INT(12) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL,
  `cargahoraria` INT(12) NULL,
  `qtdsemestres` INT(12) NULL,
  PRIMARY KEY (`id`));
  
  
  CREATE TABLE `db_faculdadefinal`.`aluno` (
  `id` INT(12) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL,
  `cpf` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `genero` VARCHAR(45) NULL,
  `datanascimento` DATE NULL,
  `ra` VARCHAR(45) NULL,
  `datamatricula` VARCHAR(45) NULL,
  `situacao` VARCHAR(45) NULL,
  `idcurso` INT(8) NULL,
  `idendereco` INT(8) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_endereco_idx` (`idendereco` ASC) VISIBLE,
  INDEX `fk_curso_idx` (`idcurso` ASC) VISIBLE,
  CONSTRAINT `fk_endereco`
    FOREIGN KEY (`idendereco`)
    REFERENCES `db_faculdadefinal`.`endereco` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_curso`
    FOREIGN KEY (`idcurso`)
    REFERENCES `db_faculdadefinal`.`curso` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `db_faculdadefinal`.`disciplina` (
  `id` INT(12) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL,
  `cargahoraria` INT(12) NULL,
  `semestre` INT(12) NULL,
  `idcurso` INT(12) NULL,
  PRIMARY KEY (`id`)
  INDEX `fk_cursoDisciplina_idx` (`idcurso` ASC) VISIBLE,
  CONSTRAINT `fk_cursoDisciplina`
    FOREIGN KEY (`idcurso`)
    REFERENCES `db_faculdadefinal`.`curso` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

);
    
CREATE TABLE `db_faculdadefinal`.`funcionario` (
  `id` INT(12) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL,
  `cpf` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `genero` VARCHAR(45) NULL,
  `datanascimento` DATE NULL,
  `ctps` VARCHAR(45) NULL,
  `salario` INT(8) NULL,
  `idendereco` INT(8) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_enderecoDocente_idx` (`idendereco` ASC) VISIBLE,
  CONSTRAINT `fk_enderecoDocente`
    FOREIGN KEY (`idendereco`)
    REFERENCES `db_faculdadefinal`.`endereco` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `db_faculdadefinal`.`docente` (
  `id` INT(12) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL,
  `cpf` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `genero` VARCHAR(45) NULL,
  `datanascimento` DATE NULL,
  `ctps` VARCHAR(45) NULL,
  `formacao` VARCHAR(45) NULL,
  `salario` INT(8) NULL,
  `idendereco` INT(8) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_enderecoDocente_idx` (`idendereco` ASC) VISIBLE,
  CONSTRAINT `fk_enderecoDocente`
    FOREIGN KEY (`idendereco`)
    REFERENCES `db_faculdadefinal`.`endereco` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    */
}
