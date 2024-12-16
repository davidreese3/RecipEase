FROM openjdk:17-jdk-slim AS build
COPY ./pom.xml ./pom.xml
COPY ./mvnw ./mvnw
COPY .mvn .mvn
COPY ./src ./src
RUN ./mvnw package

FROM openjdk:17-jdk-slim
COPY --from=build ./target/*.jar spring-jdbc.jar
ENTRYPOINT ["java", "-jar", "spring-jdbc.jar"]
