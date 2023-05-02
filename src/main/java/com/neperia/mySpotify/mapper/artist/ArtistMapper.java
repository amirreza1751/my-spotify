package com.neperia.mySpotify.mapper.artist;

import com.neperia.mySpotify.dto.artist.ArtistDTO;
import com.neperia.mySpotify.mapper.album.AlbumMapper;
import com.neperia.mySpotify.model.Artist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AlbumMapper.class)
public abstract class ArtistMapper {

    public abstract Artist toEntity(ArtistDTO artistDTO);

    public abstract ArtistDTO toDto(Artist artist);
}
