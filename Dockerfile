
FROM gradle:8.5-jdk23 AS build

COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

RUN gradle build -x test

FROM eclipse-temurin:23-jre

WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 3000

ENTRYPOINT ["java","-jar","app.jar"]

