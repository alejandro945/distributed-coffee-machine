# Oracle SQL script for creating the tables required for the Coffee Machine system

create table "P09713_1_2".receta (idreceta number(10) primary key, nombre varchar2(300) not null, precio number(20,5) not null);

create table "P09713_1_2".ingrediente (idingrediente number(10) primary key, nombre varchar2(300) not null);

create table "P09713_1_2".receta_ingrediente (idreceta number(10) not null, idingrediente number(10) not null, unidades number(10) not null, constraint fk_receta_ing_recetas foreign key (idreceta) references P09713_1_2.receta (idreceta), constraint fk_receta_ing_ingre foreign key (idingrediente) references P09713_1_2.ingrediente (idingrediente));

create table "P09713_1_2".ventas (consecutivo number(10) primary key, idmaquina number(10) not null, valor number(20,5) not null, fecha_inicial date not null, fecha_final date not null);

create table "P09713_1_2".ventas_receta (consecutivo number(10) primary key, id_receta number(10) not null, consecutivo_ventas number(10) not null, constraint fk_ven_rece_recetas foreign key (id_receta) references P09713_1_2.receta (idreceta), constraint fk_ven_rece_ventas foreign key (consecutivo_ventas) references P09713_1_2.ventas (consecutivo));

create table "P09713_1_2".alarma (idalarma number(10) primary key, nombre varchar2(300) not null);

create table "P09713_1_2".maquina (idmaquina number(10) primary key, ubicacion varchar2(300) not null);

create table "P09713_1_2".alarma_maquina (id_alarma number(10) not null,id_maquina number(10) not null,fecha_inicial date not null, fecha_final date, consecutivo number(10) primary key, constraint fk_alar_maq_alarma foreign key (id_alarma) references P09713_1_2.alarma (idalarma), constraint fk_alar_maq_maquina foreign key (id_maquina) references P09713_1_2.maquina (idmaquina));

create table "P09713_1_2".operadores (idoperador number(10) primary key,nombre varchar2(300) not null, correo varchar2(300) not null, contrasena varchar2(300) not null);

create table "P09713_1_2".asignacion_maquina (id_operador number(10) not null, id_maquina number(10) not null, constraint fk_asig_maq_operador foreign key (id_operador) references P09713_1_2.operadores (idoperador), constraint fk_asig_maq_maquina foreign key (id_maquina) references P09713_1_2.maquina (idmaquina));

create sequence "P09713_1_2".consecalarma minvalue 1 maxvalue 999999999999999999999999999 start with 13 increment by 1 nocache nocycle;

create sequence "P09713_1_2".consecutivo minvalue 1 maxvalue 999999999999999999999999999 start with 20 increment by 1 nocache nocycle;

create sequence "P09713_1_2".consecutivo1 minvalue 1 maxvalue 999999999999999999999999999 start with 20 increment by 1 nocache nocycle;

create sequence "P09713_1_2".seq_ingredientes minvalue 1 maxvalue 999999999999999999999999999 start with 5 increment by 1 nocache nocycle;

create sequence "P09713_1_2".seq_alarmas minvalue 1 maxvalue 999999999999999999999999999 start with 13 increment by 1 nocache nocycle;
