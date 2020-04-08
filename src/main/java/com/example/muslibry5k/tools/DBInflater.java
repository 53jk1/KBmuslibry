package com.example.muslibry5k.tools;

import com.example.muslibry5k.model.Artist;
import com.example.muslibry5k.model.Publisher;
import com.example.muslibry5k.model.Song;
import com.example.muslibry5k.repositories.ArtistRepository;
import com.example.muslibry5k.repositories.PublisherRepository;
import com.example.muslibry5k.repositories.SongRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DBInflater implements ApplicationListener<ContextRefreshedEvent> {

    public DBInflater(SongRepository songRepository, ArtistRepository artistRepository, PublisherRepository publisherRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.publisherRepository = publisherRepository;
    }

    private SongRepository songRepository;
    private ArtistRepository artistRepository;
    private PublisherRepository publisherRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }
    private void initData() {

        Artist taco = new Artist("Filip", "Szcześniak", "Taco Hemingway");
        Publisher tacocorp = new Publisher("Tacocorp");
        Song sanatorium = new Song("SANATORIUM", "Rap", "14803245",
                "2019", tacocorp);
        taco.getSongs().add(sanatorium);
        sanatorium.getArtists().add(taco);
        publisherRepository.save(tacocorp);
        artistRepository.save(taco);
        songRepository.save(sanatorium);

        Artist aluta = new Artist("Aluta", "Brzenszczyszczykiewicz", "Aluta del Rej");
        Publisher alutaRecords = new Publisher("Aluta Records");
        Song english = new Song("English for stubborn people", "Lanaism", "18002687886",
                "1975", alutaRecords);
        aluta.getSongs().add(english);
        english.getArtists().add(aluta);
        publisherRepository.save(alutaRecords);
        artistRepository.save(aluta);
        songRepository.save(english);

        Artist lukasz = new Artist("Lukas", "Halson", "Lukasz");
        Publisher magicPen = new Publisher("Magic Pen");
        Song shatan = new Song("SHATAN!!!", "Hard Polo", "1694386",
                "2015", magicPen);
        lukasz.getSongs().add(shatan);
        shatan.getArtists().add(lukasz);
        publisherRepository.save(magicPen);
        artistRepository.save(lukasz);
        songRepository.save(shatan);

    }
}