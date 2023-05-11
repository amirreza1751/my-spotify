package com.neperia.mySpotify.dto.album;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neperia.mySpotify.dto.artist.ArtistDTO;
import com.neperia.mySpotify.dto.track.TrackDTO;
import com.neperia.mySpotify.enums.Genre;
import com.neperia.mySpotify.model.Album;
import com.neperia.mySpotify.model.Artist;
import com.neperia.mySpotify.model.Track;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Set;

@Data
public class AlbumDTO {
    Long id;

    String title;

    String cover;

    Timestamp releaseDate;

    Genre genre;

    Set<TrackDTO> tracks;

    String length;

    int numberOfTracks;

    String artistName;

    Long artistId;

    String artistPic;
}

