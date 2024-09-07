# Build stage


#COPY . /echo
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package

FROM maven:3-openjdk-15-slim AS build
COPY . /echo
WORKDIR /echo
RUN mvn package
# syntax=docker/dockerfile:1
FROM openjdk:15-slim
COPY --from=build /echo/target/echo-client.jar /echo/echo-client.jar
WORKDIR /echo
#RUN ["javac", "Client.java"]
ENTRYPOINT java -jar echo-client.jar


