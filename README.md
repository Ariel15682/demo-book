## Spring Boot

Proyecto Spring Boot con las dependencias: 
* Spring Web
* Spring Boot Dev tools

y los starters para persistencia:
* H2
* Spring Data JPA

Se genera tambien fichero .gitignore (IntelliJ, Maven, Java)

El acceso se puede realizar desde Postman o Navegador.

## Entidad Book

1. Crear entidad 
* Atributos
* Constructores: Vacio y sobrecargado
* Getters y Setters
* Metodo toString()
* Equals & Hash
2. BookRepository (almacenar books en BB.DD)
3. BookController (acceder a traves de una URL)
   a. Buscar todos los libros
   b. Buscar un solo libro
   c. Crear un  nuevo libro
   d. Actualizar un libro existente
   e. Borrar un libro
   d. Borrar todos los libros
4. Fichero data.sql con el dato de un libro para hacer un INSERT luego de creado el
esquema/modelo con JPA e Hibernate. Con este fichero, por mas que su script este 
comentado, el fichero application.properties debe estar configurado; 
spring.sql.init.mode=never y spring.jpa.hibernate.ddl-auto=update para que no de
error en cada inicio al intentar crear el esquema de BD cada vez estando ya creado.
5. Configuracion de Swagger:
* clase SwaggerConfig: @EnableSwagger2; Probada en version 2.55 Spring (OK), en
version 2.7 con javax.persistence (FAIL), 3.1 con Jakarta.persistence (FAIL)
* Dependencias:
pom.xml

Con esta unica:
````xml
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
            <version>3.0.0</version>
        </dependency>
````
Con estas otras:
````xml
<dependencies> 
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
</dependencies>   
````
Tener en cuenta que frameworks (Spring/jersey) y que versiones se estan utilizando
al momento de cargar las dependencias. Estas dependencias cargan otras que son 
necesarias como swagger annotation, models, core, jaxrs(x)...

6. Se crea clase test para el controlador de libro (BookControllerTest)
7. En esta aplicacion se utilizaron anotaciones propias de Test como por ej. 
@DisplayName; para dar un nombre elegido a los test, de Hibernate como @Table y 
@Column; para el nombre de las tablas y columnas, de SpringFox/Swagger como
ser @ApiOperation, @ApiParam, @ApiModel, @ApiModelProperty.
Se utilizo tambien @GeneratedValue; para generar claves primaria (Id), Objetos
Optionals, ResponseEntity para manejar las respuestas HTTP, la interfaz Logger
 