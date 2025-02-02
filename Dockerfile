FROM openjdk:17-jdk
RUN chmod 755 /file-save
RUN mkdir -p /file-save
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
