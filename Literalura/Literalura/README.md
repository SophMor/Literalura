# Proyecto Literalura
Proyecto de consola con Spring Boot para consultar información sobre libros.

## Requisitos
Antes de usar el proyecto, asegúrate de tener lo siguiente:

JDK 11 o superior instalado en tu máquina.
PostgreSQL configurado y ejecutándose en tu entorno.
Maven instalado para gestionar las dependencias del proyecto.
## Pasos para configurar y ejecutar el proyecto
1. Crear la base de datos llamada Literalura
2. Importar como maven el proyecto
3. Configurar en ApplicationProperties
   * spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
   *   spring.datasource.username=tu_usuario
   *    spring.datasource.password=tu_contraseña
5. Ejecutar la clase DesafioLiteralura
