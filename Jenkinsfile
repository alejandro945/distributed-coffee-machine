pipeline {
    agent any

    stages {
        stage('Clone repository') {
            steps {
                git 'https://github.com/tuusuario/turepo.git' // Clona el repositorio
            }
        }
        stage('Conectar a máquina remota y ejecutar build') {
            steps {
                script {
                    // Configurar la conexión a través de la dirección IP asignada por ZeroTier
                    def ip = '10.147.19.126' // xhgrid 2 ip in zerotier network
                    def sshCommand = """
                        ssh -o StrictHostKeyChecking=no swarch@${ip} 'cd /ruta/en/remota && gradle build'
                    """
                    // Ejecutar el comando SSH para conectar a la máquina remota y ejecutar el build
                    sh sshCommand
                }
            }
        }
    }
}
