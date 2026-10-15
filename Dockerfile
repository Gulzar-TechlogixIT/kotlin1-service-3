FROM gradle:8.14-jdk21 AS build
WORKDIR /workspace

COPY kot_lib_3/ /workspace/kot_lib_3/
RUN gradle -p /workspace/kot_lib_3 publishToMavenLocal --no-daemon

COPY kotlin1-service-3/ /workspace/kotlin1-service-3/
RUN gradle -p /workspace/kotlin1-service-3 installDist --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/kotlin1-service-3/build/install/kotlin1-service-3/ /app/
EXPOSE 8083
CMD ["/app/bin/kotlin1-service-3"]