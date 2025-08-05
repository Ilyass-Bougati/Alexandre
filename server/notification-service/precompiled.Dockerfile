FROM alpine:latest AS builder

# Install curl and OpenJDK (e.g., OpenJDK 17)
RUN apk add --no-cache curl openjdk17

# Set environment variable for Java
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

WORKDIR /app
COPY ./notification-service/target/notification-service-1.0-SNAPSHOT.jar /app


EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/notification-service-1.0-SNAPSHOT.jar"]