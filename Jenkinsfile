pipeline {
    agent {
        kubernetes {
            yaml '''
            apiVersion: v1
            kind: Pod
            metadata:
              name: jenkins-agent
            spec:
              containers:
              - name: maven
                image: maven:3.9.9-eclipse-temurin-17-alpine
                command:
                - cat
                tty: true
              - name: docker
                image: docker:27.2.0-alpine3.20
                command:
                - cat
                tty: true
                volumeMounts:
                - mountPath: "/var/run/docker.sock"
                  name: docker-socket
              volumes:
              - name: docker-socket
                hostPath:
                  path: "/var/run/docker.sock"
            '''
        }
    }

    environment {
        DOCKER_IMAGE_NAME = 'village1031/bucams-api'
        DOCKER_CREDENTIALS_ID = 'dockerhub-access' // 오타 수정됨
    }

    stages {
        /*
        stage('SonarQube Analysis') {
            steps {
                container('maven') {
                    withSonarQubeEnv('sonarqube-server') {
                        sh '''mvn clean verify sonar:sonar \
                            -Dsonar.projectKey=university-api \
                            -Dsonar.projectName=university-api'''
                    }
                }
            }
        }
        */

        stage('Gradle Build') {
            steps {
                container('maven') {
                    sh 'pwd'
                    sh 'ls -al'
                    sh 'cd backend && chmod +x gradlew && ./gradlew -v && ./gradlew clean && ./gradlew build'
                    sh 'ls -al backend/build/libs'
                }
            }
        }

        stage('Image Build & Push') {
            steps {
                container('docker') {
                    script {
                        def dockerImageVersion = "${env.BUILD_NUMBER}"

                        sh 'docker logout'

                        withCredentials([usernamePassword(
                            credentialsId: DOCKER_CREDENTIALS_ID,
                            usernameVariable: 'DOCKER_USERNAME',
                            passwordVariable: 'DOCKER_PASSWORD'
                        )]) {
                            sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        }

                        withEnv(["DOCKER_IMAGE_VERSION=${dockerImageVersion}"]) {
                            sh 'docker -v'
                            sh 'echo $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_VERSION'
                            sh 'docker build --no-cache -t $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_VERSION ./'
                            sh 'docker image inspect $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_VERSION'
                            sh 'docker push $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_VERSION'
                        }
                    }
                }
            }
        }

        /*
        stage('Trigger university-k8s-manifests') {
            steps {
                script {
                    def dockerImageVersion = "${env.BUILD_NUMBER}"
                    withEnv(["DOCKER_IMAGE_VERSION=${dockerImageVersion}"]) {
                        build job: 'university-k8s-manifests',
                          parameters: [
                              string(name: 'DOCKER_IMAGE_VERSION', value: "${DOCKER_IMAGE_VERSION}")
                          ],
                          wait: true
                    }
                }
            }
        }
        */
    }

    /*
    post {
        always {
            withCredentials([string(
                credentialsId: 'discord-webhook',
                variable: 'DISCORD_WEBHOOK_URL'
            )]) {
                discordSend description: """
                제목 : ${currentBuild.displayName}
                결과 : ${currentBuild.result}
                실행 시간 : ${currentBuild.duration / 1000}s
                """,
                result: currentBuild.currentResult,
                title: "${env.JOB_NAME} : ${currentBuild.displayName}",
                webhookURL: "${DISCORD_WEBHOOK_URL}"
            }
        }

        success {
            withCredentials([string(
                credentialsId: 'discord-webhook',
                variable: 'DISCORD_WEBHOOK_URL'
            )]) {
                discordSend description: """
                제목 : ${currentBuild.displayName}
                결과 : ${currentBuild.result}
                실행 시간 : ${currentBuild.duration / 1000}s
                """,
                result: currentBuild.currentResult,
                title: "${env.JOB_NAME} : ${currentBuild.displayName} 성공",
                webhookURL: "${DISCORD_WEBHOOK_URL}"
            }
        }

        failure {
            withCredentials([string(
                credentialsId: 'discord-webhook',
                variable: 'DISCORD_WEBHOOK_URL'
            )]) {
                discordSend description: """
                제목 : ${currentBuild.displayName}
                결과 : ${currentBuild.result}
                실행 시간 : ${currentBuild.duration / 1000}s
                """,
                result: currentBuild.currentResult,
                title: "${env.JOB_NAME} : ${currentBuild.displayName} 실패",
                webhookURL: "${DISCORD_WEBHOOK_URL}"
            }
        }
    }
    */
}
