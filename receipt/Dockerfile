FROM maven:3.9.6-eclipse-temurin-17 as builder
WORKDIR /app
COPY /receipt/. .
RUN mvn -f /app/pom.xml -B package -DskipTests

# Используйте образ JRE для запуска приложения
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8080
#COPY target/food-order-manager-1.0-SNAPSHOT.jar food-order-manager.jar
COPY --from=builder /app/target/receipt-0.0.1-SNAPSHOT.jar receipt.jar
ENTRYPOINT ["java", "-jar", "/app/receipt.jar"]
