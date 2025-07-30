FROM openjdk:21-slim AS builder

# Install dependencies
RUN apt-get update && apt-get install -y maven
RUN apt-get update && apt-get install -y curl

WORKDIR /app/discovery-server
COPY . /app

# Build the JAR
RUN mvn package -Dmaven.test.skip=true

EXPOSE 8761
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/discovery-server/target/discovery-server-1.0-SNAPSHOT.jar"]
