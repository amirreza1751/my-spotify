package com.neperia.mySpotify.controller;

import com.neperia.mySpotify.dto.track.TrackDTO;
import com.neperia.mySpotify.dto.track.TrackDTO;
import com.neperia.mySpotify.mapper.track.TrackMapper;
import com.neperia.mySpotify.model.Track;
import com.neperia.mySpotify.service.TrackService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    private final TrackService trackService;
    private final TrackMapper mapper;

    public TrackController(TrackService trackService, TrackMapper mapper) {
        this.trackService = trackService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<Page<TrackDTO>> getAllTracks(@RequestParam(defaultValue = "0") int pageNo,
                                                       @RequestParam(defaultValue = "25") int pageSize,
                                                       @RequestParam(required = false) String sortBy,
                                                       @RequestParam(required = false) String sortDir) {
        return new ResponseEntity<>(trackService.getEntities(pageNo, pageSize, sortBy, sortDir).map(track -> mapper.toDto(track)),
                HttpStatus.OK);
    }
    @GetMapping({"/{trackId}"})
    public ResponseEntity<TrackDTO> getTrack(@PathVariable Long trackId) {
        return new ResponseEntity<>(mapper.toDto((Track) trackService.getEntityById(trackId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TrackDTO> saveTrack(@RequestBody TrackDTO trackDTO, @RequestParam() Long albumId) {
        Track track = (Track) trackService.createTrack(mapper.toEntity(trackDTO), albumId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("track", "/api/tracks" + track.getId().toString());
        return new ResponseEntity<>(mapper.toDto(track), httpHeaders, HttpStatus.CREATED);
    }
    @PutMapping({"/{trackId}"})
    public ResponseEntity<TrackDTO> updateTrack(@PathVariable("trackId") Long trackId, @RequestBody TrackDTO trackDTO) {
        trackService.updateEntity(trackId, mapper.toEntity(trackDTO));
        return new ResponseEntity<>(mapper.toDto((Track) trackService.getEntityById(trackId)), HttpStatus.OK);
    }
    @DeleteMapping({"/{trackId}"})
    public ResponseEntity<?> deleteTrack(@PathVariable("trackId") Long trackId) {
        trackService.deleteEntity(trackId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
