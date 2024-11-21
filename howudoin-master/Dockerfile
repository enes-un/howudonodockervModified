FROM openjdk:17-jdk-slim
WORKDIR /usr/src/java-app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/usr/src/java-app/app.jar"]