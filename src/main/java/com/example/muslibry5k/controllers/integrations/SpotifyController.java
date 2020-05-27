package com.example.muslibry5k.controllers.integrations;

import com.example.muslibry5k.commands.integrations.SpotifyAlbumSearchForm;
import com.example.muslibry5k.model.Artist;
import com.example.muslibry5k.model.Publisher;
import com.example.muslibry5k.model.Song;
import com.example.muslibry5k.model.UnknownPublisher;
import com.example.muslibry5k.repositories.ArtistRepository;
import com.example.muslibry5k.repositories.PublisherRepository;
import com.example.muslibry5k.repositories.SongRepository;
import com.example.muslibry5k.services.integrations.SpotifyService;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SpotifyController {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final SpotifyService spotifyService;
    private final PublisherRepository publisherRepository;
    private final String CLIENT_ID = "96bdd4fc53b94c8fb61079f804b5bb52";
    private final String CLIENT_SECRET = "db0ad2cb74194db8a12268ef8f7ac772";
    private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .build();
    private final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();
    private final ClientCredentials clientCredentials = null;

    public SpotifyController(SongRepository songRepository, ArtistRepository artistRepository, SpotifyService spotifyService, PublisherRepository publisherRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.spotifyService = spotifyService;
        this.publisherRepository = publisherRepository;
    }

    @RequestMapping(value = {"/spotify/albums"}, method = RequestMethod.GET)
    public String showAlbumSearch(Model model) {

        SpotifyAlbumSearchForm albumSearchForm = new SpotifyAlbumSearchForm();
        model.addAttribute("albumSearchForm", albumSearchForm);

        return "integrations/spotify/album/search";
    }

    @RequestMapping(value = {"/spotify/albums"}, method = RequestMethod.POST)
    public String showAlbumsForImport(Model model,
                                      @ModelAttribute("albumSearchForm") SpotifyAlbumSearchForm albumSearchForm) {

        String query = albumSearchForm.getQuery();

        if (query != null && query.length() > 0) {
            model.addAttribute("albums", spotifyService.getAlbums(query));
        }

        return "integrations/spotify/album/list";
    }

    @RequestMapping("/spotify/album/{albumId}/show")
    public String getAlbumDetails(Model model, @PathVariable("albumId") String albumId) {
        model.addAttribute("album", spotifyService.getAlbum(albumId));
        model.addAttribute("tracks", spotifyService.getAlbumsTracks(albumId));
        return "integrations/spotify/album/show";
    }

    @RequestMapping(value = {"/spotify/album/{albumId}/save"}, method = RequestMethod.GET)
    public String addAlbumSongsToDB(@PathVariable("albumId") String albumId) {

        Track[] tracks = spotifyService.getAlbumsTracks(albumId);

        for (Track track : tracks) {
            Artist artist = new Artist(track.getArtists()[0].getName(), "[Spotify]", "Spotify: NDA");
            Optional<Song> songOptional = songRepository.getFirstByIsmn(track.getId());

            Optional<Publisher> publisherOptional = publisherRepository.getPublisherByName("Spotify: NDA");

            Song song = songOptional.isPresent() ? songOptional.get() : new Song(track.getName(),
                    "Spotify: NDA", track.getId(), "Spotify: NDA");

            if (publisherOptional.isPresent()) {
                song.setPublisher(publisherOptional.get());
            } else {
                song.setPublisher(UnknownPublisher("Unknown").get());
            }

            Optional<Artist> fromDatabaseArtist = artistRepository.getFirstByFirstName(artist.getFirstName());
            if (fromDatabaseArtist.isPresent()) {
                song.getArtists().add(fromDatabaseArtist.get());
                fromDatabaseArtist.get().getSongs().add(song);
            } else {
                song.getArtists().add(artist);
                artist.getSongs().add(song);
                artistRepository.save(artist);
            }
            songRepository.save(song);
        }

        return "redirect:/songs";
    }


}