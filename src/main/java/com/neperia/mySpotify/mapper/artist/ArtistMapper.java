package com.neperia.mySpotify.mapper.artist;

import com.neperia.mySpotify.dto.artist.ArtistDTO;
import com.neperia.mySpotify.dto.artist.ArtistInsertDTO;
import com.neperia.mySpotify.mapper.album.AlbumMapper;
import com.neperia.mySpotify.model.Artist;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.Set;

@Mapper(componentModel = "spring", uses = AlbumMapper.class)
public abstract class ArtistMapper {

    public abstract Artist toEntity(ArtistInsertDTO artistInsertDTO);

    public abstract ArtistDTO toDto(Artist artist);

    public abstract Set<ArtistDTO> toDto(Collection<Artist> artists);
}
