pipeline {
    agent any // Use any available agent

    stages {
        stage('Build') {
            steps {
                script {
                    // Clean and build the project
                    sh 'mvn clean package'
                }
            }
        }

        stage('Run') {
            steps {
                script {
                    // Run the application (adjust the command as needed)
                    sh 'java -jar target/your-app.jar'
                }
            }
        }
    }
}
