package com.example.muslibry5k.repositories;

import com.example.muslibry5k.model.Artist;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    Optional<Artist> getFirstByFirstNameAndLastName(String firstName, String lastName);

    Optional<Artist> getFirstByFirstName(String firstName);
}