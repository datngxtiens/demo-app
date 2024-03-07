pipeline {
    agent any
    stages {
        stage('Build docker images') {
            steps {
                script {
                    DOCKER_IMAGE="gitops-demo"
                    app = docker.build("datngxtiens/${DOCKER_IMAGE}")
                    // app.inside {
                    //     sh 'echo $(curl localhost:8080)'
                    // }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    DOCKER_REGISTRY="registry.hub.docker.com"
                    DOCKER_NAME="datngxtiens"

                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                        app.push("${env.BUILD_NUMBER}")
                        app.push("latest")
                    }

                    sh "docker image rm ${DOCKER_NAME}/${DOCKER_IMAGE}:latest"
                    sh "docker image rm ${DOCKER_REGISTRY}/${DOCKER_NAME}/${DOCKER_IMAGE}:${env.BUILD_NUMBER}"
                }
            }
        }

        stage('Trigger ManifestUpdate') {
            steps {
                echo "Update manifestjob"
                build job: 'update-manifest-github', //name job 2
                    parameters: [
                        string(name: 'DOCKERTAG', value: env.BUILD_NUMBER)
                    ]
            }
        }

    }
}