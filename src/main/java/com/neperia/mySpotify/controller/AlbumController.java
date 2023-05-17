package com.neperia.mySpotify.controller;

import com.neperia.mySpotify.dto.album.AlbumDTO;
import com.neperia.mySpotify.dto.artist.ArtistDTO;
import com.neperia.mySpotify.dto.genre.GenreDTO;
import com.neperia.mySpotify.enums.Genre;
import com.neperia.mySpotify.mapper.album.AlbumMapper;
import com.neperia.mySpotify.model.Album;
import com.neperia.mySpotify.model.Artist;
import com.neperia.mySpotify.service.AlbumService;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:63343", maxAge = 3600)
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
    @GetMapping("/genres/typeahead")
    public ResponseEntity<List<GenreDTO>> getAllGenres(){
        return  new ResponseEntity<>(albumService.getAllGenres(), HttpStatus.OK);
    }
    @GetMapping({"/{albumId}"})
    public ResponseEntity<AlbumDTO> getAlbum(@PathVariable Long albumId) {
        return new ResponseEntity<>(mapper.toDto((Album) albumService.getEntityById(albumId)), HttpStatus.OK);
    }
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<AlbumDTO> saveAlbum(
            @RequestPart("artistId") String artistId,
            @RequestPart("albumTitle") String albumTitle,
            @RequestPart("releaseDate") String releaseDate,
            @RequestPart("genre") String genre,
            @RequestPart("file") MultipartFile albumCover
    ) {
        Album album = albumService.createAlbum(artistId, genre, albumTitle, releaseDate, albumCover);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("album", "/api/albums" + album.getId().toString());
        return new ResponseEntity<>(mapper.toDto(album), httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{albumId}/cover", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<AlbumDTO> updateAlbumCover(@RequestPart("id") String albumId, @RequestPart("file") MultipartFile cover) {
        Album album = albumService.updateCover(albumId, cover);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("artist", "/api/albums" + album.getId().toString());
        return new ResponseEntity<>(mapper.toDto(album), httpHeaders, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{albumId}", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<AlbumDTO> updateAlbum(
            @PathVariable("albumId") Long albumId,
            @RequestPart("artistId") String artistId,
            @RequestPart("albumTitle") String albumTitle,
            @RequestPart("releaseDate") String releaseDate,
            @RequestPart("genre") String genre
            ) {
        albumService.updateAlbum(albumId, albumTitle, artistId, genre, releaseDate);
        return new ResponseEntity<>(mapper.toDto(albumService.getEntityById(albumId)), HttpStatus.OK);
    }
    @DeleteMapping({"/{albumId}"})
    public ResponseEntity<?> deleteAlbum(@PathVariable("albumId") Long albumId) {
        albumService.deleteEntity(albumId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
