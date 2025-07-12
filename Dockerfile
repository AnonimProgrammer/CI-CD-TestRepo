FROM openjdk:21-jdk-slim
EXPOSE 8080
WORKDIR /app
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]