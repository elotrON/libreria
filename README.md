# Vamos a una crear una biblioteca sencilla pero suficientemente rica para tener casos interesantes.

## Objetivo:
Aprender habilidades basicas en 
  - JUnit
  - Mockito
  - servicios
  - repositorios
  - Algo de lógica de negocio, haría 

## Entidades
### Libro

Representa un libro físico de la biblioteca.

Campos:
* Identificador
* ISBN
* Título
* Autor
* Año de publicación
* Categoría
* Disponible (sí/no)

### Usuario

Representa una persona registrada en la biblioteca.

Campos:

* Identificador
* Nombre
* Apellidos
* Email
* Fecha de alta
* Activo (sí/no)
* Penalizado (sí/no)


### Préstamo

Representa el préstamo de un libro a un usuario.

Campos:

* Identificador
* Libro
* Usuario
* Fecha de préstamo
* Fecha prevista de devolución
* Fecha real de devolución
* Estado (activo, devuelto, retrasado)

## Funcionalidad básica
### Gestión de libros

Permite:

* Dar de alta libros.
* Consultar libros.
* Modificar datos de un libro.
* Dar de baja libros.

Reglas:

* No puede haber dos libros con el mismo ISBN.
* Un libro prestado no puede eliminarse.

### Gestión de usuarios

Permite:

* Dar de alta usuarios.
* Consultar usuarios.
* Modificar usuarios.
* Desactivar usuarios.

Reglas:

* No puede haber dos usuarios con el mismo email.
* Un usuario desactivado no puede pedir préstamos.

### Gestión de préstamos

Permite:

* Prestar un libro.
* Devolver un libro.
* Consultar préstamos.

Reglas:

* Solo se pueden prestar libros disponibles.
* Solo usuarios activos pueden pedir préstamos.
* Usuarios penalizados no pueden pedir préstamos.
* Al prestar un libro pasa a "no disponible".
* Al devolverlo vuelve a "disponible".

### Reglas de negocio interesantes para testear

Estas son las que te harán aprender testing de verdad.


* Si un libro ya está prestado: No se puede volver a prestar.
* Si el usuario está penalizado: No se puede realizar el préstamo.
* Si el usuario está inactivo: No se puede realizar el préstamo.
* Al devolver un libro: 
  * El préstamo cambia a DEVUELTO. 
  * El libro vuelve a estar disponible.
* Si la devolución supera la fecha prevista: El usuario queda penalizado.

### Servicios que existirán
* LibroService: Responsable de gestionar libros.
* UsuarioService: Responsable de gestionar usuarios.
* PrestamoService: Responsable de la lógica de préstamos.

Aquí estará la mayor parte de los tests unitarios y de Mockito.

Qué aprenderás con este ejercicio
JUnit
assertEquals
assertTrue
assertFalse
assertThrows
Mockito
Mockear repositorios.
Simular respuestas de repositorios.
Verificar llamadas.
Simular errores.
Spring Boot
Services
Repositories
DTOs (si más adelante quieres añadirlos)
Excepciones de negocio

Es un proyecto lo bastante pequeño para terminarlo en pocos días, 
pero lo bastante completo para practicar casi todo lo que se usa habitualmente en una API Spring Boot.