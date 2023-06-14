--# Oracle SQL script for creating the tables required for the Coffee Machine system
DROP TABLE IF EXISTS receta CASCADE;
DROP TABLE IF EXISTS ingrediente CASCADE;
DROP TABLE IF EXISTS receta_ingrediente CASCADE;
DROP TABLE IF EXISTS ventas CASCADE;
DROP TABLE IF EXISTS ventas_receta CASCADE;
DROP TABLE IF EXISTS alarma CASCADE;
DROP TABLE IF EXISTS maquina CASCADE;
DROP TABLE IF EXISTS alarma_maquina CASCADE;
DROP TABLE IF EXISTS operadores CASCADE;
DROP TABLE IF EXISTS asignacion_maquina CASCADE;
DROP sequence IF EXISTS consecalarma CASCADE;
DROP sequence IF EXISTS consecutivo CASCADE;
DROP sequence IF EXISTS consecutivo1 CASCADE;
DROP sequence IF EXISTS seq_ingredientes CASCADE;
DROP sequence IF EXISTS seq_alarmas CASCADE;
DROP sequence IF EXISTS seq_receta CASCADE;
DROP sequence IF EXISTS seq_orden_entrega CASCADE;
DROP sequence IF EXISTS seq_orden_trabajo CASCADE;
DROP TABLE IF EXISTS monedas CASCADE;
DROP TABLE IF EXISTS ingredientesInv CASCADE;
DROP TABLE IF EXISTS suministros CASCADE;
DROP TABLE IF EXISTS kit_reparacion CASCADE;
DROP TABLE IF EXISTS ordenes_entrega CASCADE;
DROP TABLE IF EXISTS ordenes_trabajo CASCADE;


create table receta (idreceta integer primary key, nombre varchar(300) not null, precio numeric(20,5) not null);

create table ingrediente (idingrediente integer primary key, nombre varchar(300) not null);

create table receta_ingrediente (idreceta integer not null, idingrediente integer not null, unidades integer not null,foreign key (idreceta) references receta (idreceta),foreign key (idingrediente) references ingrediente (idingrediente));

create table ventas (consecutivo integer primary key, idmaquina integer not null, valor numeric(20,5) not null, fecha_inicial date not null, fecha_final date not null);

create table ventas_receta (consecutivo integer primary key, id_receta integer not null, consecutivo_ventas integer not null,foreign key (id_receta) references receta (idreceta),foreign key (consecutivo_ventas) references ventas (consecutivo));

create table alarma (idalarma integer primary key, nombre varchar(300) not null);

create table maquina (idmaquina integer primary key, ubicacion varchar(300) not null);

create table alarma_maquina (id_alarma integer not null,id_maquina integer not null,fecha_inicial date not null, fecha_final date, consecutivo integer primary key,foreign key (id_alarma) references alarma (idalarma),foreign key (id_maquina) references maquina (idmaquina));

create table operadores (idoperador integer primary key,nombre varchar(300) not null, correo varchar(300) not null, contrasena varchar(300) not null);

create table asignacion_maquina (id_operador integer not null, id_maquina integer not null,foreign key (id_operador) references operadores (idoperador), foreign key (id_maquina) references maquina (idmaquina));

create sequence consecalarma minvalue 1 start with 13 increment by 1 no cycle;

create sequence consecutivo minvalue 1 start with 20 increment by 1 no cycle;

create sequence consecutivo1 minvalue 1 start with 20 increment by 1 no cycle;

create sequence seq_ingredientes minvalue 1 start with 5 increment by 1 no cycle;

create sequence seq_alarmas minvalue 1 start with 13 increment by 1 no cycle;

create sequence seq_receta minvalue 1 start with 13 increment by 1 no cycle;

create sequence seq_orden_entrega minvalue 1 start with 1 increment by 1 no cycle;

create sequence seq_orden_trabajo minvalue 1 start with 1 increment by 1 no cycle;


create table monedas (idmoneda integer primary key, nombre varchar(300) not null, cantidad numeric(20,5) not null);

create table ingredientesInv (idingrediente integer primary key, cantidad numeric(20,5) not null, foreign key (idingrediente) references ingrediente (idingrediente));

create table suministros (idsuministro integer primary key, nombre varchar(300) not null, cantidad numeric(20,5) not null);

create table kit_reparacion (idkit integer primary key, nombre varchar(300) not null, cantidad numeric(20,5) not null);

create table ordenes_entrega (idOperador integer, idorden integer primary key, idmaquina integer, idkit integer, fecha date not null, cantidad integer, idsuministro integer, idingrediente integer, idAlarma integer, foreign key (idmaquina) references maquina (idmaquina), foreign key (idOperador) references operadores (idoperador), foreign key(idAlarma) references alarma_maquina (consecutivo));

create table ordenes_trabajo (idAlarma integer, idOperador integer, idorden integer primary key, idmaquina integer, fecha date not null, idorden_entrega integer, foreign key (idmaquina) references maquina (idmaquina), foreign key (idorden_entrega) references ordenes_entrega (idorden), foreign key (idOperador) references operadores (idoperador), foreign key(idAlarma) references alarma_maquina (consecutivo));