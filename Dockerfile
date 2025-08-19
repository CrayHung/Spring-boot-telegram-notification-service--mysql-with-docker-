# ===== Stage 1: Build =====
FROM gradle:8.8-jdk17 AS builder
WORKDIR /home/gradle/src
COPY . .
RUN gradle clean bootJar -x test

# ===== Stage 2: Runtime =====
FROM eclipse-temurin:17-jre
ENV TZ=Asia/Taipei \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
WORKDIR /app
COPY --from=builder /home/gradle/src/build/libs/*-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
