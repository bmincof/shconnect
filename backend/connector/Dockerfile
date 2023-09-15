FROM openjdk:11-jdk
LABEL maintainer="dadada0228"
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} connector-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/connector-0.0.1-SNAPSHOT.jar"]