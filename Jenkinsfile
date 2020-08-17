pipeline {
	options {
    	buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
  	}
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
				dir('api-test') {
					git credentialsId: 'gitlab_login', url: 'https://gitlab.com/WagnerGalvao/tasks-api-tests'
					bat 'mvn test'
				}
        	}
        }
        stage('Deploy Frontend') {
            steps {
				dir('frontend') {
					git credentialsId: 'gitlab_login', url: 'https://gitlab.com/WagnerGalvao/tasks-frontend'
	                bat 'mvn clean package'
	                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8081/')], contextPath: 'tasks', war: 'target\\tasks.war'
				}
            }
        }
    }
}
