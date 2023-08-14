package com.example.demobook.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "books")
@ApiModel("Entidad Libro para representar elemento didactico compuesto por laminas en soporte de celulosa")
public class Book {

    // atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Clave ficticia autoincremental tipo Long")
    private Long id;
    private String title;
    private String author;

    @ApiModelProperty("Idiomas disponibles")
    @Column(name = "lang")
    private String language;
    @ApiModelProperty("Cantidad de Paginas")
    private Integer pages;
    @ApiModelProperty("Precio en Euros")
    private Double price;

    @ApiModelProperty("Fecha lanzamiento")
    @Column(name = "releaseDate")
    private LocalDate releaseDate;

    @ApiModelProperty("Se encuentra en formato e-book")
    private Boolean online;
    private String editorial;

    @ApiModelProperty("Genero")
    private String gender;


    // constructores
    public Book() {
    }

    public Book(Long id, String title, String author, String language, Integer pages, Double price, LocalDate releaseDate,
                Boolean online, String editorial, String gender) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.language = language;
        this.pages = pages;
        this.price = price;
        this.releaseDate = releaseDate;
        this.online = online;
        this.editorial = editorial;
        this.gender = gender;
    }


    // getter y setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", language='" + language + '\'' +
                ", pages=" + pages +
                ", price=" + price +
                ", releaseDate=" + releaseDate +
                ", online=" + online +
                ", editorial='" + editorial + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) &&
                Objects.equals(language, book.language) && Objects.equals(pages, book.pages) &&
                Objects.equals(price, book.price) && Objects.equals(releaseDate, book.releaseDate) &&
                Objects.equals(online, book.online) && Objects.equals(editorial, book.editorial) &&
                Objects.equals(gender, book.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, language, pages, price, releaseDate, online, editorial, gender);
    }
}
