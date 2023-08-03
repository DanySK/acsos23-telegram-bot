FROM eclipse-temurin:20.0.2_9-jdk-alpine as builder
COPY . /app
WORKDIR /app
RUN sed -i 's/alias(libs.plugins.gitSemVer)//' build.gradle.kts
RUN ./gradlew shadowJar

FROM eclipse-temurin:20.0.2_9-jre-alpine as app
COPY --from=builder /app/build/libs/*-all.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
