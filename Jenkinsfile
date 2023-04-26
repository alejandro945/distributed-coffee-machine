pipeline {
    agent any

    stages {
        stage('Clone repository') {
            steps {
                checkout scmGit(
                branches: [[name: 'main']],
                userRemoteConfigs: [[url: 'https://github.com/alejandro945/distributed-coffee-machine']])
                // Ejecutar el comando SSH para copiar los archivos cambiados usando scp, asumimos la creacion de carpetas
                //sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/* swarch@10.147.19.125:./ci-cd-coffee-machine/'
                //sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/* swarch@10.147.19.95:./ci-cd-coffee-machine/'
                //sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/* swarch@10.147.19.107:./ci-cd-coffee-machine/'
            }
        }
        stage('Copy Source code and Conect to server node') {
            steps {
                script {
                    sh "export LC_CTYPE=C"
                    // Configurar la conexión a través de la dirección IP asignada por ZeroTier
                    def ip = '10.147.19.125' // xhgrid 9 ip in zerotier network

                    def sshBuild = """
                    sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@${ip} "cd ./ci-cd-coffee-machine/src && gradle build"'
                    """
                    // Ejecutar el comando SSH para conectar a la máquina remota y ejecutar el build
                    sh sshBuild
                }
            }
        }
        stage('Copy Source code and Conect to first client node') {
            steps {
                script {
                    sh "export LC_CTYPE=C"
                    // Configurar la conexión a través de la dirección IP asignada por ZeroTier
                    def ip = '10.147.19.95' // xhgrid 10 ip in zerotier network

                    def sshBuild = """
                    sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@${ip} "cd ./ci-cd-coffee-machine/src && gradle build"'
                    """
                    // Ejecutar el comando SSH para conectar a la máquina remota y ejecutar el build
                    sh sshBuild
                }
            }
        }
        stage('Copy Source code and Conect to second client node') {
            steps {
                script {
                    sh "export LC_CTYPE=C"
                    // Configurar la conexión a través de la dirección IP asignada por ZeroTier
                    def ip = '10.147.19.107' // xhgrid 11 ip in zerotier network

                    def sshBuild = """
                    sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@${ip} "cd ./ci-cd-coffee-machine/src && gradle build"'
                    """
                    // Ejecutar el comando SSH para conectar a la máquina remota y ejecutar el build
                    sh sshBuild
                }
            }
        }
    }
}
