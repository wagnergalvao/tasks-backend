pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        jdk 'jdk8'
    }
    stages {{
        stage ('Initialize') {
            steps {
                bat echo "PATH = ${PATH}"
				bat echo "MAVEN_HOME = ${MAVEN_HOME}"
            }
        }
        stage('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit tests') {
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
        stage('Functional Test') {
        	steps {
				dir('functional-test') {
					git credentialsId: 'gitlab_login', url: 'https://gitlab.com/WagnerGalvao/tasks-functional-test'
					bat 'mvn test'
				}
        	}
        }
    }
}
