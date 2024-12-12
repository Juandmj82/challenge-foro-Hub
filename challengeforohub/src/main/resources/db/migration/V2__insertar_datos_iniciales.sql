-- Insertar 10 usuarios
INSERT INTO usuarios (nombre, email, contrasena) VALUES 
('Juan Perez', 'juan.perez@example.com', '$2a$10$sFKmbxBXUeqsG4KtX9aN3.WU8Fon0Qt5EzxVLNMjJvqDCwEJxWDSa'),
('Maria Rodriguez', 'maria.rodriguez@example.com', '$2a$10$sFKmbxBXUeqsG4KtX9aN3.WU8Fon0Qt5EzxVLNMjJvqDCwEJxWDSa'),
('Carlos Gomez', 'carlos.gomez@example.com', '$2a$10$sFKmbxBXUeqsG4KtX9aN3.WU8Fon0Qt5EzxVLNMjJvqDCwEJxWDSa'),
('Ana Sanchez', 'ana.sanchez@example.com', '$2a$10$sFKmbxBXUeqsG4KtX9aN3.WU8Fon0Qt5EzxVLNMjJvqDCwEJxWDSa'),
('Luis Martinez', 'luis.martinez@example.com', '$2a$10$sFKmbxBXUeqsG4KtX9aN3.WU8Fon0Qt5EzxVLNMjJvqDCwEJxWDSa'),
('Sofia Torres', 'sofia.torres@example.com', '$2a$10$sFKmbxBXUeqsG4KtX9aN3.WU8Fon0Qt5EzxVLNMjJvqDCwEJxWDSa'),
('Diego Ramirez', 'diego.ramirez@example.com', '$2a$10$sFKmbxBXUeqsG4KtX9aN3.WU8Fon0Qt5EzxVLNMjJvqDCwEJxWDSa'),
('Laura Garcia', 'laura.garcia@example.com', '$2a$10$sFKmbxBXUeqsG4KtX9aN3.WU8Fon0Qt5EzxVLNMjJvqDCwEJxWDSa'),
('Pedro Lopez', 'pedro.lopez@example.com', '$2a$10$sFKmbxBXUeqsG4KtX9aN3.WU8Fon0Qt5EzxVLNMjJvqDCwEJxWDSa'),
('Elena Fernandez', 'elena.fernandez@example.com', '$2a$10$sFKmbxBXUeqsG4KtX9aN3.WU8Fon0Qt5EzxVLNMjJvqDCwEJxWDSa');

-- Insertar cursos
INSERT INTO cursos (nombre, categoria) VALUES 
('Java Básico', 'Programación'),
('Spring Framework', 'Backend'),
('Desarrollo Web', 'Frontend'),
('Programación Backend', 'Backend');

-- Insertar 10 tópicos
INSERT INTO topicos (titulo, mensaje, fecha_creacion, status, autor_id, curso_id) VALUES 
('Problema con Herencia en Java', 'Estoy teniendo dificultades para entender cómo implementar herencia correctamente', NOW(), 'NO_RESPONDIDO', 1, 1),
('Configuración de Spring Boot', 'Necesito ayuda para configurar un proyecto Spring Boot desde cero', NOW(), 'NO_RESPONDIDO', 2, 2),
('Consultas JPA', 'Cómo realizar consultas personalizadas usando Spring Data JPA', NOW(), 'NO_SOLUCIONADO', 3, 2),
('Diseño de API REST', 'Mejores prácticas para diseñar una API REST con Spring', NOW(), 'NO_RESPONDIDO', 4, 3),
('Manejo de Excepciones', 'Estrategias para manejar excepciones en aplicaciones Java', NOW(), 'NO_SOLUCIONADO', 5, 1),
('Seguridad en Spring', 'Implementación de autenticación JWT en Spring Security', NOW(), 'NO_RESPONDIDO', 6, 2),
('Pruebas Unitarias', 'Cómo escribir pruebas unitarias efectivas con JUnit', NOW(), 'NO_SOLUCIONADO', 7, 4),
('Microservicios', 'Introducción a la arquitectura de microservicios con Spring Cloud', NOW(), 'NO_RESPONDIDO', 8, 3),
('Optimización de Consultas', 'Técnicas para mejorar el rendimiento de consultas en bases de datos', NOW(), 'NO_SOLUCIONADO', 9, 4),
('Desarrollo Frontend', 'Integración de aplicaciones Spring con frameworks frontend', NOW(), 'NO_RESPONDIDO', 10, 3);