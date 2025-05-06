# Use the official Gradle image to build the app
FROM gradle:8.5-jdk21 AS build

# Copy the project files into the Docker image
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

# Build the app (creates build/libs/*.jar)
RUN gradle build -x test

# Now create the final image with just the jar
FROM eclipse-temurin:21-jre

# Set working directory in final container
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

# Expose port (make sure matches your app's port)
EXPOSE 3000

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]
