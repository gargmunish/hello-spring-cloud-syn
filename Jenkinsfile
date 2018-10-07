pipeline {
  agent any
  stages {
    stage('Source Checkout') {
      steps {
        git(url: 'https://github.com/gargmunish/hello-spring-cloud-master', branch: 'master')
      }
    }
    stage('Code Scan') {
      parallel {
        stage('Sonar') {
          steps {
            sh 'echo "Sonar"'
          }
        }
        stage('PMD') {
          steps {
            sh 'echo "PMD"'
          }
        }
        stage('Check Style') {
          steps {
            sh 'echo "checkstyle"'
          }
        }
      }
    }
    stage('Build Services') {
      parallel {
        stage('Business') {
          steps {
            sh '''mvn build-helper:parse-version versions:set -DnewVersion=\\${parsedVersion.majorVersion}.\\${parsedVersion.minorVersion}.\\${parsedVersion.nextIncrementalVersion} versions:commit
mvn clean install deploy'''
          }
        }
        stage('Data') {
          steps {
            sh 'echo "Data"'
          }
        }
      }
    }
    stage('Update Build#') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
    		sh '''git add pom.xml git commit -m "updated build number" git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/gargmunish/hello-spring-cloud-master.git''
	}
     }
    }
    stage('Deploy to Dev') {
      steps {
        sh 'cf push -f ./manifest_dev.yml'
      }
    }
    stage('Unit Test') {
      steps {
        sh '## Add Test Step here'
      }
    }
    stage('Deploy to Release') {
      steps {
        sh '## Deploy to release'
      }
    }
    stage('Functional Test') {
      steps {
        sh '# Add functional Test'
      }
    }
    stage('Deploy to Stage') {
      steps {
        sh '# Deploy to Stage box'
      }
    }
    stage('Performance Test') {
      parallel {
        stage('Performance Test') {
          steps {
            sh '## Add Lisa Test'
          }
        }
        stage('UAT Test') {
          steps {
            sh '## Add Lisa Test'
          }
        }
      }
    }
    stage('Deploy to Prod') {
      steps {
        sh '## Add prod to Deploy'
      }
    }
  }
}
