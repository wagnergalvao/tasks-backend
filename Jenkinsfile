pipeline {
    agent any
    tools {
        maven 'MAVEN_LOCAL'
        jdk 'JAVA_LOCAL'
    }
    stages {
        stage('Build Backend') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit tests') {
            steps {
                sh 'mvn test'
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
					sh 'mvn test'
				}
        	}
        }
        stage('Deploy Frontend') {
            steps {
				dir('frontend') {
					git credentialsId: 'gitlab_login', url: 'https://gitlab.com/WagnerGalvao/tasks-frontend'
	                sh 'mvn clean package'
	                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8081/')], contextPath: 'tasks', war: 'target\\tasks.war'
				}
            }
        }
        stage('Functional Test') {
        	steps {
				dir('functional-test') {
					git credentialsId: 'gitlab_login', url: 'https://gitlab.com/WagnerGalvao/tasks-functional-test'
					sh 'mvn test'
				}
        	}
        }
	}
}
