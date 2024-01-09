FROM gradle:8.3-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle clean build --exclude-task test

FROM openjdk:17-jdk

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/telephony-0.0.1-SNAPSHOT.jar /app/telephony-app.jar

ENTRYPOINT ["java", "-jar", "/app/telephony-app.jar"]
