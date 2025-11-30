# --- Build stage: compile with Maven (JDK 17) ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# copy only pom first to cache dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# copy source and build
#COPY . .
COPY src ./src
RUN mvn clean package -DskipTests -B
RUN #mvn clean package

# --- Run stage: small runtime (JRE 17) ---
FROM eclipse-temurin:17-jre
WORKDIR /app


# copy the JAR produced by the build stage
COPY --from=build /app/target/*.jar ./app.jar

EXPOSE 4002

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
