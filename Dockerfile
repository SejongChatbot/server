FROM openjdk:17-alpine

RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm

RUN yum install google-chrome-stable_current_x86_64.rpm

RUN wget -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/ curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_linux64.zip

RUN unzip /tmp/chromedriver.zip chromedriver -d /usr/bin

ARG JAR_FILE=/build/libs/sejongmate-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /sejongmate.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/sejongmate.jar"]