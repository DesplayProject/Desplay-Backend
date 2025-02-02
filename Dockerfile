FROM amazoncorretto:17-alpine-jdk
RUN sudo chmod 755 /file-save
RUN mkdir -p /file-save
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
