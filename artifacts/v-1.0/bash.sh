#!/bin/bash

export PASSWORD="swarch"
maquinas="swarch@xhgrid"
path="ci-cd-coffeemachine"
cfg="config"

echo "send libs to server"


sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no broker.zip ${maquinas}5:./$path
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no remoteAdministration.zip ${maquinas}3:./$path
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no proxyCache.zip ${maquinas}4:./$path
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no coffeeMachineSubsystem.zip ${maquinas}11:./$path

echo "Unzip libs in the machines"
sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}3 "cd ci-cd-coffeemachine; mkdir libs; rm -r libs; unzip remoteAdministration.zip"
sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}4 "cd ci-cd-coffeemachine; mkdir libs; rm -r libs; unzip proxyCache.zip"
sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}11 "cd ci-cd-coffeemachine; mkdir libs; rm -r libs; unzip coffeeMachineSubsystem.zip"
sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}5 "cd ci-cd-coffeemachine; mkdir libs; rm -r libs; unzip broker.zip"


echo "send config files to each process node"
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/server.cfg ${maquinas}3:./${path}/libs
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/proxy.cfg ${maquinas}4:./${path}/libs
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/coffeMach.cfg ${maquinas}11:./${path}/libs
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/codMaquina.cafe ${maquinas}11:./${path}/libs
sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/broker.cfg ${maquinas}5:./${path}/libs