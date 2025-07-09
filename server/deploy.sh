#!/usr/bin/bash

if [[ "$*" == *"--build-all"* ]]; then
    echo "Building all images..."
    docker build . -f discovery-server/Dockerfile -t alexandre/discovery-server
fi

# using minikube docker
eval $(minikube docker-env)

# Building and deploying the discovery server
kubectl apply -f k8s/discovery-server.yaml
