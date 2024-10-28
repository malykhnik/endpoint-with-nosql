FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/endpoint-with-nosql-0.0.1-SNAPSHOT.jar /app/endpoint-with-nosql.jar

ENTRYPOINT ["java", "-jar", "endpoint-with-nosql.jar"]