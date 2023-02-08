FROM openjdk:11-jdk

ARG JAR_FILE=./build/libs/pjt2-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
