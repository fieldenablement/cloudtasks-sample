FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY ${JAR_FILE} demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/demo-0.0.1-SNAPSHOT.jar"]