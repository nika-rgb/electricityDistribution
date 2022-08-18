FROM openjdk:17-alpine
ARG PACKAGE_NAME=electricity_distribution

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src
ENV PACKAGE_NAME ${PACKAGE_NAME}
CMD ["./mvnw", "spring-boot:run"]