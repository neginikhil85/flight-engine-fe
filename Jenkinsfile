pipeline {
    agent any // Use any available agent

    stages {
        stage('Build') {
            steps {
                script {
                    // Clean and build the project using cmd
                    bat 'mvn clean package'
                }
            }
        }

        stage('Run') {
            steps {
                script {
                    // Run the application using cmd (adjust the command as needed)
                    bat 'java -jar target/your-app.jar'
                }
            }
        }
    }
}
