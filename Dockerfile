FROM openjdk:21-jdk-slim-buster
WORKDIR /app

COPY /build/libs/time-tarcker.jar build/

WORKDIR /app/build
EXPOSE 8091
ENTRYPOINT java -jar time-tarcker.jar