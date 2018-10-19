pipeline {
  agent any
  stages {
    stage('Source Checkout') {
      steps {
        git(url: 'https://github.com/gargmunish/hello-spring-cloud-syn.git', branch: 'master')
      }
    }
    stage('Fail') {
      steps {
        script {
          try {
            // Any maven phase that that triggers the test phase can be used here.
            sh 'curl -d \'{"firstName": "First name","secondName": "Second name","dateOfBirth": "01/12/2020","profession": "Software Developer","salary": 0}\' -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:8443/getPerson'
          } catch(err) {
            if (currentBuild.result == 'UNSTABLE')
            currentBuild.result = 'FAILURE'
            throw err
          }
        }

        retry(count: 3) {
          sh 'curl -d \'{"firstName": "First name","secondName": "Second name","dateOfBirth": "01/12/2020","profession": "Software Developer","salary": 0}\' -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:8443/getPerson'
        }

      }
    }
    stage('Code Scan') {
      parallel {
        stage('Sonar') {
          steps {
            input(message: 'waiting for input', id: 'Munish', ok: 'OK')
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
            emailext(subject: 'hello', body: 'from body', attachLog: true)
          }
        }
      }
    }
    stage('Build Services') {
      parallel {
        stage('Business') {
          steps {
            sh '''mvn build-helper:parse-version versions:set -DnewVersion=\\${parsedVersion.majorVersion}.\\${parsedVersion.minorVersion}.\\${parsedVersion.nextIncrementalVersion} versions:commit 
             mvn clean install deploy -s settings.xml'''
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
        script {
          withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
            sh 'git config user.email "tradetools@fedex.com"'
            sh 'git config user.name ${GIT_USERNAME} '
            sh 'git add pom.xml'
            sh 'git commit -m "updated build number" '
            sh 'git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/gargmunish/hello-spring-cloud-master.git'
          }
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
        sh '''mvn clean install
cd /honme
cd ../test
cd ../test2'''
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
  environment {
    NEXUS = credentials('nexus-admin')
  }
}