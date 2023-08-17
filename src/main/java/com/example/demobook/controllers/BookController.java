package com.example.demobook.controllers;

import com.example.demobook.entities.Book;
import com.example.demobook.repositories.BookRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final Logger log = LoggerFactory.getLogger(BookController.class);

    // Atributos
    private final BookRepository bookRepository;

    // Constructores
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    // CRUD sobre la entidad Book

    // METODOS:

    // Buscar todos los libros (una lista de libros)
    @GetMapping("/api/books")
    public List<Book> findAll(){
        // recuperar y devolver los libros de base de satos
        return bookRepository.findAll();
    }

    /* Buscar un libro en clase de datos segun su id

     // el findById no devuelve un libro, devuelve un objeto "optional" un envoltorio.

     Optional<Book> bookOpt = bookRepository.findById(id);

     // Opcion 1
     if (bookOpt.isPresent())
     return bookOpt.get(); //si el libro esta, devuelve ese libro
     else return null; // no es buena practica devolver un null
     }

     // Opcion 2
     //return bookOpt.orElse(null); // lo mismo en una sola linea. "Devuelveme el libro y sino un null"
     }
     */

    // Opcion 3
    //devolviendo una @ResponseEntity (notFound()-404) y no "vacio" status ok (200)
    @GetMapping("/api/books/{id}")
    // Descrpcion para el metodo en swagger
    @ApiOperation("Buscar un libro por clave primaria id Long")
    // @ApiParam descripcion del parametro id
    public ResponseEntity<Book> findOneById(@ApiParam("Clave primaria tipo Long") @PathVariable Long id){

        Optional<Book> bookOpt = bookRepository.findById(id);

//        if (bookOpt.isPresent())
//            return ResponseEntity.ok(bookOpt.get());
//        else
//            return ResponseEntity.notFound().build();

        return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Opcion 4 (con programacion funcional)
    // return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());


    // Crear/salvar un nuevo libro en base de datos
    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));

        //Guarda el libro pasado por parametro en BB.DD.
        if(book.getId() != null){
            // Si el libro existe quiere decir que no es una creacion sino una actualizacion
            log.warn("Trying to create an existent book with id"); // se puede capturar con Try/Catch - log.error
            //System.out.println("trying to create book with id");
            return ResponseEntity.badRequest().build();
        }
        Book result = bookRepository.save(book); //El libro devuelto tiene una clave primaria
        return ResponseEntity.ok(result);
    }

    /* Actualizar un libro existente en base de datos
     * Lo normal aqui es que se recupere el objeto y mediante setter que se hagan las modificaciones de los parametros
     * requeridos/permitidos y luego se salvan los cambios, pero no el objeto libro. Lo habitual es que esto no lo haga
     * la clase controller o la que llama al repositorio sino una clase intermedia
     */
    @PutMapping("/api/books")
    // El id debe ser distinto a nulo para que sea una modificacion/update
    public ResponseEntity<Book> update(@RequestBody Book book){
        if(book.getId() == null){
            // Si no existe id quiere decir que es una creacion
            log.warn("Trying to update a non existent book");
            return ResponseEntity.badRequest().build();
        }
        if(!bookRepository.existsById(book.getId())){
            log.warn("Trying to update a non existent book");
            return ResponseEntity.notFound().build();
        }
        // El proceso de actualizacion
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result);
    }

    // Borrar un libro en base de datos
    @ApiIgnore
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){

        if(!bookRepository.existsById(id)){
            log.warn("Trying to delete a non existent book");
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Borrar todos los libros en base de datos
    @ApiIgnore // Con esta anotacion escondemos el metodo de la api swagger
    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAll(){
        log.info("REST Request for delete all books"); // log.debug
        // Se podria hacer un count y si da mas de cero borra y sino no
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
