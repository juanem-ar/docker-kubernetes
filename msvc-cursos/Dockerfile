FROM openjdk:21-jdk-slim as builder
LABEL authors="jmlopez"

#Carpeta destino dentro de la imagen
WORKDIR /app/msvc-cursos

COPY ./pom.xml /app
COPY ./msvc-cursos/.mvn ./.mvn
COPY ./msvc-cursos/mvnw .
COPY ./msvc-cursos/pom.xml .

#No compila ni ejecuta el test. Solo se usa para descargar las dependencias. Se empaqueta el proyecto pero sin el codigo fuente.
RUN  ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#otra Forma para empaquetar solo dependencias
#RUN ./mvnw dependency:go-offline
COPY ./msvc-cursos/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/msvc-cursos/target/msvc-cursos-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "msvc-cursos-0.0.1-SNAPSHOT.jar"]