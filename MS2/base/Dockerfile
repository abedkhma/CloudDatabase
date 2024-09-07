# Build stage


#COPY . /echo
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package

FROM maven:3-openjdk-15-slim AS build
COPY . /echo
WORKDIR /echo
RUN mvn package

FROM openjdk:15-slim
COPY --from=build /echo/target/kv-server.jar /echo/kv-server.jar
WORKDIR /echo
ENTRYPOINT java -jar kv-server.jar


#FROM openjdk:15-slim
#COPY --from=build /echo/target/nio-echo-server.jar /echo/nio-echo-server.jar
#WORKDIR /echo
#ENTRYPOINT java -jar nio-echo-server.jar


FROM openjdk:15-slim
COPY --from=build /echo/target/echo-client.jar /echo/echo-client.jar
WORKDIR /echo
ENTRYPOINT java -jar echo-client.jar


