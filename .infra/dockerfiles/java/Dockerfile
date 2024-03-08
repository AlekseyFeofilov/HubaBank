FROM gradle:jdk17-alpine as build
WORKDIR /opt/gradle
COPY . .
RUN gradle bootJar -x test

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /opt/app
COPY --from=build /opt/gradle/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/opt/app/app.jar"]