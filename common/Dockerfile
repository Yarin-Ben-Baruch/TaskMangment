FROM openjdk:18

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/common

COPY ${JAR_FILE} /opt/common/app.jar

ENTRYPOINT ["java", "-jar", "/opt/common/app.jar.jar"]