package com.example.muslibry5k.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Artist {
    private String firstName;
    private String lastName;
    private String nick;

    @Id
    private Long id;

    @ManyToMany(mappedBy = "artists")
    private Set<Song> songs = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artist(String firstName, String lastName, String nick, Set<Song> songs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.songs = songs;
    }

    public Artist() {
    }

    @Override
    public String toString() {
        return "Artist{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nick='" + nick + '\'' +
                ", id=" + id +
                ", songs=" + songs +
                '}';
    }
}