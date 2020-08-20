pipeline { 
    agent any 
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') { 
            steps { 
				bat 'echo Build'
            }
        }
        stage('Test'){
            steps {
				bat 'echo Test'
            }
        }
        stage('Deploy') {
            steps {
				bat 'echo Deploy'
            }
        }
    }
}