FROM openjdk:21
LABEL authors="jmlopez"

#Carpeta destino dentro de la imagen
WORKDIR /app

COPY ./target/msvc-cursos-0.0.1-SNAPSHOT.jar /app

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "msvc-cursos-0.0.1-SNAPSHOT.jar"]