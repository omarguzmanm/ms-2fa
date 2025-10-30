pipeline {
    agent any

//        environment {
//         DOCKER_IMAGE = "romilbhai/spring-boot-app"
//         K8S_NAMESPACE = "springboot-demo"
//         DOCKER_CREDENTIALS_ID = 'dockerhub-credentials-id' // Define Docker Hub credentials ID
//         KUBECONFIG_CREDENTIALS_ID = 'kubeconfig-springboot' // Define Kubernetes config credentials ID
//     }

    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout scm
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    sh "mvn clean package"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t romilbhai/spring-boot-app:${env.BUILD_ID} ."
                    sh "docker tag romilbhai/spring-boot-app:${env.BUILD_ID} romilbhai/spring-boot-app:latest"
                }
            }
        }

        stage('Push to dockerhub') {
            steps {
                script {
                       withCredentials([string(credentialsId: 'dockerhub-password', variable: 'DOCKER_PASSWORD')]) {
                sh """
                    echo \$DOCKER_PASSWORD | docker login -u romilbhai --password-stdin
                """
                sh "docker push romilbhai/spring-boot-app:${env.BUILD_ID}"
                sh "docker push romilbhai/spring-boot-app:latest"
            }
                    }
                }
            }


        stage('Deploy to Kubernetes') {
            steps {
                script {

                     kubeconfig(credentialsId: 'kubeconfig') {

                        sh 'kubectl apply -f k8s/deployment.yaml -n ${env.K8S_NAMESPACE}'
                        sh 'kubectl apply -f k8s/service.yaml -n ${env.K8S_NAMESPACE}'
                    }


                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build or deployment failed.'
        }
    }
}
