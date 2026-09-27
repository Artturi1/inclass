
pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    tools {
        maven 'Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                dir('TemperatureConverter_InClass4') {
                    bat 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('TemperatureConverter_InClass4') {
                    bat 'mvn test'
                }
            }
        }

        stage('Code Coverage') {
            steps {
                dir('TemperatureConverter_InClass4') {
                    bat 'mvn jacoco:report'
                }
            }
        }

        stage('Publish Test Results') {
            steps {
                junit 'TemperatureConverter_InClass4/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Coverage Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'TemperatureConverter_InClass4/target/site/jacoco',
                    reportFiles: 'index.html',
                    reportName: 'Code Coverage Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
                ])
            }
        }
    }
}
