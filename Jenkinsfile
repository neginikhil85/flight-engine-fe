pipeline {
    agent any // Use any available agent

    stages {

        stage('Checkout SCM') {
           steps {
                checkout scm
           }
        }

        stage('Build') {
            steps {
                script {
                    // Clean and build the project using cmd
                    bat 'mvn clean package -Pproduction -DskipTests'
                }
            }
        }

        stage('Run') {
            steps {
                script {
                    // Run the application using cmd (adjust the command as needed)
                    bat 'java -jar target/flight-engine-fe-0.0.1-SNAPSHOT.jar'
                }
            }
        }
    }
}
