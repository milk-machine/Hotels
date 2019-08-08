pipeline {
  agent {
    docker {
      image 'circleci/android'
    }

  }
  stages {
    stage('Test') {
      steps {
        sh '''./gradlew test
'''
      }
    }
  }
}