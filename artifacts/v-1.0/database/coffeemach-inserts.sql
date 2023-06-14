/*Clean Data (Sin Where)*/
delete from ingrediente;
delete from receta;
delete from receta_ingrediente;
delete from alarma;
delete from maquina;
delete from alarma_maquina;
delete from operadores;
delete from asignacion_maquina;
delete from monedas;
delete from ingredientesInv;
delete from suministros;
delete from kit_reparacion;

/*Ingredientes*/
insert into ingrediente (idingrediente,nombre) values (1,'Agua');
insert into ingrediente (idingrediente,nombre) values (2,'Cafe');
insert into ingrediente (idingrediente,nombre) values (3,'Azucar');
insert into ingrediente (idingrediente,nombre) values (4,'Vaso');

/*Recetas*/
insert into receta (idreceta,nombre,precio) values (1,'Tinto',800);
insert into receta (idreceta,nombre,precio) values (2,'Tinto Cargado', 1000);

/*Receta Ingrediente*/
insert into receta_ingrediente (idreceta,idingrediente,unidades) values (1,1,100);
insert into receta_ingrediente (idreceta,idingrediente,unidades) values (1,2,10);
insert into receta_ingrediente (idreceta,idingrediente,unidades) values (1,3,10);
insert into receta_ingrediente (idreceta,idingrediente,unidades) values (1,4,1);
insert into receta_ingrediente (idreceta,idingrediente,unidades) values (2,1,100);
insert into receta_ingrediente (idreceta,idingrediente,unidades) values (2,2,30);
insert into receta_ingrediente (idreceta,idingrediente,unidades) values (2,3,10);
insert into receta_ingrediente (idreceta,idingrediente,unidades) values (2,4,1);

/*Alarmas*/
insert into alarma (idalarma,nombre) values (1,'alarma1');
insert into alarma (idalarma,nombre) values (2,'alarma2');
insert into alarma (idalarma,nombre) values (3,'alarma3');
insert into alarma (idalarma,nombre) values (4,'alarma4');
insert into alarma (idalarma,nombre) values (5,'alarma5');
insert into alarma (idalarma,nombre) values (6,'alarma6');
insert into alarma (idalarma,nombre) values (7,'alarma7');
insert into alarma (idalarma,nombre) values (8,'alarma8');
insert into alarma (idalarma,nombre) values (9,'alarma9');
insert into alarma (idalarma,nombre) values (10,'alarma10');
insert into alarma (idalarma,nombre) values (11,'alarma11');
insert into alarma (idalarma,nombre) values (12,'alarma12');

/*Máquinas*/
-- Crea una función que inserte las máquinas
CREATE OR REPLACE FUNCTION insertar_maquinas()
RETURNS VOID AS $$
DECLARE
    contador INTEGER := 1;
BEGIN
    FOR contador IN 1..200 LOOP
        -- Genera la sentencia INSERT
        EXECUTE 'INSERT INTO maquina (idmaquina, ubicacion) VALUES (' || contador || ', ''Ubicacion ' || contador || ''');';
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Llama a la función para insertar las máquinas
SELECT insertar_maquinas();

/*Alarma Máquina
insert into alarma_maquina (id_alarma,id_maquina,fecha_inicial,fecha_final,consecutivo) values (2,5,now(),null,2);
insert into alarma_maquina (id_alarma,id_maquina,fecha_inicial,fecha_final,consecutivo) values (3,1,now(),null,3);
insert into alarma_maquina (id_alarma,id_maquina,fecha_inicial,fecha_final,consecutivo) values (4,3,now(),null,4);
*/

/*Operadores*/
insert into operadores (idoperador,nombre,correo,contrasena) values (1,'Miguel Angel','test@gmail.com','1123');
insert into operadores (idoperador,nombre,correo,contrasena) values (2,'Donatello','test1@gmail.com','2123');
insert into operadores (idoperador,nombre,correo,contrasena) values (3,'Raffaello','test2@gmail.com','3123');
insert into operadores (idoperador,nombre,correo,contrasena) values (4,'Leonardo','test3@gmail.com','4123');

/*Asignacion Máquina*/
insert into asignacion_maquina (id_operador,id_maquina) values (1,1);
insert into asignacion_maquina (id_operador,id_maquina) values (1,2);
insert into asignacion_maquina (id_operador,id_maquina) values (1,3);
insert into asignacion_maquina (id_operador,id_maquina) values (2,4);
insert into asignacion_maquina (id_operador,id_maquina) values (2,5);
insert into asignacion_maquina (id_operador,id_maquina) values (2,6);
insert into asignacion_maquina (id_operador,id_maquina) values (3,7);
insert into asignacion_maquina (id_operador,id_maquina) values (4,8);

/*Monedas*/
insert into monedas (idmoneda,nombre,cantidad) values (1,'Moneda 100',500);
insert into monedas (idmoneda,nombre,cantidad) values (2,'Moneda 200',500);
insert into monedas (idmoneda,nombre,cantidad) values (3,'Moneda 500',500);

/*Ingredientes Inventario*/
insert into ingredientesInv (idingrediente,cantidad) values (1,1000);
insert into ingredientesInv (idingrediente,cantidad) values (2,1000);
insert into ingredientesInv (idingrediente,cantidad) values (3,1000);
insert into ingredientesInv (idingrediente,cantidad) values (4,1000);

/*Suministros*/
insert into suministros (idsuministro,nombre,cantidad) values (1,'Pitillos',1000);
insert into suministros (idsuministro,nombre,cantidad) values (2,'Vasos',1000);
insert into suministros (idsuministro,nombre,cantidad) values (3,'Tapas',1000);

/*Kit Reparación*/
insert into kit_reparacion (idkit,nombre,cantidad) values (1,'Kit 1',20);
insert into kit_reparacion (idkit,nombre,cantidad) values (2,'Kit 2',20);
insert into kit_reparacion (idkit,nombre,cantidad) values (3,'Kit 3',20);

commit;
