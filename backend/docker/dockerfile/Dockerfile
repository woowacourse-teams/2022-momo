FROM openjdk:11-jre-slim

ARG JAR_FILE=momo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENV PROFILE prod

EXPOSE 8080

RUN mkdir -p momo

CMD java -jar app.jar --spring.profiles.active="${PROFILE}" >>/momo/application.log 2>/dev/null
