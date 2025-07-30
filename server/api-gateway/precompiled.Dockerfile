FROM openjdk:21-slim AS builder

# Install dependencies
RUN apt-get update && apt-get install -y maven
RUN apt-get update && apt-get install -y curl

WORKDIR /app/api-gateway
COPY . /app

EXPOSE 8888
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/api-gateway/target/api-gateway-1.0-SNAPSHOT.jar"]
