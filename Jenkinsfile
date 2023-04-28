pipeline {
    agent any
    tools {
        gradle 'Gradle 6.6'
    }

    stages {
        stage('Clone repository, Copy and build') {
            steps {
                // Get some code from a GitHub repository
                checkout scmGit(
                branches: [[name: 'main']],
                userRemoteConfigs: [[url: 'https://github.com/alejandro945/distributed-coffee-machine']])
                // Run the maven build principal and copy the jar to the server
                sh 'cd $(pwd)/src && gradle build'
                sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/src/ServidorCentral/build/libs/* swarch@10.147.19.125:./ci-cd-coffee-machine/'
                // Run the maven build of the coffee machine and copy the jar to the remote pc, changed the ip and port of the client
                sh 'cd $(pwd)/src/coffeMach/src/resources/ && sed -i "1s/.*/CoffeMach.Endpoints = default -h 192.168.131.50 -p 12346/" coffeMach.cfg'
                sh 'cd $(pwd)/src && gradle build'
                sh 'cd $(pwd)/src/ && echo 1 > codMaquina.cafe'
                sh 'mv $(pwd)/src/codMaquina.cafe $(pwd)/src/coffeMach/build/libs/'
                sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/src/coffeMach/build/libs/* swarch@10.147.19.95:./ci-cd-coffee-machine/'
                // Run the maven build of the coffee machine and copy the jar to the remote pc, changed the ip and port of the client
                sh 'cd $(pwd)/src/coffeMach/src/resources/ && sed -i "1s/.*/CoffeMach.Endpoints = default -h 192.168.131.51 -p 12346/" coffeMach.cfg'
                sh 'cd $(pwd)/src && gradle build'
                sh 'cd $(pwd)/src/ && echo 2 > codMaquina.cafe'
                sh 'mv $(pwd)/src/codMaquina.cafe $(pwd)/src/coffeMach/build/libs/'
                sh 'sshpass -p "swarch" scp -o StrictHostKeyChecking=no -r $(pwd)/src/coffeMach/build/libs/* swarch@10.147.19.107:./ci-cd-coffee-machine/'
                // Run the JAR
                sh 'sshpass -p "swarch" ssh -X -o StrictHostKeyChecking=no swarch@10.147.19.125 "cd ./ci-cd-coffee-machine/libs && java -cp "./*" ServidorCentral"'
                sh 'sshpass -p "swarch" ssh -X -o StrictHostKeyChecking=no swarch@10.147.19.95 "cd ./ci-cd-coffee-machine/libs && java -cp "./*" CoffeeMach"'
                sh 'sshpass -p "swarch" ssh -X -o StrictHostKeyChecking=no swarch@10.147.19.107 "cd ./ci-cd-coffee-machine/libs && java -cp "./*" CoffeeMach"'                
            }
        }
    }
}
