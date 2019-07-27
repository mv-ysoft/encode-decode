FROM openjdk:11.0.4-jdk-slim
ARG JAR_FILE
COPY ${JAR_FILE} encode-decode.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/encode-decode.jar"]