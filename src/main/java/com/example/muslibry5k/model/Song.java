package com.example.muslibry5k.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(of = {"id"})
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String genre;
    private String ismn;
    private String year;

    @ManyToOne
    private Publisher publisher;

    @ManyToMany
    private final Set<Artist> artists = new HashSet<>();

    public Song(String title, String genre, String ismn, String year, Publisher publisher) {
        this.title = title;
        this.genre = genre;
        this.ismn = ismn;
        this.year = year;
        this.publisher = publisher;
    }

    public Song(String title, String genre, String ismn, String year) {
        this.title = title;
        this.genre = genre;
        this.ismn = ismn;
        this.year = year;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPublisher(com.example.muslibry5k.model.Publisher publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setIsmn(String ismn) {
        this.ismn = ismn;
    }

    public void setYear(String year) {
        this.year = year;
    }
}