FROM openjdk:8
MAINTAINER Prashanth
ARG JAR_FILE=target/client-1.0-gameofthree.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080