pipeline {
  agent any
  stages {
    stage('pull repo') {
      steps {
        git 'https://github.com/farrukh90/artemis'
      }
    }

    stage('Stage2') {
      steps {
        sh 'echo "Hello"'
      }
    }

  }
}