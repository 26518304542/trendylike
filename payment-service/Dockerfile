FROM eclipse-temurin:17-jdk-jammy
ARG JAR_FILE=target/*.jar

# install curl for healthcheck probes (keeps image small)
USER root
RUN apt-get update && apt-get install -y curl ca-certificates && apt-get clean && rm -rf /var/lib/apt/lists/*

COPY ${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app.jar"]
