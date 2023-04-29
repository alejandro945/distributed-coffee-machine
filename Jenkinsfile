pipeline {
    agent any
    stages {
        stage('Clone repository, Copy and build') {
            steps {
                // Get some code from a GitHub repository
                checkout scmGit(
                branches: [[name: 'main']],
                userRemoteConfigs: [[url: 'https://github.com/alejandro945/distributed-coffee-machine']])

                sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/* swarch@10.147.19.125:./ci-cd-coffee-machine/'
                sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/* swarch@10.147.19.95:./ci-cd-coffee-machine/'
                sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/* swarch@10.147.19.107:./ci-cd-coffee-machine/'

                sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@10.147.19.95 "cd ci-cd-coffee-machine/src/ && echo 1 > codMaquina.cafe && cd coffeMach/src/main/resources/ && sed -i \"1s/.*/CoffeMach.Endpoints = default -h 192.168.131.50 -p 12346\" coffeMach.cfg"'
                sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@10.147.19.107 "cd ci-cd-coffee-machine/src/ && echo 2 > codMaquina.cafe && cd coffeMach/src/main/resources/ && sed -i \"1s/.*/CoffeMach.Endpoints = default -h 192.168.131.51 -p 12346\" coffeMach.cfg"'
                
                sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@10.147.19.125 "cd ci-cd-coffee-machine/src/ && gradle build && java -jar ServidorCentral/build/libs/ServidorCentral.jar"'
                sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@10.147.19.95 "cd ci-cd-coffee-machine/src/ && gradle build && java -jar coffeeMach/build/libs/coffeeMach.jar"'
                sh 'sshpass -p "swarch" ssh -o StrictHostKeyChecking=no swarch@10.147.19.107 "cd ci-cd-coffee-machine/src/ && gradle build && java -jar coffeeMach/build/libs/coffeeMach.jar"'
            }
        }
    }
}
