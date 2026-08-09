# ==========================================
# Phase 1: Build and Compile the Java Code
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Set working directory for compilation
WORKDIR /build

# Copy the build configuration file first to leverage Docker caching layers
COPY pom.xml .

# Copy the actual application source code
COPY src ./src

# Compile and package the enterprise executable Jar, skipping tests during build phase
RUN mvn clean package -DskipTests

# ==========================================
# Phase 2: Lightweight Production Runtime
# ==========================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the compiled executable Jar directly from the temporary build container phase
COPY --from=build /build/target/*.jar app.jar

# Expose standard enterprise system application traffic port
EXPOSE 8080

# Execute production runtime script boundary
ENTRYPOINT ["java", "-jar", "app.jar"]
