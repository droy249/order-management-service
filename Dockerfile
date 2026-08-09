# Phase 1: Lightweight runtime packaging layout
FROM eclipse-temurin:21-jre-alpine

# Set deployment working directory inside the container
WORKDIR /app

# Copy the compiled executable Jar from the runner build target
COPY target/*.jar app.jar

# Expose standard enterprise system engine application traffic port
EXPOSE 8080

# Configure production engine execution script boundaries
ENTRYPOINT ["java", "-jar", "app.jar"]
