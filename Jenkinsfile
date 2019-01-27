node{
    def DOCKERHUB_REPO = "akhilsuryadevara/cartracker"
    def DOCKER_SERVICE_ID = "car-tracker"
    def DOCKER_IMAGE_VERSION = ""

    stage("clean workspace"){
        deleteDir()
    }

    stage("git checkout"){
        checkout scm

        def GIT_COMMIT = sh(returnStdout: true, script: "git rev-parse HEAD").trim().take(11)
        DOCKER_IMAGE_VERSION = "${BUILD_NUMBER}-{GIT_COMMIT}"
    }

    stage("mvn build"){
        def mvn = tool name: "maven3.0.5", type: "maven"
        sh "mvn clean install"
    }

    stage("docker build"){
        sh "docker build -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION} ."
    }

    stage("docker push"){
        sh "docker push ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION}"
    }

    stage("docker service"){
        try{
            sh """
                if[[ \$(docker service ls --filter name = ${DOCKER_SERVICE_ID} --quiet | wc -l) -eq 0 ]]; then
                    docker service create \
                     --replicas 1 \
                     --name ${DOCKER_SERVICE_ID} \
                     --env spring.profiles.active=prod \
                     ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION}
                else
                    docker service update \
                     --image ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION} \
                     ${DOCKER_SEFVICE_ID}
                fi
            """
        }

         catch (e) {
         sh "docker service update --rollback ${DOCKER_SERVICE_ID}"
         error "Service  update failed . Rolling back ${DOCKER_SERVICE_ID}"
         }

         finally{
            sh "docker container prune -f"
            sh "docker image prune -af"
         }
    }

        stage("cleanup"){
        sh "docker container prune -f"
        sh "docker rmi ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION}"
        }


}