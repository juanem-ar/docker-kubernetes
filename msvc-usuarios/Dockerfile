FROM openjdk:21
LABEL authors="jmlopez"

#Carpeta destino dentro de la imagen
WORKDIR /app/msvc-usuarios

COPY ./pom.xml /app
COPY ./msvc-usuarios/.mvn ./.mvn
COPY ./msvc-usuarios/mvnw .
COPY ./msvc-usuarios/pom.xml .

#No compila ni ejecuta el test. Solo se usa para descargar las dependencias. Se empaqueta el proyecto pero sin el codigo fuente.
RUN  ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#otra Forma para empaquetar solo dependencias
#RUN ./mvnw dependency:go-offline
COPY ./msvc-usuarios/src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "./target/msvc-usuarios-0.0.1-SNAPSHOT.jar"]