#!/bin/bash

export PASSWORD="swarch"
maquinas="swarch@xhgrid"
path="ci-cd-coffeemachine"
cfg="config"

gradle build

routeServidor="./remoteAdministration/build/"
routeCoffeeMach="./coffeeMachineSubsystem/build/"
routeProxy="./proxyCache/build/"

echo "Comprimiendo librerias"

cd "$routeServidor" && zip -r libs.zip libs
cd "../.."
cd "$routeCoffeeMach" && zip -r libs.zip libs
cd "../.."
cd "$routeProxy" && zip -r libs.zip libs
cd "../.."

echo "send libs to server"
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${routeServidor}/libs.zip ${maquinas}3:./$path
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${routeProxy}/libs.zip ${maquinas}4:./$path
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${routeCoffeeMach}/libs.zip ${maquinas}11:./$path

echo "Unzip libs in the machines"
sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}3 "cd ci-cd-coffeemachine && rm -r libs && unzip libs.zip"
sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}4 "cd ci-cd-coffeemachine && rm -r libs && unzip libs.zip"
sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}11 "cd ci-cd-coffeemachine && rm -r libs && unzip libs.zip"

echo "send config files to each process node"
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/server.cfg ${maquinas}3:./${path}/libs
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/proxy.cfg ${maquinas}4:./${path}/libs
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/coffeMach.cfg ${maquinas}11:./${path}/libs
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/codMaquina.cafe ${maquinas}11:./${path}/libs

