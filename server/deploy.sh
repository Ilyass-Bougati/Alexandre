#!/usr/bin/bash

# Function for notification
notify_if_failed_or_success() {
    local topic=$1
    local exit_code=$?

    if [[ -z "$topic" ]]; then
        echo "Usage: notify_if_failed_or_success <topic>"
        return 1
    fi

    if [[ "$exit_code" -ne 0 ]]; then
        ./utils/error.py "$topic"
    fi
}

# checking for the build-all flag
# if it's present building all the images
if [[ "$*" == *"--build-all"* ]]; then

    if [[ "$*" != *"--compose"* ]]; then
        # using minikube docker
        eval $(minikube docker-env)
    fi

    echo "Building all images..."
    # docker build . -f discovery-server/Dockerfile -t alexandre/discovery-server
    notify_if_failed_or_success "alexandre"
    docker build . -f config-server/Dockerfile -t alexandre/config-server
    notify_if_failed_or_success "alexandre"
    docker build . -f api-gateway/Dockerfile -t alexandre/api-gateway
    notify_if_failed_or_success "alexandre"
    docker build . -f user-service/Dockerfile -t alexandre/user-service
    DEPLOY_STATUS="$?"

    # Notifying that the deployment is finished
    if [[ "$*" == *"--ntfy"* ]]; then
        if [[ "$DEPLOY_STATUS" == "0" ]]; then
            ./utils/notify.py alexandre
        else
            ./utils/error.py alexandre
            exit 1
        fi
    fi
fi

# checking for the compose flag
# if it's present we're using docker compose instead of k8s
if [[ "$*" == *"--compose"* ]]; then
    echo "Running using docker compose..."
    docker compose up
else
    echo "Running using k8s..."

    # Building and deploying the discovery server
    kubectl apply -f k8s/discovery-server.yaml
    kubectl apply -f k8s/config-server.yaml
fi
