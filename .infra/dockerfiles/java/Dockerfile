FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY build/libs/*.jar service.jar
ENTRYPOINT ["java","-jar","/app/service.jar"]