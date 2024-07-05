# Use the official Eclipse Temurin JDK 22 as the base image
FROM eclipse-temurin:22-jdk-alpine AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and Gradle build files
COPY gradle /app/gradle
COPY gradlew /app/
COPY build.gradle /app/
COPY settings.gradle /app/

# Copy the source code
COPY src /app/src

# Grant execute permission to the Gradle wrapper
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew clean build

# Use a minimal base image for the runtime
FROM eclipse-temurin:22-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Expose the application port (change this if your app runs on a different port)
EXPOSE 8009

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
