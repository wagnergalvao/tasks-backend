pipeline {
	agent {
		label 'windows'
	}
	stages {
        stage('Build Backend'){
            steps {
                bat "mvn clean package -DskipTests=true"
            }
        }
    }
}
