package com.olabi.olabiflix.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

// @Entity Para criar tabela
@Entity
// @Table para definir nome da tabela *não obrigatório
@Table(
        name = "filme",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "releaseYear", "director", "writer"})
)
public class Filme {

    @Id
    @UuidGenerator
    private UUID id;
    @NotBlank(message = "Title não pode ser vazio")
    @Size(max = 225)
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "releaseYear não pode ser vazio")
    @Pattern(regexp = "^\\d{4}$", message = "releaseYear precisa ser um numero de 4 digitos" )
    @Column(nullable = false)
    private String releaseYear;

    private String rated;

    private String released;

    private String runtime;

    private String genre;

    @NotBlank(message = "director não pode ser vazio")
    @Size(max = 225)
    @Column(nullable = false)
    private String director;

    @NotBlank(message = "writer não pode ser vazio")
    @Size(max = 225)
    @Column(nullable = false)
    private String writer;

    private String actors;

    private String plot;

    private String language;

    private String country;

    private String awards;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    protected Filme() {}

    // Construtor sem o campo 'id' pois será gerado automaticamente

    public Filme(String title, String releaseYear, String rated, String released, String runtime, String genre, String director, String writer, String actors, String plot, String language, String country, String awards) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", rated='" + rated + '\'' +
                ", released='" + released + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", actors='" + actors + '\'' +
                ", plot='" + plot + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", awards='" + awards + '\'' +
                '}';
    }
}
