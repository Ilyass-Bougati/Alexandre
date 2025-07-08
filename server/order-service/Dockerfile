FROM openjdk:17-slim AS builder

# Install dependencies
RUN apt-get update && apt-get install -y \
    maven \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app/order-service
COPY . /app

# Build the JAR
RUN mvn package -Dmaven.test.skip=true

EXPOSE 10001
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/order-service/target/order-service-1.0-SNAPSHOT.jar"]
