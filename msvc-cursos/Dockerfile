ARG MSVC_NAME=msvc-cursos

FROM openjdk:21-jdk-slim as builder

ARG MSVC_NAME

LABEL authors="jmlopez"

#Carpeta destino dentro de la imagen
WORKDIR /app/$MSVC_NAME

COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

#No compila ni ejecuta el test. Solo se usa para descargar las dependencias. Se empaqueta el proyecto pero sin el codigo fuente.
RUN  ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#otra Forma para empaquetar solo dependencias
#RUN ./mvnw dependency:go-offline
COPY ./$MSVC_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk-slim
ARG MSVC_NAME
WORKDIR /app

ARG TARGET_FOLDER=/app/$MSVC_NAME/target

COPY --from=builder $TARGET_FOLDER/$MSVC_NAME-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8002

ENV PORT $PORT_APP

EXPOSE $PORT

CMD sleep 20 && java -jar msvc-cursos-0.0.1-SNAPSHOT.jar
#Alternativa para que en el servicio de aws se inicie primero la base de datos

#ENTRYPOINT ["java", "-jar", "msvc-cursos-0.0.1-SNAPSHOT.jar"]