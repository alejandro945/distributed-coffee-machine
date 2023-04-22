Para la gestion del servidor postgres.

 - pg_ctl status
 - pg_ctl stop
 - pg_ctl -D /var/lib/pgsql/data -l pgstartup.log start &

Modigicar el archivo pg_hba.conf dentro de data, para dar permisos a la red.

Crear usuario y base de datos.

 - psql -h xhgrid2 -U postgres -c "\i scripts/postgres/coffeemach-user.sql"

Ejectur los scrits de creacion y poblacion al server.

 - psql -h xhgrid2 -U cofmachu -d coffeemachine -c "\i scripts/postgres/coffeemach-ddl.sql"
 - psql -h xhgrid2 -U cofmachu -d coffeemachine -c "\i scripts/postgres/coffeemach-inserts.sql"

Cambiar los datos de conexión en el servidor (ConexionDB)

1)Estando en src_postgres, compilar los proyectos: 	

./gradlew build


2) Correr los proyectos en el siguiente orden, si esta en sesión remota desde windows
use xming y Putty:

java -jar ServidorCentral/build/libs/ServidorCentral.jar
java -jar ClienteRecetas/build/libs/ClienteRecetas.jar
java -jar coffeeMach/build/libs/coffeeMach.jar
java -jar cmLogistics/build/libs/cmLogistics.jar