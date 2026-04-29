# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
# Move into the project folder where pom.xml lives
WORKDIR /project
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jdk-alpine
# Update the path to find the jar inside the project/target folder
COPY --from=build /project/target/*.jar app.jar
EXPOSE 10000
ENTRYPOINT ["java", "-jar", "app.jar"]
