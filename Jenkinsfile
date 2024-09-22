pipeline {
    agent any // Use any available agent

    stages {
        stage('Build') {
            steps {
                script {
                    // Run Maven build using the Red Hat Maven image
                    sh 'podman run --rm -v $WORKSPACE:/workspace -w /workspace registry.access.redhat.com/openshift3/maven-35-centos7:latest mvn clean package'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests using the same Maven image
                    sh 'podman run --rm -v $WORKSPACE:/workspace -w /workspace registry.access.redhat.com/openshift3/maven-35-centos7:latest mvn test'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deployment logic, e.g., applying Kubernetes resources
                    sh 'kubectl apply -f deployment.yaml'
                }
            }
        }
    }
}
