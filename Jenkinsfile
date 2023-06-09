pipeline {
    agent any
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
				deploy adapters: [tomcat8(credentialsId: 'tomcat_login', path: '', url: 'http://localhost:8081/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
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
					deploy adapters: [tomcat8(credentialsId: 'tomcat_login', path: '', url: 'http://localhost:8081/')], contextPath: 'tasks', war: 'target/tasks.war'
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
        stage('Deploy Producao') {
            steps {
				sh 'docker-compose build'
				sh 'docker-compose up -d'
			}
        }
        stage('Health Check') {
        	steps {
				dir('functional-test') {
					sh 'mvn verify -Dskip.surefire.tests'
				}
        	}
        }
	}
	post {
		always {
			junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml, functional-test/target/surefire-reports/*.xml, functional-test/target/failsafe-reports/*.xml'
			archiveArtifacts artifacts: 'target/tasks-backend.war, frontend/target/tasks.war', followSymlinks: false, onlyIfSuccessful: true
		}
		unsuccessful {
			emailext attachLog: true, body: 'See the attached log below', subject: '$JOB_NAME Build $BUILD_NUMBER as failed', to: 'galvao.wagner@outlook.com.br'
		}
		fixed {
			emailext attachLog: true, body: 'See the attached log below', subject: '$JOB_NAME Build $BUILD_NUMBER as fixed', to: 'galvao.wagner@outlook.com.br'
		}
	}
}
