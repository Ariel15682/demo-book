package com.example.demobook.controllers;

import com.example.demobook.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @DisplayName("Comprobar metodos CRUD desde controladores Spring REST") //mejora la visualizacion de los test
    @Test
    void findAll() {
        //Book[] es en JSON que se pasa a array normal y luego a una lista o array dinamico
        //ResponseEntity es una respuesta que maneja objetos http
        ResponseEntity<Book[]> response = testRestTemplate.getForEntity("/api/books", Book[].class);

        //comprobar aserciones
        assertEquals(HttpStatus.OK, response.getStatusCode()); // = assertEquals(200, response.getStatusCodeValue()); //deprecated

        List<Book> books = Arrays.asList(Objects.requireNonNull(response.getBody())); // Arrays.asList(response.getBody()); //puede producir un null
        System.out.println(books.size());
    }

    // Libro inexistente
    @Test
    void findOneByIdInexistent() {
        ResponseEntity<Book> response = testRestTemplate.getForEntity("/api/books/2", Book.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


        /*
         * metodo crear:
         * preparar las cabeceras
         * preparar el Json
         * preparar la peticion
         * ejecutar la peticion pidiendole que te devuelva datos de tipo libro
         */
        @Test
    void create() {
        HttpHeaders headers  = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON)); // headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); //solo recibe JSON

        String json = """
                {
                        "title": "Aprende Java con ejercicios",
                        "author": "Luis Jose Sanchez Gonzalez",
                        "idioma": "Español",
                        "pages": 917,
                        "price": 19.95,
                        "releaseDate": "2019-09-22",
                        "online": true,
                        "editorial": "",
                        "genero": "Programacion"
                }
                      """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Book> response = testRestTemplate.exchange("/api/books", HttpMethod.POST, request, Book.class);
        Book result = response.getBody();
        assertEquals(1L, Objects.requireNonNull(result).getId()); // assertEquals(1L, result.getId()); //puede devolver un null
        assertEquals("Aprende Java con ejercicios", result.getTitle());
    }
}
