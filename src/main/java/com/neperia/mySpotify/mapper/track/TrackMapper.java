package com.neperia.mySpotify.mapper.track;

import com.neperia.mySpotify.dto.album.AlbumDTO;
import com.neperia.mySpotify.dto.track.TrackDTO;
import com.neperia.mySpotify.dto.track.TrackInsertDTO;
import com.neperia.mySpotify.model.Album;
import com.neperia.mySpotify.model.Track;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class TrackMapper {


    public abstract Track toEntity(TrackInsertDTO trackInsertDTO);

    public abstract TrackDTO toDto(Track track);

    public abstract Set<TrackDTO> toDto(Collection<Track> tracks);

    @AfterMapping
    protected void beautifyTrackLength(Track track, @MappingTarget TrackDTO trackDTO){
        trackDTO.setDuration(track.getDuration().toString().replace("PT", "").replace("M", "M "));
    }

    @AfterMapping
    protected void calculateTrackLength(TrackInsertDTO trackInsertDTO, @MappingTarget Track track){
        track.setDuration(Duration.ofMinutes(Long.parseLong(trackInsertDTO.getMinutes())).plusSeconds(Long.parseLong(trackInsertDTO.getSeconds())));
    }
}
