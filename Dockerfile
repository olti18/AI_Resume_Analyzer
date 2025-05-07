# Use official OpenJDK 23 slim image
FROM eclipse-temurin:23-jdk-jammy as build

# Install Gradle
RUN apt-get update && apt-get install -y curl unzip && \
    curl -sLo gradle.zip https://services.gradle.org/distributions/gradle-8.13-bin.zip && \
    unzip gradle.zip -d /opt && \
    ln -s /opt/gradle-8.13/bin/gradle /usr/bin/gradle

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the project
RUN gradle build -x test

# Second stage: smaller image for running app
FROM eclipse-temurin:23-jre-jammy

WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
