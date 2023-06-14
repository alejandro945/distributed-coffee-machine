#!/bin/bash

export PASSWORD="swarch"
maquinas="swarch@xhgrid"
path="ci-cd-coffeemachine"
cfg="config"


routeServidor="./remoteAdministration/build/"
routeCoffeeMach="./coffeeMachineSubsystem/build/"
routeProxy="./proxyCache/build/"
brokerMessage="./messageBroker/build/"
logistic="./logistic/build/"
warehouse="./inventory/build/"


echo "Comprimiendo librerias"


clients=(10 11 12 15)

start=40

sendData(){
    echo "send to the node $2"
    sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no $1/libs.zip ${maquinas}$2:./$path
    echo "unzip in node $2"
    sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$2 "cd ci-cd-coffeemachine && rm -r libs && unzip libs.zip"
    echo "send configs to node $2"
    sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/${3} ${maquinas}$2:./${path}/libs
}

countSend=0
echo "management coffee machine"
for i in "${clients[@]}"
do
    if (($countSend < 1)); then
        sendData "$routeCoffeeMach" $i "coffeMach.cfg"
    else
        sendData "$routeCoffeeMach" $i "coffeMach2.cfg"
        sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && mv coffeMach2.cfg coffeMach.cfg"
    fi

    echo "send codMaquina.cafe to node $i"
    sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/codMaquina.cafe ${maquinas}$i:./${path}/libs
    countSend=$(($countSend+1))
done


port=12347
count=0
startProxy=44
condMaquina=1


for i in "${clients[@]}"
do
    newVal=$(($start+$i))
    for ((j=0; j<20; j++))
    do
        newPort=$(($port+$j))
        echo $newPort
        sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && sed -i 's/CoffeMach.Endpoints = default -h 192\.168\.131\..* -p ..*/CoffeMach.Endpoints = default -h 192.168.131.'"$newVal"' -p '"$newPort"'/' coffeMach.cfg"
        sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && echo $condMaquina > codMaquina.cafe"
        #sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && java -cp './*' CoffeMach" &
        echo "run machine in $j node $i"
        condMaquina=$(($condMaquina+1))
        sleep 3         
    done
done
