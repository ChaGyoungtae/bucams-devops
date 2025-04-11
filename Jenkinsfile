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
              - name: node
                image: node:20-alpine
                command:
                - cat
                tty: true
              volumes:
              - name: docker-socket
                hostPath:
                  path: "/var/run/docker.sock"
            '''
        }
    }

    environment {
        DOCKER_IMAGE_NAME = 'village1031/bucams-api'
        DOCKER_IMAGE_NAME_FRONTEND = 'village1031/bucams-vue'
        DOCKER_CREDENTIALS_ID = 'dockerhub-access'
    }

    stages {
        stage('Gradle Build') {
            steps {
                container('maven') {
                    sh 'cd backend && chmod +x gradlew && ./gradlew clean build'
                }
            }
        }

        stage('Frontend Build') {
            steps {
                container('node') {
                    sh '''
                        cd frontend
                        npm install
                        npm run build
                    '''
                }
            }
        }

        stage('Image Build & Push - Backend') {
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
                            sh 'docker build --no-cache -t $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_VERSION ./backend'
                            sh 'docker push $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_VERSION'
                        }
                    }
                }
            }
        }

        stage('Image Build & Push - Frontend') {
            steps {
                container('docker') {
                    script {
                        def dockerImageVersion = "${env.BUILD_NUMBER}"

                        withCredentials([usernamePassword(
                            credentialsId: DOCKER_CREDENTIALS_ID,
                            usernameVariable: 'DOCKER_USERNAME',
                            passwordVariable: 'DOCKER_PASSWORD'
                        )]) {
                            sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        }

                        withEnv(["DOCKER_IMAGE_VERSION=${dockerImageVersion}"]) {
                            sh 'pwd'
                            sh 'ls -al'
                            sh 'docker build --no-cache -t $DOCKER_IMAGE_NAME_FRONTEND:$DOCKER_IMAGE_VERSION ./frontend'
                            sh 'docker push $DOCKER_IMAGE_NAME_FRONTEND:$DOCKER_IMAGE_VERSION'
                        }
                    }
                }
            }
        }

        stage('Trigger bucams-k8s-manifests') {
            steps {
                script {
                    def dockerImageVersion = "${env.BUILD_NUMBER}"
                    withEnv(["DOCKER_IMAGE_VERSION=${dockerImageVersion}"]) {
                        build job: 'bucams-k8s-manifests',
                          parameters: [
                              string(name: 'DOCKER_IMAGE_VERSION', value: "${DOCKER_IMAGE_VERSION}")
                          ],
                          wait: true
                    }
                }
            }
        }
    }
}
