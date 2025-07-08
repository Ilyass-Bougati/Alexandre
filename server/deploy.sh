#!/usr/bin/bash

eval $(minikube docker-env)
docker build . -f discovery-server/Dockerfile -t alexandre/discovery-server
kubectl apply -f k8s/discovery-server.yaml
