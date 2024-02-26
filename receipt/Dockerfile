FROM maven:3.9.6-eclipse-temurin-17 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8090
COPY --from=builder /app/target/receipt-0.0.1-SNAPSHOT.jar receipt.jar
ENTRYPOINT ["java", "-jar", "/app/receipt.jar"]
