FROM openjdk:17-jdk-alpine
EXPOSE 8081
VOLUME /tmp
ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]