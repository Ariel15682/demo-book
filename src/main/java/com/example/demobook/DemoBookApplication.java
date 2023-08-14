package com.example.demobook;

import com.example.demobook.entities.Book;
import com.example.demobook.repositories.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import com.example.demobook.config.SwaggerConfig;
import java.time.LocalDate;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class DemoBookApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoBookApplication.class, args);
        BookRepository repository = context.getBean(BookRepository.class);

        //CRUD
        // crear un libro
        Book book1 = new Book(null, "Java Curso Práctico ", "José María Vegas Gertrudix",
                "Castellano", 524, 34.90, LocalDate.of(2020, 12,21), true,
                "ra-ma", "POO");
        //Book book2 = new Book(null, "Java 17 Programación Avanzada ", "José María Vegas Gertrudix",
        // "Castellano", 526, 32.90, LocalDate.of(2021, 11,22), true,
        // "RA-MA", "IT");

        // almacenar un libro
        System.out.println("Numero de libros en Base de Datos: " + repository.findAll().size());
        repository.save(book1);
        //repository.save(book2);

        // recuperar todos los libros
        System.out.println("Numero de libros en Base de Datos: " + repository.findAll().size());

        // borrar un libro
        //repository.deleteById(1L);

        System.out.println("Numero de libros en Base de Datos: " + repository.findAll().size());

    }
}
