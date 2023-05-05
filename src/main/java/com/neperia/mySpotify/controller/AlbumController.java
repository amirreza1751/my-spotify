package com.neperia.mySpotify.controller;

import com.neperia.mySpotify.dto.album.AlbumDTO;
import com.neperia.mySpotify.mapper.album.AlbumMapper;
import com.neperia.mySpotify.model.Album;
import com.neperia.mySpotify.service.AlbumService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final AlbumMapper mapper;

    public AlbumController(AlbumService albumService, AlbumMapper mapper) {
        this.albumService = albumService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<Page<AlbumDTO>> getAllAlbums(@RequestParam(defaultValue = "0") int pageNo,
                                                        @RequestParam(defaultValue = "25") int pageSize,
                                                        @RequestParam(required = false) String sortBy,
                                                        @RequestParam(required = false) String sortDir) {
        return new ResponseEntity<>(albumService.getEntities(pageNo, pageSize, sortBy, sortDir).map(album -> mapper.toDto(album)),
                HttpStatus.OK);
    }
    @GetMapping({"/{albumId}"})
    public ResponseEntity<AlbumDTO> getAlbum(@PathVariable Long albumId) {
        return new ResponseEntity<>(mapper.toDto((Album) albumService.getEntityById(albumId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<AlbumDTO> saveAlbum(@RequestBody AlbumDTO albumDTO, @RequestParam() Long artistId) {
        Album album = (Album) albumService.createAlbum(mapper.toEntity(albumDTO), artistId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("album", "/api/albums" + album.getId().toString());
        return new ResponseEntity<>(mapper.toDto(album), httpHeaders, HttpStatus.CREATED);
    }
    @PutMapping({"/{albumId}"})
    public ResponseEntity<AlbumDTO> updateAlbum(@PathVariable("albumId") Long albumId, @RequestBody AlbumDTO albumDTO) {
        albumService.updateEntity(albumId, mapper.toEntity(albumDTO));
        return new ResponseEntity<>(mapper.toDto((Album) albumService.getEntityById(albumId)), HttpStatus.OK);
    }
    @DeleteMapping({"/{albumId}"})
    public ResponseEntity<?> deleteAlbum(@PathVariable("albumId") Long albumId) {
        albumService.deleteEntity(albumId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
