pipeline {
    agent any

    stages {
        stage('Clone repository, Copy and build') {
            steps {
                checkout scmGit(
                branches: [[name: 'main']],
                userRemoteConfigs: [[url: 'https://github.com/alejandro945/distributed-coffee-machine']])
                // Ejecutar el comando SSH para copiar los archivos cambiados usando scp, asumimos la creacion de carpetas
                //sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/* swarch@10.147.19.125:./ci-cd-coffee-machine/'
                //sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/* swarch@10.147.19.95:./ci-cd-coffee-machine/'
                //sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/* swarch@10.147.19.107:./ci-cd-coffee-machine/'
                // Ejecutar el comando SSH para conectar a la máquina remota y ejecutar el build
                sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@10.147.19.125 "cd ./ci-cd-coffee-machine/src && gradle build"'
                sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@10.147.19.95 "cd ./ci-cd-coffee-machine/src && gradle build"'
                sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@10.147.19.107 "cd ./ci-cd-coffee-machine/src && gradle build"'
            }
        }
    }
}
