# Dockerfile

FROM java:8-jdk-alpine

MAINTAINER  Bryant Collaguazo <pumabryant@gmail.com>

ENV TOKEN = ""

COPY /target/discordBot-jar-with-dependencies.jar /discordBot.jar

ENTRYPOINT java -jar /discordBot.jar ${TOKEN}