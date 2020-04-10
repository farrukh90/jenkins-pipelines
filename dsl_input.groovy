pipeline {
    agent any 
    stages {
        stage('Stage1') { 
            steps {
                echo "hello"
            }
        }
        stage('Stage2') { 
            steps {
                echo "hello"
            }
        }
        stage('Stage3') { 
            steps {
                echo "hello"
				input 'Should I proceed?'
            }
        }
    }
}