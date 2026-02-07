FROM maven:3.9.6-eclipse-temurin-11

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY testng.xml .

RUN mvn dependency:resolve

CMD ["mvn", "clean", "test", "-DsuiteXmlFile=testng.xml"]
