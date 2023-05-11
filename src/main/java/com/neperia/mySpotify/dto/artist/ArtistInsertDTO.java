package com.neperia.mySpotify.dto.artist;

import com.neperia.mySpotify.dto.album.AlbumDTO;
import lombok.Data;

import java.util.Set;

@Data
public class ArtistInsertDTO {
    Long id;

    String name;

    String profilePicture;

    Set<AlbumDTO> albums;
}
