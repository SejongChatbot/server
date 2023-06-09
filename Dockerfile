FROM openjdk:17.0.1-jdk-slim

RUN apt-get -y update

RUN apt -y install wget

RUN apt -y install unzip

RUN apt -y install curl

RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb

RUN apt -y install ./google-chrome-stable_current_amd64.deb

RUN wget -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/` curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_linux64.zip

RUN unzip /tmp/chromedriver.zip chromedriver -d /usr/bin

ARG JAR_FILE=/build/libs/sejongmate-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /sejongmate.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/sejongmate.jar"]