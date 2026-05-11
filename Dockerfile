# Stage 1: Build the application using Gradle wrapper
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /workspace/app

# Copy gradle files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Make gradlew executable and build the application
RUN chmod +x gradlew
RUN ./gradlew build -x test --no-daemon

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-jammy
VOLUME /tmp
WORKDIR /app

# Copy the built jar file
COPY --from=build /workspace/app/build/libs/*SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
