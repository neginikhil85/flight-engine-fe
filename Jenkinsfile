pipeline {
    agent {
            docker {
                image 'maven:3.8.6-jdk-11' // or another Maven image
                args '-v /path/to/your/project:/workspace'
            }
    }

    environment {
        // Project and application configuration in OpenShift
        OPENSHIFT_PROJECT = 'your-openshift-project'
        OPENSHIFT_APP_NAME = 'your-vaadin-app'
        DOCKER_IMAGE = "${OPENSHIFT_APP_NAME}:latest"
        JAVA_VERSION = '17'  // Using Java 17
        MAVEN_HOME = tool 'Maven 3.8.6' // Configured Maven version in Jenkins
    }

    tools {
        jdk "${JAVA_VERSION}"
        maven "${MAVEN_HOME}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Mvn Build') {
            steps {
                echo 'Building the Vaadin application with Maven...'
                // Build the Vaadin project in production mode
                sh "${MAVEN_HOME}/bin/mvn clean package -Pproduction -DskipTests"
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                // Run unit tests
                sh "${MAVEN_HOME}/bin/mvn test"
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building and pushing the Docker image in OpenShift...'
                script {
                    // Use OpenShift's Jenkins plugin to create a new image build
                    openshiftBuild(buildConfig: "${OPENSHIFT_APP_NAME}", showBuildLogs: 'true')
                }
            }
        }

        stage('Deploy to OpenShift') {
            steps {
                echo 'Deploying the application to OpenShift...'
                script {
                    // Trigger OpenShift deployment
                    openshiftDeploy(deploymentConfig: "${OPENSHIFT_APP_NAME}", waitTime: '600')
                    openshiftVerifyDeployment(deploymentConfig: "${OPENSHIFT_APP_NAME}", replicaCount: '1', verifyReplicaCount: true)
                }
            }
        }
    }
}
