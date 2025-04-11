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
        DOCKER_CREDENTAILS_ID = 'dockerhub-access'
    }

    stages {
        // stage('SonarQube Analysis'){
        //     steps{
        //         container('maven') {
        //             withSonarQubeEnv('sonarqube-server'){
        //                 sh '''mvn clean verify sonar:sonar \
        //                     -Dsonar.projectKey=university-api \
        //                     -Dsonar.projectName=university-api'''
        //             }
        //         }
        //     }
        // }
        // Maven Build 자동화
            stage('Gradle Build') {
                steps {
                    container('gradle') {
                        sh 'pwd'
                        sh 'ls -al'
                        sg 'cd backend'
                        sh './gradlew -v'         // Gradle 버전 확인
                        sh './gradlew clean'      // 빌드 클린
                        sh './gradlew build'      // 실제 빌드 수행
                        sh 'ls -al'
                        sh 'ls -al ./build/libs'  // Gradle 빌드 결과물 위치
                    }
                }
            }

        stage('Image Build & Push') {
            steps {
                container('docker') {
                    // 그루비 문법 사용 가능한 블록
                    script {
                        def dockerImageVersion = "${env.BUILD_NUMBER}"

                        sh 'docker logout'

                        
                        // withCredentials() - 파이프라인에서 자격 증명을 사용할 수 있는 블록을 생성한다
                        // usernamePassword() - 자격 증명 중 사용자 이름과 비밀번호를 가져온다
                        // credentialsId는 자격 증명을 식별할 수 있는 식별자를 작성한다
                        // usernameVariavle은 자격 증명에서 가져온 사용자 이름을 저장하는 환경 변수의 이름을 작성한다
                        // passwordVariavle은 자격 증명에서 가져온 사용자 비밀번호를 저장하는 환경 변수의 이름을 작성한다

                        withCredentials([usernamePassword(
                            credentialsId: DOCKER_CREDENTAILS_ID, 
                            usernameVariable: 'DOCKER_USERNAME',
                            passwordVariable: 'DOCKER_PASSWORD'
                            )]) {
                            sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'

                        }
                        
                        // 파이프라인 단계에서 환경변수를 설정하는 역할을 한다
                        withEnv(["DOCKER_IMAGE_VERSION=${dockerImageVersion}"]) {
                            sh 'docker -v'
                            sh 'echo $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_VERSION'
                            // sh 'docker images university-api'
                            // sh 'docker build --no-cache -t university-api:2.7 ./'
                            // sh 'docker images university-api'
                            sh 'docker build --no-cache -t $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_VERSION ./'
                            sh 'docker image inspect $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_VERSION'
                            sh 'docker push $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_VERSION'
                        }                        
                    }                    
                }
            }
        }
        
        // stage('Trigger university-k8s-manifests'){
        //     steps {
        //         script {
        //           def dockerImageVersion = "${env.BUILD_NUMBER}"

        //           withEnv(["DOCKER_IMAGE_VERSION=${dockerImageVersion}"]) {
        //             // 다른 잡을 빌드하면서 파라미터 전달
        //             build job: 'university-k8s-manifests',
        //               parameters: [
        //                   string(name: 'DOCKER_IMAGE_VERSION', value: "${DOCKER_IMAGE_VERSION}")
        //               ],
        //               wait: true // 하위 잡이 끝날 떄까지 기다림
        //             }
        //         }
        //     }
        // }
    }

    // post {
    //     always {
    //         withCredentials([string(
    //             credentialsId: 'discord-webhook',
    //             variable: 'DISCORD_WEBHOOK_URL'
    //             )]) {
    //             discordSend description: """
    //             제목 : ${currentBuild.displayName}
    //             결과 : ${currentBuild.result}
    //             실행 시간 : ${currentBuild.duration / 1000}s
    //             """,
    //             result: currentBuild.currentResult,
    //             title: "${env.JOB_NAME} : ${currentBuild.displayName}", 
    //             webhookURL: "${DISCORD_WEBHOOK_URL}"
    //         }
    //     }

        // success {
        //     withCredentials([string(
        //         credentialsId: 'discord-webhook',
        //         variable: 'DISCORD_WEBHOOK_URL'
        //         )]) {
        //         discordSend description: """
        //         제목 : ${currentBuild.displayName}
        //         결과 : ${currentBuild.result}
        //         실행 시간 : ${currentBuild.duration / 1000}s
        //         """,
        //         result: currentBuild.currentResult,
        //         title: "${env.JOB_NAME} : ${currentBuild.displayName} 성공", 
        //         webhookURL: "${DISCORD}"

        //     }
        // }

        // failure {
        //     withCredentials([string(
        //         credentialsId: 'discord-webhook',
        //         variable: 'DISCORD_WEBHOOK_URL'
        //         )]) {
        //         discordSend description: """
        //         제목 : ${currentBuild.displayName}
        //         결과 : ${currentBuild.result}
        //         실행 시간 : ${currentBuild.duration / 1000}s
        //         """,
        //         result: currentBuild.currentResult,
        //         title: "${env.JOB_NAME} : ${currentBuild.displayName} 실패", 
        //         webhookURL: "${DISCORD}"

        //     }
        // }
    }

}
