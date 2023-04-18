FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/*.jar

COPY ${JAR_FILE} /sejongmate.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/sejongmate.jar"]