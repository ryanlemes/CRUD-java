FROM adoptopenjdk/maven-openjdk11

COPY . .

RUN mvn clean package
RUN mvn test

WORKDIR ./target

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "desafio-inter.jar"]
