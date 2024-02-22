# Используйте образ JDK для сборки
FROM maven:3.9.6-eclipse-temurin-17 as builder
WORKDIR /app
COPY /order-manager/. .
RUN mvn -f /app/pom.xml -B package -DskipTests

# Используйте образ JRE для запуска приложения
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8080
#COPY /app/order-manager/target/order-manager-1.0-SNAPSHOT.jar order-manager.jar
COPY --from=builder /app/target/order-manager-1.0-SNAPSHOT.jar order-manager.jar
ENTRYPOINT ["java", "-jar", "/app/order-manager.jar"]
