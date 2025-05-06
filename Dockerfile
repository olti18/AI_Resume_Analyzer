#FROM gradle:8.5-jdk23 AS build
#
#COPY --chown=gradle:gradle . /home/gradle/project
#WORKDIR /home/gradle/project
#
#RUN gradle build -x test
#
#FROM eclipse-temurin:23-jre
#
#WORKDIR /app
#
#COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
#
#EXPOSE 3000
#
#ENTRYPOINT ["java","-jar","app.jar"]
# Build stage with Java 23 + Gradle 8.5
FROM eclipse-temurin:23-jdk AS build-env

RUN apt-get update && apt-get install -y curl unzip

# Install Gradle 8.5 manually
RUN curl -sSLo gradle.zip https://services.gradle.org/distributions/gradle-8.5-bin.zip && \
    unzip gradle.zip -d /opt && \
    ln -s /opt/gradle-8.5 /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle

# Set up user and working directory
RUN useradd -ms /bin/bash gradle
USER gradle
WORKDIR /home/gradle/project

# Copy project files
COPY --chown=gradle:gradle . .

# Run build
RUN gradle build -x test

# Final image to run app
FROM eclipse-temurin:23-jre

WORKDIR /app

COPY --from=build-env /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "app.jar"]
