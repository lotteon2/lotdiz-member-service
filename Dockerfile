FROM openjdk:11-slim-buster

WORKDIR /app

ARG ORIGINAL_JAR_FILE=./build/libs/member-service-1.0.0.jar

COPY ${ORIGINAL_JAR_FILE} member-service.jar

CMD ["java", "-jar", "/app/member-service.jar"]
