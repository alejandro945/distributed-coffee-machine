#!/bin/bash

export PASSWORD="swarch"
maquinas="swarch@xhgrid"
path="ci-cd-coffeemachine"
cfg="config"

gradle build

routeServidor="./remoteAdministration/build/"
routeCoffeeMach="./coffeeMachineSubsystem/build/"
routeProxy="./proxyCache/build/"
brokerMessage="./messageBroker/build/"
logistic="./logistic/build/"
warehouse="./inventory/build/"


echo "Comprimiendo librerias"

cd "$routeServidor" && zip -r libs.zip libs
cd "../.."
cd "$routeCoffeeMach" && zip -r libs.zip libs
cd "../.."
cd "$routeProxy" && zip -r libs.zip libs
cd "../.."
cd "$brokerMessage" && zip -r libs.zip libs
cd "../.."
cd "$logistic" && zip -r libs.zip libs
cd "../.."
cd "$warehouse" && zip -r libs.zip libs
cd "../.."

nodes=(4 5 6 7 10 11 12)
clients=(10 11 12)
broker=(5 7)
proxy=(4 6)

start=40

sendData(){
    echo "send to the node $2"
    sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no $1/libs.zip ${maquinas}$2:./$path
    echo "unzip in node $2"
    sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$2 "cd ci-cd-coffeemachine && rm -r libs && unzip libs.zip"
    echo "send configs to node $2"
    sshpass -p ${PASSWORD} scp -o StrictHostKeyChecking=no ${cfg}/${3} ${maquinas}$2:./${path}/libs
}

#for i in "${nodes[@]}"
#do
#    echo "create folder in node $i"
#    sshpass -p ${PASSWORD} ssh ${maquinas}$i "mkdir $path"
#    sshpass -p ${PASSWORD} ssh ${maquinas}$i "cd $path && mkdir libs"
#done


echo "manegement of the proxy"
for i in "${proxy[@]}"
do
    echo "Proxy"
    #sendData "$routeProxy" $i "proxy.cfg"
done

echo "management of the broker"
for i in "${broker[@]}"
do
    echo "Broker"
    #sendData "$brokerMessage" $i "broker.cfg"
done

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




echo runProxy
for i in "${proxy[@]}"
do
    newVal=$(($start+$i))
    sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && sed -i 's/192.168.131\..* -p 12346/192.168.131.'"$newVal"' -p 12346/' proxy.cfg"
    sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && java -cp './*' ProxyCache" &
    echo "run proxy in node $i"
    sleep 5
done

echo runBroker
for i in "${broker[@]}"
do
    newVal=$(($start+$i))
    sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && sed -i 's/192.168.131\..* -p 12346/192.168.131.'"$newVal"' -p 12346/' broker.cfg" 
    sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && java -cp './*' MessageBroker" &
    echo "run broker in node $i"
    sleep 5
done

port=12347
count=0
startProxy=44
condMaquina=1

for i in "${clients[@]}"
do
    newVal=$(($start+$i))
    for ((j=0; j<5; j++))
    do
        newPort=$(($port+$j))
        echo $newPort
        sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && sed -i 's/CoffeMach.Endpoints = default -h 192\.168\.131\..* -p ..*/CoffeMach.Endpoints = default -h 192.168.131.'"$newVal"' -p '"$newPort"'/' coffeMach.cfg"
        sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && echo $condMaquina > codMaquina.cafe"
        sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "cd $path/libs && java -cp './*' CoffeeMach" & >> "test.txt" &
        echo "run machine in $j node $i"
        condMaquina=$(($condMaquina+1))
        sleep 3      
    done
done



cleanup() {
    echo "Terminando los procesos remotos..."
    # Matar los procesos relacionados en máquinas remotas
    for i in "${nodes[@]}"
    do
        sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${maquinas}$i "pkill -f 'java -cp'"
    done
    exit 0
}


echo "exit?"
read res
if [ "$res" == "y" ]; then
    cleanup
fi