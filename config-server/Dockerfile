FROM  openjdk:18-alpine
RUN apk --update --no-cache add curl
COPY /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]