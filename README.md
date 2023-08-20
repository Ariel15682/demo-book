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
Dependencias:
pom.xml

````xml
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
            <version>3.0.0</version>
        </dependency>
````

Tener en cuenta que frameworks (Spring/jersey) y que versiones se estan utilizando
al momento de cargar las dependencias. Estas dependencias cargan otras que son 
necesarias como swagger annotation, models, core, jaxrs(x)...

6. Se crea clase test para el controlador de libro (BookControllerTest)

En esta aplicacion se utilizo: 
* Testing: @DisplayName; para dar un nombre custom a los test. 
* Hibernate; @Table y @Column; para el nombre de las tablas y columnas.
* SpringFox/Swagger: @GeneratedValue; para generar claves primaria (Id), Objetos
  Optionals, ResponseEntity para manejar las respuestas HTTP, la interfaz Logger.
  @ApiOperation, @ApiParam, @ApiModel, @ApiModelProperty.

## Spring Security
Se implementa Spring Boot starter Security en su version 3.1 con Spring Boot 2.5.5. 
Encontramos que en versiones de Spring Boot posteriores a 3 algunas clases fueron 
deprecadas.

### Novedades:
La clase abstracta WebSecurityConfigurerAdapter, de la cual extendia nuestra clase 
de configuracion de la seguridad (WebSecurityConfig) en SpringBoot 2.5 fue deprecada en 
Spring Boot 2.7. En consecuencia, si utilizaramos esa version de Spring Boot nuestra 
clase de configuracion ya no extiende de ninguna otra clase, el metodo 
AuthenticationManager() que gestiona la seguridad y roles de los usuarios ya no es 
de tipo "void" sino que retorna un valor de ese tipo "AuthenticationManager". 
Lo mismo para el metodo de configuracion de HttpSecurity, ya no es de tipo void sino que
retorna un objeto de tipo "SecurityFilterChain". Esta ultima nos provee de un sistema 
de "login" para restringir el acceso a algun area de la aplicacion sin antes autenticarse.
Al inicio de la aplicacion Spring  genera el password en consola de forma "random"
(ej. cc5db156-2789-4c72-8ab3-f2bcec483b49) el usuario siempre es "user". 
Las credenciales se pueden "customizar"; 
incluir en el fichero:

main/resources/application.properties

```` properties
# Reemplaza usuario y contraseña que otorga Spring por consola al iniciar la aplicacion
spring.security.user.name=ariel
spring.security.user.password=12345

# Granted roles for the default user name.
spring.security.user.roles=
````
La otra forma de gestionar usuarios y roles es con AuthenticationManager en la clase 
de configuracion como codigo.

Se implemento tambien un metodo para ignorar la seguridad en los test;
````
    @Bean
    @Profile("test")
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/**");
    }
````
Acompañado de la anotacion ``@ActiveProfiles("test")`` en la clase de testeo.

Se implemento ademas BCryptPasswordEncoder para cifrar la contraseña.

---
### Actualizacion
Al momento en que me encuentro, Agosto de 2023, con Spring Boot 3.1 se deprecaron y en
otros casos cambio la implementacion de los metodos; authorizeRequests() por 
authorizeHttpRequests(Customizer <AutorizationManagerRequestMatcherRegistry> 
authorizeHttpRequestCustomizer), antMatchers() por requestMatchers(), formLogin() por
formLogin(Customizer<FormLoginConfigurer<HttpSecurity>> formLoginCustomizer) y 
httpBasic() por httpBasic(Customizer<HttpBasicConfigurer<HttpSecurity>> httpBasicCustomizer)
todos de la clase HttpSecurity con la dependencia Spring Boot starter Security y
utilizados en el metodo de configuracion que gestiona la seguridad Http, dentro de
nuestra clase WebSecurityConfig. 

Dentro del paquete "config" se adjunta un fichero WebSecurity actualizada para Spring Boot
2.7, muy similar a su version anterior. Las dependencias utilizadas son Spring Boot Starter
Security o las que se detallan abajo.

````xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-core -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-core</artifactId>
        <version>6.1.0</version>
    </dependency>
    
    <!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-web -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-web</artifactId>
        <version>6.1.0</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-config -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-config</artifactId>
        <version>6.1.0</version>
    </dependency>

</dependencies>
````

Se adjunta al pie del fichero WebSecurity.txt la actualizacion para Spring Boot 3.1