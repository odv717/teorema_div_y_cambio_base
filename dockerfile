FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY src/core/ ./src/core/

RUN javac -d out src/core/*.java

EXPOSE 8080

CMD ["java", "-cp", "out", "core.Main"]