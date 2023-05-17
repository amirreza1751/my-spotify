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
        for (int k=0;k<random.ints(2, 4).findFirst().getAsInt(); k++){
            artist = new Artist();
            artist.setName(faker.artist().name());
            artist.setProfilePicture("https://i.scdn.co/image/ab6761610000e5ebc36dd9eb55fb0db4911f25dd");
            Set<Track> tracks = new HashSet<>();
            for (int i=0; i<random.ints(2, 4).findFirst().getAsInt(); i++){
                Album album = new Album();
                album.setTitle("Album"+i);
                album.setGenre(Genre.ROCK);
                album.setCover("https://static-cse.canva.com/blob/1052545/1600w-1Nr6gsUndKw.jpg");
                album.setReleaseDate(new Timestamp(System.currentTimeMillis()));
                album.setArtist(artist);
                for (int j=0; j<random.ints(3, 7).findFirst().getAsInt(); j++){
                    track = new Track();
                    track.setName("Cool Track "+i+ "_"+j);
                    track.setDuration(Duration.ofMinutes(random.ints(3, 6).findFirst().getAsInt()).plusSeconds(random.ints(1, 59).findFirst().getAsInt()));
                    track.setPath("https://test.com/");
                    track.setAlbum(album);
                    tracks.add(track);
                }
            }
            trackRepository.saveAll(tracks);
        }

    }
}
