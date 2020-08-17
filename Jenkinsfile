pipeline {
	agent any
    stages {
        stage ('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit tests') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Deploy Backend') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8081/')], contextPath: 'tasks-backend', war: 'target\\tasks-backend.war'
            }
        }
        stage('API Test') {
        	steps {
        		git credentialsId: 'gitlab_login', url: 'https://gitlab.com/WagnerGalvao/tasks-api-tests'
        		bat 'mvn test'
        	}
        }
    }
}
