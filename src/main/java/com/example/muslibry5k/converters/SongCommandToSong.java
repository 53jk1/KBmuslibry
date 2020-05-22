package com.example.muslibry5k.converters;

import com.example.muslibry5k.commands.SongCommand;
import com.example.muslibry5k.model.Song;
import com.example.muslibry5k.repositories.ArtistRepository;
import com.example.muslibry5k.repositories.PublisherRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SongCommandToSong implements Converter<SongCommand, Song> {

    private final PublisherRepository publisherRepository;
    private final ArtistRepository artistRepository;

    public SongCommandToSong(PublisherRepository publisherRepository, ArtistRepository artistRepository) {
        this.publisherRepository = publisherRepository;
        this.artistRepository = artistRepository;
    }

    @lombok.Synchronized
    @org.springframework.lang.Nullable
    @Override
    public com.example.muslibry5k.model.Song convert(@javax.annotation.Nonnull com.example.muslibry5k.commands.SongCommand source) {
        if (source == null) {
            return null;
        }

        final com.example.muslibry5k.model.Song song = new com.example.muslibry5k.model.Song();
        song.setId(source.getId());
        song.setTitle(source.getTitle());
        song.setGenre(source.getGenre());
        song.setYear(source.getYear());
        song.setIsmn(source.getIsmn());

        java.util.Optional<com.example.muslibry5k.model.Publisher> publisher = publisherRepository.findById(source.getPublisherId());

        if (publisher.isPresent()) {
            song.setPublisher(publisher.get());
        } else {
            song.setPublisher(publisherRepository.getPublisherByName("Unknown").get());
        }

        java.util.Optional<com.example.muslibry5k.model.Artist> artist = artistRepository.findById(source.getArtistId());

        if (artist.isPresent()) {
            song.getArtists().add(artist.get());
        }

        return song;
    }
}