FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} customers-java.jar
ENTRYPOINT ["java","-jar","/customers-java.jar","--server.port=8081"]
EXPOSE 8081