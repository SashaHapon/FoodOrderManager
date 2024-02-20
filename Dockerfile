# Используйте образ JDK для сборки
FROM maven:3.9.6-eclipse-temurin-17 as builder
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src
RUN mvn -f /app/pom.xml -B package -DskipTests

# Используйте образ JRE для запуска приложения
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8080
#COPY target/food-order-manager-1.0-SNAPSHOT.jar food-order-manager.jar
COPY --from=builder /app/target/food-order-manager-1.0-SNAPSHOT.jar food-order-manager.jar
ENTRYPOINT ["java", "-jar", "/app/food-order-manager.jar"]
