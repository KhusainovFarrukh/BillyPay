FROM  openjdk:18-alpine
RUN apk --update --no-cache add curl
EXPOSE 8080
COPY /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]