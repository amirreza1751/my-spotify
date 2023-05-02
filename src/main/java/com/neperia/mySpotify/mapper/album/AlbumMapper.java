package com.neperia.mySpotify.mapper.album;

import com.neperia.mySpotify.dto.album.AlbumDTO;
import com.neperia.mySpotify.mapper.track.TrackMapper;
import com.neperia.mySpotify.model.Album;
import com.neperia.mySpotify.model.Track;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;

@Mapper(componentModel = "spring", uses = TrackMapper.class)
public abstract class AlbumMapper {

    public abstract Album toEntity(AlbumDTO albumDTO);

    public abstract AlbumDTO toDto(Album album);

    public abstract Set<AlbumDTO> toDto(Collection<Album> albums);

    @AfterMapping
    protected void calculateLength(Album album, @MappingTarget AlbumDTO albumDTO){
        if (album.getTracks() == null){
            return;
        }
        Duration temp = Duration.ZERO;
        for (Track track : album.getTracks())
        {
            temp = temp.plus(track.getDuration());
        }
        albumDTO.setLength(temp);
    }
}
