# Build multi-stage: compila com Maven e roda em JVM (modo mais simples de configurar no Render)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src src
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/target/quarkus-app/lib/ ./lib/
COPY --from=build /workspace/target/quarkus-app/*.jar ./
COPY --from=build /workspace/target/quarkus-app/app/ ./app/
COPY --from=build /workspace/target/quarkus-app/quarkus/ ./quarkus/

# Render injeta a variavel PORT -- o application.yml le PORT via ${PORT:8080}
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]
