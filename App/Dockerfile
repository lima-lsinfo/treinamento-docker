FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu as build

RUN apt-get update && apt-get install -y maven

RUN mkdir /opt/app

WORKDIR /opt/app

COPY java-docker/pom.xml ./

RUN mvn dependency:go-offline

COPY java-docker/src ./src

RUN mvn clean install -DskipTests

FROM openjdk:21-slim as final
# ARG JAR_FILE=target/*.jar

COPY --from=build /opt/app/target/docker-0.0.1-SNAPSHOT.jar app.jar
# COPY --from=build ${JAR_FILE} app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
