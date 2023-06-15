#!/bin/bash

nodes=(4 5 6 7 10 11 12 15)
PASSWORD="swarch"
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