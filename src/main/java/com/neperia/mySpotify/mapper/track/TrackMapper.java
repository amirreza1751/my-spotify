package com.neperia.mySpotify.mapper.track;

import com.neperia.mySpotify.dto.album.AlbumDTO;
import com.neperia.mySpotify.dto.track.TrackDTO;
import com.neperia.mySpotify.model.Album;
import com.neperia.mySpotify.model.Track;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class TrackMapper {

    public abstract Track toEntity(TrackDTO trackDTO);

    public abstract TrackDTO toDto(Track track);

    public abstract Set<TrackDTO> toDto(Collection<Track> tracks);
}
