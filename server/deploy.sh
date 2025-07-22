#!/usr/bin/bash

# Function for notification
notify_if_failed_or_success() {
    local topic=$1
    local message=$2
    local tag=$3
    local exit_code=$?

    if [[ "$*" == *"--ntfy"* ]]; then
        if [[ -z "$topic" ]]; then
            echo "Usage: notify_if_failed_or_success <topic>"
            return 1
        fi

        if [[ "$exit_code" -ne 0 ]]; then
            ./utils/notify.py "$topic" "$message" "$tag"
        fi
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
    docker build . -f discovery-server/Dockerfile -t alexandre/discovery-server
    notify_if_failed_or_success  alexandre "Building the discovery-server failed" "rotating_light"
    docker build . -f config-server/Dockerfile -t alexandre/config-server
    notify_if_failed_or_success  alexandre "Building the config-server failed" "rotating_light"
    docker build . -f api-gateway/Dockerfile -t alexandre/api-gateway
    notify_if_failed_or_success  alexandre "Building the api-gateway failed" "rotating_light"
    docker build . -f order-service/Dockerfile -t alexandre/order-service
    notify_if_failed_or_success  alexandre "Building the order-service failed" "rotating_light"
    docker build . -f inventory-service/Dockerfile -t alexandre/inventory-service
    notify_if_failed_or_success  alexandre "Building the inventory-service failed" "rotating_light"
    docker build . -f notification-service/Dockerfile -t alexandre/notification-service
    notify_if_failed_or_success  alexandre "Building the notification-service failed" "rotating_light"
    docker build . -f delivery-service/Dockerfile -t alexandre/delivery-service
    notify_if_failed_or_success  alexandre "Building the delivery-service failed" "rotating_light"
    docker build . -f payment-service/Dockerfile -t alexandre/payment-service
    notify_if_failed_or_success  alexandre "Building the payment-service failed" "rotating_light"
    docker build . -f user-service/Dockerfile -t alexandre/user-service
    DEPLOY_STATUS="$?"

    # Notifying that the deployment is finished
    if [[ "$*" == *"--ntfy"* ]]; then
        if [[ "$DEPLOY_STATUS" == "0" ]]; then
            ./utils/notify.py alexandre "Building finished successfully" "loudspeaker"
        else
            ./utils/notify.py alexandre "Error the user-service failed" "rotating_light"
            exit 1
        fi
    fi
fi

if [[ "$*" == *"--precompile"* ]]; then
    # precompiling the services
    mvn clean install -Dmaven.test.skip=true

    # building the docker images
    docker build . -f discovery-server/precompiled.Dockerfile -t alexandre/discovery-server
    notify_if_failed_or_success  alexandre "Building the discovery-server failed" "rotating_light"
    docker build . -f config-server/precompiled.Dockerfile -t alexandre/config-server
    notify_if_failed_or_success  alexandre "Building the config-server failed" "rotating_light"
    docker build . -f api-gateway/precompiled.Dockerfile -t alexandre/api-gateway
    notify_if_failed_or_success  alexandre "Building the api-gateway failed" "rotating_light"
    docker build . -f order-service/precompiled.Dockerfile -t alexandre/order-service
    notify_if_failed_or_success  alexandre "Building the order-service failed" "rotating_light"
    docker build . -f inventory-service/precompiled.Dockerfile -t alexandre/inventory-service
    notify_if_failed_or_success  alexandre "Building the inventory-service failed" "rotating_light"
    docker build . -f notification-service/precompiled.Dockerfile -t alexandre/notification-service
    notify_if_failed_or_success  alexandre "Building the notification-service failed" "rotating_light"
    docker build . -f delivery-service/precompiled.Dockerfile -t alexandre/delivery-service
    notify_if_failed_or_success  alexandre "Building the delivery-service failed" "rotating_light"
    docker build . -f payment-service/precompiled.Dockerfile -t alexandre/payment-service
    notify_if_failed_or_success  alexandre "Building the payment-service failed" "rotating_light"
    docker build . -f user-service/precompiled.Dockerfile -t alexandre/user-service

    # Notifying that the deployment is finished
    if [[ "$*" == *"--ntfy"* ]]; then
        if [[ "$DEPLOY_STATUS" == "0" ]]; then
            ./utils/notify.py alexandre "Building finished successfully" "loudspeaker"
        else
            ./utils/notify.py alexandre "Error the user-service failed" "rotating_light"
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
