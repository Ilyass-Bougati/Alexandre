FROM openjdk:21-slim AS builder

# Install dependencies
RUN apt-get update && apt-get install -y curl

WORKDIR /app/delivery-service
COPY . /app

EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/delivery-service/target/delivery-service-1.0-SNAPSHOT.jar"]
