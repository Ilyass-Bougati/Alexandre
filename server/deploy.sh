#!/usr/bin/bash

# checking for the build-all flag
# if it's present building all the images
if [[ "$*" == *"--build-all"* ]]; then

    if [[ "$*" != *"--compose"* ]]; then
        # using minikube docker
        eval $(minikube docker-env)
    fi

    echo "Building all images..."
    docker build . -f discovery-server/Dockerfile -t alexandre/discovery-server
    docker build . -f config-server/Dockerfile -t alexandre/config-server
    docker build . -f api-gateway/Dockerfile -t alexandre/api-gateway
    # Notifying that the deployment is finished
    ./utils/notify.py alexandre
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
