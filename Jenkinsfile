pipeline {
    agent any

    stages {
        stage('Clone repository') {
            steps {
                 checkout scmGit(
                branches: [[name: 'main']],
                userRemoteConfigs: [[url: 'https://github.com/alejandro945/distributed-coffee-machine']])
            }
        }
        stage('Copy Source code and Conect to server node') {
            steps {
                script {
                    // Configurar la conexión a través de la dirección IP asignada por ZeroTier
                    def ip = '10.147.19.125' // xhgrid 9 ip in zerotier network

                    def sshCopy = """
                    sh "sshpass -p 'swarch' scp -o StrictHostKeyChecking=no -r ./* swarch@${ip}:./ci-cd-coffee-machine/"
                    """
                    // Ejecutar el comando SSH para copiar los archivos cambiados usando scp
                    sh sshCopy

                    def sshConect = """
                        sshpass -p 'swarch' ssh swarch@${ip} 'cd cd ./ci-cd-coffee-machine/src/coffeemach && ./gradlew build && java -jar ServidorCentral/build/libs/ServidorCentral.jar'
                    """
                    // Ejecutar el comando SSH para conectar a la máquina remota y ejecutar el build
                    sh sshConect
                }
            }
        }
        stage('Copy Source code and Conect to first client node') {
            steps {
                script {
                    // Configurar la conexión a través de la dirección IP asignada por ZeroTier
                    def ip = '10.147.19.95' // xhgrid 10 ip in zerotier network

                    def sshCopy = """
                    sh "sshpass -p 'swarch' scp -o StrictHostKeyChecking=no -r ./* swarch@${ip}:./ci-cd-coffee-machine/"
                    """
                    // Ejecutar el comando SSH para copiar los archivos cambiados usando scp
                    sh sshCopy

                    def sshConect = """
                        sshpass -p 'swarch' ssh swarch@${ip} 'cd cd ./ci-cd-coffee-machine/src/coffeemach && ./gradlew build && java -jar coffeeMach/build/libs/coffeeMach.jar'
                    """
                    // Ejecutar el comando SSH para conectar a la máquina remota y ejecutar el build
                    sh sshConect
                }
            }
        }
        stage('Copy Source code and Conect to second client node') {
            steps {
                script {
                    // Configurar la conexión a través de la dirección IP asignada por ZeroTier
                    def ip = '10.147.19.107' // xhgrid 11 ip in zerotier network

                    def sshCopy = """
                    sh "sshpass -p 'swarch' scp -o StrictHostKeyChecking=no -r ./* swarch@${ip}:./ci-cd-coffee-machine/"
                    """
                    // Ejecutar el comando SSH para copiar los archivos cambiados usando scp
                    sh sshCopy

                    def sshConect = """
                        sshpass -p 'swarch' ssh swarch@${ip} 'cd cd ./ci-cd-coffee-machine/src/coffeemach && ./gradlew build && java -jar coffeeMach/build/libs/coffeeMach.jar'
                    """
                    // Ejecutar el comando SSH para conectar a la máquina remota y ejecutar el build
                    sh sshConect
                }
            }
        }
    }
}
