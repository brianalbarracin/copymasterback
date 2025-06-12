# Usa una imagen base con Java 21
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/tu-unidad-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto de tu aplicación (según tu application.yml)
EXPOSE 8787

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]