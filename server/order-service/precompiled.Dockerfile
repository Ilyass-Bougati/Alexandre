FROM openjdk:21-slim AS builder

# Install dependencies
RUN apt-get update && apt-get install -y maven
RUN apt-get update && apt-get install -y curl

WORKDIR /app/order-service
COPY . /app

EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/order-service/target/order-service-1.0-SNAPSHOT.jar"]
