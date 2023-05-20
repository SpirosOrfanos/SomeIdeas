FROM openjdk:17-alpine
EXPOSE 9090
ARG JAR_FILE=target/app.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]