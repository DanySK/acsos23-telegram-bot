FROM eclipse-temurin:21.0.5_11-jdk-alpine as builder
COPY . /app
WORKDIR /app
RUN sed -i 's/alias(libs.plugins.gitSemVer)//' build.gradle.kts
RUN ./gradlew shadowJar

FROM eclipse-temurin:21.0.5_11-jre-alpine as app
COPY --from=builder /app/build/libs/*-all.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
