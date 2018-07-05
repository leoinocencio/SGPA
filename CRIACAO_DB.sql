CREATE DATABASE `sgpa` /*!40110 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE sgpa.tipo_pessoa (
    id_tipo_pessoa int NOT NULL AUTO_INCREMENT,
    descricao varchar(255) NOT NULL,
    PRIMARY KEY (id_tipo_pessoa)
);

CREATE TABLE sgpa.pessoa (
    id int NOT NULL AUTO_INCREMENT,
    nome varchar(255) NOT NULL,
    cpf varchar(11),
	rg varchar(9),
    n_oab varchar(50),
    n_carteira_trabalho varchar(50),
    login varchar(255) NOT NULL,
    senha varchar(110) NOT NULL,
	ds_comentario varchar (4000),
    id_tipo_pessoa int,
    PRIMARY KEY (id),
    CONSTRAINT FK_TipoPessoaPessoa FOREIGN KEY (id_tipo_pessoa)
      REFERENCES sgpa.tipo_pessoa(id_tipo_pessoa)
      ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE sgpa.documento (
    id_documento int NOT NULL AUTO_INCREMENT,
    dsc_documento varchar(255) NOT NULL,
    arquivo_virtual MEDIUMBLOB,
    tipo_documento varchar(6),
    PRIMARY KEY (id_documento)
);

CREATE TABLE sgpa.processo (
    id_processo int NOT NULL AUTO_INCREMENT,
    numero_processo varchar(255) NOT NULL,
    tp_processo varchar(255),
    st_processo varchar(255),
    valor_indenizacao DECIMAL,
    id_cliente int NOT NULL,
	id_advogado int NOT NULL,
    id_documento int NOT NULL,
    dt_inicio date,
    dt_fim date,
    PRIMARY KEY (id_processo),
    CONSTRAINT FK_ProcessoCli FOREIGN KEY (id_cliente) REFERENCES sgpa.pessoa (id),
	CONSTRAINT FK_ProcessoAdv FOREIGN KEY (id_advogado) REFERENCES sgpa.pessoa (id),
    CONSTRAINT FK_ProcessoDoc FOREIGN KEY (id_documento) REFERENCES sgpa.documento (id_documento)
);

CREATE TABLE sgpa.pessoa_notificacao (
    id int NOT NULL AUTO_INCREMENT,
	id_remetente int NOT NULL,
	id_receptor int NOT NULL,
    id_processo int NOT NULL,
    descricao varchar(4000) NOT NULL,
    PRIMARY KEY (id),
	CONSTRAINT FK_PessoaNotRemet FOREIGN KEY (id_remetente) REFERENCES sgpa.pessoa (id),
	CONSTRAINT FK_PessoaNotRecep FOREIGN KEY (id_receptor) REFERENCES sgpa.pessoa (id),
    CONSTRAINT FK_PessoaNotProc FOREIGN KEY (id_processo) REFERENCES sgpa.processo (id_processo)
);

CREATE TABLE sgpa.pagamento (
    id_pagamento int NOT NULL AUTO_INCREMENT,
	id_processo int NOT NULL,
	vl_pagamento DECIMAL,
	dt_pagamento date,
    numero_pagamento varchar(110),
    id_documento int NOT NULL, 
    PRIMARY KEY (id_pagamento),
	CONSTRAINT FK_PagamentoProc FOREIGN KEY (id_processo) REFERENCES sgpa.processo (id_processo),
    CONSTRAINT FK_PagamentoDoc FOREIGN KEY (id_documento) REFERENCES sgpa.documento (id_documento)
);

CREATE TABLE sgpa.contrato (
    id int NOT NULL AUTO_INCREMENT,
    id_processo int NOT NULL,
	id_documento int NOT NULL,
    PRIMARY KEY (id),
	CONSTRAINT FK_ContratoProc FOREIGN KEY (id_processo) REFERENCES sgpa.processo (id_processo),
	CONSTRAINT FK_ContratoDoc FOREIGN KEY (id_documento) REFERENCES sgpa.documento (id_documento)
);

#ALTER TABLE sgpa.processo
#ADD dt_inicio date;

#ALTER TABLE sgpa.processo
#ADD dt_fim date;

INSERT INTO `sgpa`.`tipo_pessoa` (`descricao`) VALUES ('ADVOGADO');
INSERT INTO `sgpa`.`tipo_pessoa` (`descricao`) VALUES ('CLIENTE');
INSERT INTO `sgpa`.`tipo_pessoa` (`descricao`) VALUES ('ADMINISTRADOR');
INSERT INTO `sgpa`.`tipo_pessoa` (`descricao`) VALUES ('FUNCIONÁRIO');


INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('ADMINISTRADOR','07671368458','3765882',null,null,'ADMINISTRADOR','21232F297A57A5A743894A0E4A801FC3','TESTE COMENTARIO',3);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('OSMAR OLIVEIRA','07671368451','3765882','5326323','000000001','OSMAR','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',1);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('JOSE MARIA','07671348458','3765882','32156047','000000002','JOSE','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',1);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('MARIA JOSE','01671368458','3765882','75365211','000000003','MARIA','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',1);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('JOAO PEDRO','07631362458','3765882','76543211','000000004','JOAO','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',1);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('LUCAS SILVA','11111111111','3765882',null,'000000005','LUCAS','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('ANDRE SANTOS','01798765423','3765882',null,'000000006','ANDRE','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('ROBERTO SOUSA','07971398458','3765882',null,'000000007','ROBERTO','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('GUILHERME SILVA','17671468458','3765882',null,'000000008','GUILHERME','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('PEDRO VASCONCELOS','27271368358','3765882',null,'000000009','PEDRO','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('BRUNO MONTENEGRO','44444444444','3765882',null,'000000011','BRUNO','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('EDUARDO MUNIZ','55555555555','3765882',null,'000000011','EDUARDO','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('FULANO MAZZAROPI','66666666666','3765882',null,'000000012','FULANO','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('CICLANO MULLER','77777777777','3765882',null,'000000013','CICLANO','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('ZE MARQUES','88888888888','3765882',null,'000000014','ZE','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('JOSEPH MORAES','99999999999','3765882',null,'0000000015','JOSEPH','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('LUANA ANDRADE','73689765201','3765882',null,null,'LUANA','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('AMANDA LINS','98463711987','3765882',null,null,'AMANDA','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('BRUNA SANTANA','27364912837','3765882',null,null,'BRUNA','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('PAULA RIBEIRO','87463920987','3765882',null,null,'PAULA','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('JOANA VILELA','12367898765','3765882',null,null,'JOANA','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('RODRIGO GOMES','82739485712','3765882',null,null,'RODRIGO','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);
INSERT INTO `sgpa`.`pessoa` (`nome`,`cpf`,`rg`,`n_oab`,`n_carteira_trabalho`,`login`,`senha`,`ds_comentario`,`id_tipo_pessoa`)
VALUES ('LEONARDO RORIZ','99876567890','3765882',null,null,'LEONARDO','202CB962AC59075B964B07152D234B70','TESTE COMENTARIO',2);


INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('002','TRABALHISTA','PERDIDO',11000, 7, 3, 1, '2017-01-19', '2017-11-01');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('003','CIVEL','JULGADO',11000, 8, 3, 1, '2016-03-12', '2017-03-15');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('004','PENAL','EM JULGAMENTO',11000, 9, 3, 1, '2018-01-12', null);
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('005','TRABALHISTA','JULGADO',20, 11, 3, 1, '2017-01-20', '2018-01-27');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('006','PENAL','JULGADO',200, 11, 2, 1, '2017-01-20', '2018-03-28');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('007','TRIBUTARIO','PERDIDO',3000, 12, 2, 1, '2017-03-13', '2018-01-15');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('008','TRIBUTARIO','PERDIDO',4000, 13, 2, 1, '2017-02-21', '2018-01-19');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('009','PENAL','JULGADO',1200, 14, 2, 1, '2017-04-27', '2018-01-05');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('011','CIVEL','JULGADO',1800, 9, 5, 1, '2017-05-11', '2018-01-09');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('011','CIVEL','JULGADO',28390, 9, 5, 1, '2017-06-17', '2018-01-08');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('012','CIVEL','JULGADO',1450, 9, 5, 1, '2017-08-18', '2018-01-04');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('013','CIVEL','PERDIDO',1900, 8, 5, 1, '2017-09-12', '2018-01-01');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('014','TRIBUTARIO','EM JULGAMENTO',8000, 8, 5, 1, '2017-01-21', null);
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('015','TRIBUTARIO','PERDIDO',6000, 8, 4, 1, '2017-11-22', '2018-01-02');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('016','TRABALHISTA','EM JULGAMENTO',5000, 6, 4, 1, '2017-03-20', null);
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('017','TRABALHISTA','PERDIDO',3000, 6, 4, 1, '2017-04-15', '2018-03-11');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('018','TRIBUTARIO','PERDIDO',40000, 6, 3, 1, '2017-05-16', '2017-12-13');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('019','CIVEL','JULGADO',20000, 7, 3, 1, '2016-07-18', '2017-01-15');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('020','CIVEL','JULGADO',30000, 7, 3, 1, '2016-02-19', '2017-02-16');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('021','PENAL','JULGADO',11000, 6, 3, 1, '2016-01-20', '2017-03-17');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('022','TRABALHISTA','PERDIDO',11000, 7, 4, 1, '2017-01-19', '2017-11-01');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('023','CIVEL','JULGADO',11000, 8, 4, 1, '2016-03-12', '2017-03-15');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('024','PENAL','EM JULGAMENTO',11000, 9, 4, 1, '2018-01-12', null);
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('025','TRABALHISTA','JULGADO',20, 11, 4, 1, '2017-01-20', '2018-01-27');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('026','PENAL','JULGADO',200, 11, 5, 1, '2017-01-20', '2018-03-28');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('027','TRIBUTARIO','PERDIDO',3000, 12, 5, 1, '2017-03-13', '2018-01-15');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('028','TRIBUTARIO','PERDIDO',4000, 13, 5, 1, '2017-02-21', '2018-01-19');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('029','PENAL','JULGADO',1200, 14, 5, 1, '2017-04-27', '2018-01-05');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('030','CIVEL','JULGADO',1800, 9, 4, 1, '2017-05-11', '2018-01-09');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('031','CIVEL','JULGADO',28390, 9, 4, 1, '2017-06-17', '2018-01-08');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('032','CIVEL','JULGADO',1450, 9, 2, 1, '2017-08-18', '2018-01-04');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('033','CIVEL','PERDIDO',1900, 8, 2, 1, '2017-09-12', '2018-01-01');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('034','TRIBUTARIO','EM JULGAMENTO',8000, 8, 2, 1, '2017-01-21', null);
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('035','TRIBUTARIO','PERDIDO',6000, 8, 2, 1, '2017-11-22', '2018-01-02');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('036','TRABALHISTA','EM JULGAMENTO',5000, 6, 2, 1, '2017-03-20', null);
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('037','TRABALHISTA','PERDIDO',3000, 6, 2, 1, '2017-04-15', '2018-03-11');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('038','TRIBUTARIO','PERDIDO',40000, 6, 3, 1, '2017-05-16', '2017-12-13');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('039','CIVEL','JULGADO',20000, 7, 3, 1, '2016-07-18', '2017-01-15');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('040','CIVEL','JULGADO',30000, 7, 4, 1, '2016-02-19', '2017-02-16');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('041','PENAL','JULGADO',11000, 6, 4, 1, '2016-01-20', '2017-03-17');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('042','CIVEL','JULGADO',1450, 9, 2, 1, '2017-08-18', '2018-01-04');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('043','CIVEL','PERDIDO',1900, 8, 2, 1, '2017-09-12', '2018-01-01');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('044','TRIBUTARIO','EM JULGAMENTO',8000, 8, 2, 1, '2017-01-21', null);
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('045','TRIBUTARIO','PERDIDO',6000, 8, 2, 1, '2015-11-22', '2018-01-02');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('046','TRABALHISTA','EM JULGAMENTO',5000, 6, 2, 1, '2015-03-20', null);
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('047','TRABALHISTA','PERDIDO',3000, 6, 2, 1, '2014-04-15', '2018-03-11');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('048','TRIBUTARIO','PERDIDO',40000, 6, 3, 1, '2015-05-16', '2017-12-13');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('049','CIVEL','JULGADO',20000, 7, 3, 1, '2015-07-18', '2017-01-15');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('050','CIVEL','JULGADO',30000, 7, 4, 1, '2016-02-19', '2017-02-16');
INSERT INTO `sgpa`.`processo`
(`numero_processo`,`tp_processo`,`st_processo`,`valor_indenizacao`,`id_cliente`,`id_advogado`,`id_documento`,`dt_inicio`,`dt_fim`) VALUES
('051','PENAL','JULGADO',11000, 6, 4, 1, '2016-01-20', '2017-03-17');


INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(1, 5000, '2017-11-01', 001, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(1, 11000, '2017-11-02', 002, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(2, 11000, '2017-11-03', 003, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(3, 11000, '2017-11-04', 004, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(4, 11000, '2017-11-05', 005, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(5, 11000, '2017-11-06', 006, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(6, 11000, '2017-11-07', 007, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(7, 1100, '2017-11-08', 008, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(8, 1100, '2017-11-09', 009, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(9, 11000, '2017-11-11', 011, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(11, 1100, '2017-11-11', 011, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(11, 110, '2017-11-12', 012, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(12, 1100, '2017-11-13', 013, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(13, 14000, '2017-11-14', 014, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(14, 14000, '2017-11-15', 015, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(15, 12000, '2017-11-16', 016, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(15, 11100, '2017-11-17', 017, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(16, 11000, '2017-11-18', 018, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(17, 11000, '2017-11-19', 019, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(18, 11000, '2017-11-20', 020, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(19, 11000, '2017-11-21', 021, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(20, 11000, '2017-11-22', 022, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(1, 700, '2017-11-23', 023, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(1, 200, '2017-11-24', 024, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(21, 5000, '2017-11-01', 025, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(22, 11000, '2017-11-02', 026, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(23, 11000, '2017-11-03', 027, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(24, 11000, '2017-11-04', 028, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(25, 11000, '2017-11-05', 029, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(26, 11000, '2017-11-06', 030, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(27, 11000, '2017-11-07', 031, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(27, 1100, '2017-11-08', 032, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(28, 1100, '2017-11-09', 033, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(29, 11000, '2017-11-11', 034, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(30, 1100, '2017-11-11', 035, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(31, 110, '2017-11-12', 036, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(32, 1100, '2017-11-13', 037, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(33, 14000, '2017-11-14', 038, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(34, 14000, '2017-11-15', 039, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(35, 12000, '2017-11-16', 040, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(35, 11100, '2017-11-17', 041, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(36, 11000, '2017-11-18', 042, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(37, 11000, '2017-11-19', 043, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(38, 11000, '2017-11-20', 044, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(39, 11000, '2017-11-21', 045, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(40, 11000, '2017-11-22', 046, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(41, 700, '2017-11-23', 047, 2);
INSERT INTO `sgpa`.`pagamento`
(`id_processo`,`vl_pagamento`,`dt_pagamento`,`numero_pagamento`,`id_documento`) VALUES
(42, 200, '2017-11-24', 048, 2);