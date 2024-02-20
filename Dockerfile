FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM maven:3.8.4-openjdk-17-slim
COPY --from=build /target/assn2-0.0.1-SNAPSHOT.jar assn2.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","assn2.jar"]
