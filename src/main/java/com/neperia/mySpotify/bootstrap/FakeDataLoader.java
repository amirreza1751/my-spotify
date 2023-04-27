package com.neperia.mySpotify.bootstrap;

import com.github.javafaker.Faker;
import com.neperia.mySpotify.enums.Genre;
import com.neperia.mySpotify.model.Album;
import com.neperia.mySpotify.model.Artist;
import com.neperia.mySpotify.model.Track;
import com.neperia.mySpotify.repository.TrackRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class FakeDataLoader implements CommandLineRunner {

    private final TrackRepository trackRepository;

    public FakeDataLoader(TrackRepository trackRepository){
        this.trackRepository = trackRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createFakeData();
    }

    private void createFakeData(){
        Faker faker = new Faker();
        Random random = new Random();
        Track track;
        Artist artist;
        for (int k=0;k<random.ints(3, 7).findFirst().getAsInt(); k++){
            artist = Artist.builder().name(faker.artist().name()).profilePicture("https://profile.com/artist").build();
            Set<Track> tracks = new HashSet<>();
            for (int i=0; i<random.ints(2, 5).findFirst().getAsInt(); i++){
                Album album = Album.builder().
                        title("Album"+i)
                        .genre(Genre.ROCK)
                        .cover("https://cover-path.com")
                        .releaseDate(new Timestamp(System.currentTimeMillis()))
                        .build();
                album.setArtist(artist);
                for (int j=0; j<random.ints(3, 7).findFirst().getAsInt(); j++){
                    track = Track.builder()
                            .name("Cool Track "+i+ "_"+j)
                            .duration(Duration.ofMinutes(random.ints(3, 6).findFirst().getAsInt()).plusSeconds(random.ints(1, 59).findFirst().getAsInt()))
                            .path("https://test.com/")
                            .build();
                    track.setAlbum(album);
                    tracks.add(track);
                }
            }
            trackRepository.saveAll(tracks);
        }

    }
}
