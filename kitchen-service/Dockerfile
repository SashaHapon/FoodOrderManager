FROM maven:3.9.6-eclipse-temurin-17 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8070
COPY --from=builder /app/target/kitchen-service-1.0-SNAPSHOT.jar kitchen-service.jar
ENTRYPOINT ["java", "-jar", "/app/kitchen-service.jar"]
