FROM openjdk:21
LABEL authors="jmlopez"

#Carpeta destino dentro de la imagen
WORKDIR /app

COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar /app

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]