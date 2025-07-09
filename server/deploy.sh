#!/usr/bin/bash

# using minikube docker
eval $(minikube docker-env)

# checking for the build-all flag
# if it's present building all the images
if [[ "$*" == *"--build-all"* ]]; then
    echo "Building all images..."
    docker build . -f discovery-server/Dockerfile -t alexandre/discovery-server
fi

# Building and deploying the discovery server
kubectl apply -f k8s/discovery-server.yaml
