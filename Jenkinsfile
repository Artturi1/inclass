pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    parameters {
        string(
            name: 'DOCKERHUB_REPOSITORY',
            defaultValue: 'your-dockerhub-username/temperature-converter',
            description: 'Docker Hub repository in lowercase, for example username/temperature-converter'
        )
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
        IMAGE_TAG = "${params.DOCKERHUB_REPOSITORY}:${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn clean verify'
            }
        }

        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Coverage Report') {
            steps {
                jacoco execPattern: '**/target/jacoco.exec',
                      classPattern: '**/target/classes',
                      sourcePattern: '**/src/main/java'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build --tag "%IMAGE_TAG%" .'
            }
        }

        stage('Run Docker Smoke Test') {
            steps {
                bat 'docker run --rm "%IMAGE_TAG%"'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                bat 'echo "%DOCKERHUB_CREDENTIALS_PSW%" | docker login --username "%DOCKERHUB_CREDENTIALS_USR%" --password-stdin'
                bat 'docker push "%IMAGE_TAG%"'
                bat 'docker tag "%IMAGE_TAG%" "%DOCKERHUB_REPOSITORY%:latest"'
                bat 'docker push "%DOCKERHUB_REPOSITORY%:latest"'
            }
        }
    }

    post {
        always {
            bat 'docker logout || exit 0'
            archiveArtifacts artifacts: 'target/site/jacoco/**, target/surefire-reports/**', allowEmptyArchive: true
        }
    }
}