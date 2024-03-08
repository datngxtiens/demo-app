pipeline {
    agent {
        node {
            label 'jenkins-agent-1' // Label of the node you want to run the job on
        }
    }
    stages {
        stage('Build docker images') {
            steps {
                sh 'mvn -B -DskipTests clean package'

                script {
                    DOCKER_IMAGE="gitops-demo"
                    app = docker.build("datngxtiens/${DOCKER_IMAGE}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    DOCKER_REGISTRY="registry.hub.docker.com"
                    DOCKER_NAME="datngxtiens"

                    docker.withRegistry('https://registry.hub.docker.com', 'datngxtiens-dockerhub') {
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
                build job: 'update-manifest-github',
                    parameters: [
                        string(name: 'DOCKERTAG', value: env.BUILD_NUMBER)
                    ]
            }
        }
    }
}