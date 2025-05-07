# Base image me Ubuntu 22.04
FROM ubuntu:22.04 as build

# Install dependencies + OpenJDK 23 + Gradle
RUN apt-get update && \
    apt-get install -y wget unzip curl gnupg2 software-properties-common && \
    curl -fsSL https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor -o /usr/share/keyrings/adoptium-archive-keyring.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/adoptium-archive-keyring.gpg] https://packages.adoptium.net/artifactory/deb jammy main" | tee /etc/apt/sources.list.d/adoptium.list && \
    apt-get update && \
    apt-get install -y temurin-23-jdk && \
    java -version && \
    wget https://services.gradle.org/distributions/gradle-8.13-bin.zip && \
    unzip gradle-8.13-bin.zip -d /opt && \
    ln -s /opt/gradle-8.13/bin/gradle /usr/bin/gradle

# Vendos direktorinë e punës
WORKDIR /app
COPY . .

# Build projekti pa testet
RUN gradle build -x test

# Stage për runtime
FROM ubuntu:22.04

RUN apt-get update && \
    apt-get install -y curl gnupg2 software-properties-common && \
    curl -fsSL https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor -o /usr/share/keyrings/adoptium-archive-keyring.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/adoptium-archive-keyring.gpg] https://packages.adoptium.net/artifactory/deb jammy main" | tee /etc/apt/sources.list.d/adoptium.list && \
    apt-get update && \
    apt-get install -y temurin-23-jre && \
    java -version

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]





## Use official OpenJDK 23 slim image
#FROM eclipse-temurin:23-jdk-jammy as build
#
## Install Gradle
#RUN apt-get update && apt-get install -y curl unzip && \
#    curl -sLo gradle.zip https://services.gradle.org/distributions/gradle-8.13-bin.zip && \
#    unzip gradle.zip -d /opt && \
#    ln -s /opt/gradle-8.13/bin/gradle /usr/bin/gradle
#
## Set working directory
#WORKDIR /app
#
## Copy project files
#COPY . .
#
## Build the project
#RUN gradle build -x test
#
## Second stage: smaller image for running app
#FROM eclipse-temurin:23-jre-jammy
#
#WORKDIR /app
#
## Copy built jar from previous stage
#COPY --from=build /app/build/libs/*.jar app.jar
#
## Expose port
#EXPOSE 8080
#
## Run the jar
#ENTRYPOINT ["java", "-jar", "app.jar"]
