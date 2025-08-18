# -------- Build stage --------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src
# 先拉依賴避免每次全量重建
RUN ./gradlew --no-daemon clean bootJar

# -------- Run stage --------
FROM eclipse-temurin:17-jre
WORKDIR /app
# 把 jar 複製過來
COPY --from=build /app/build/libs/*-SNAPSHOT.jar /app/app.jar
# 健康檢查（等會兒 compose 會用）
HEALTHCHECK --interval=30s --timeout=10s --retries=5 \
  CMD curl -fs http://localhost:8080/actuator/health || exit 1
# 服務埠
EXPOSE 8080
# 可選 JVM 參數
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
