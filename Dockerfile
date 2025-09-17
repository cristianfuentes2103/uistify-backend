FROM openjdk:17-jdk-alpine

EXPOSE 8080

COPY target/*.jar app.jar

RUN useradd -r -u 10001 appuser
USER 10001

ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-Djava.security.egd=file:/dev/./urandom","-Dfile.encoding=UTF-8","-Dsun.jnu.encoding=UTF-8","-jar","app.jar"]
