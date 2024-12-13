# Forohub - API de Foro con Spring Boot

## Descripción
Forohub es una API REST desarrollada con Spring Boot que implementa un sistema de gestión de foros con autenticación JWT.

## Características
- Autenticación y autorización con JWT
- CRUD de usuarios
- CRUD de tópicos
- Seguridad implementada con Spring Security
- Validación de datos
- Paginación de resultados

## Tecnologías Utilizadas
- Java 17
- Spring Boot 3.x
- Spring Security
- JWT
- Hibernate/JPA
- Maven

## Requisitos Previos
- JDK 17
- Maven
- Base de datos (configurada en `application.properties`)

## Configuración
1. Clonar el repositorio
2. Configurar las credenciales de base de datos en `application.properties`
3. Ejecutar `mvn clean install`
4. Iniciar la aplicación con `mvn spring-boot:run`

## Endpoints Principales
- `POST /login`: Autenticación de usuarios
- `GET /usuarios`: Listar usuarios
- `GET /topicos`: Listar tópicos
- `POST /topicos`: Crear nuevo tópico
- `DELETE /usuarios/eliminar-por-id/{id}`: Eliminar usuario

## Autenticación
Todos los endpoints excepto `/login` requieren un token JWT válido en el header de Authorization.

## Contribuciones
Las contribuciones son bienvenidas. Por favor, abre un issue o realiza un pull request.

## Licencia
[Especificar la licencia]
