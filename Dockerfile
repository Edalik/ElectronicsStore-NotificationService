FROM amazoncorretto:17.0.14-alpine
EXPOSE 8080
COPY ./build/libs/*.jar notification-service.jar
ENTRYPOINT ["java", "-jar", "/notification-service.jar"]