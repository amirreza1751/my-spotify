package com.neperia.mySpotify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neperia.mySpotify.dto.artist.ArtistDTO;
import com.neperia.mySpotify.dto.artist.ArtistInsertDTO;
import com.neperia.mySpotify.mapper.artist.ArtistMapper;
import com.neperia.mySpotify.model.Artist;
import com.neperia.mySpotify.service.ArtistService;
import com.neperia.mySpotify.service.FileSystemStorageService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    private final ArtistService artistService;
    private final ArtistMapper mapper;

    public ArtistController(ArtistService artistService, ArtistMapper mapper) {
        this.artistService = artistService;
        this.mapper = mapper;
    }


    @GetMapping
    public ResponseEntity<Page<ArtistDTO>> getAllArtists(@RequestParam(defaultValue = "0") int pageNo,
                                                      @RequestParam(defaultValue = "25") int pageSize,
                                                      @RequestParam(required = false) String sortBy,
                                                      @RequestParam(required = false) String sortDir) {
        return new ResponseEntity<>(artistService.getEntities(pageNo, pageSize, sortBy, sortDir).map(artist -> mapper.toDto(artist)),
                HttpStatus.OK);
    }

    @GetMapping("/typeahead")
    public ResponseEntity<Set<ArtistDTO>> getArtistsForTypeAhead() {
        return new ResponseEntity<Set<ArtistDTO>>(mapper.toDto(artistService.getArtistsForTypeAhead()), HttpStatus.OK);
    }
    @GetMapping({"/{artistId}"})
    public ResponseEntity<ArtistDTO> getArtist(@PathVariable Long artistId) {
        return new ResponseEntity<>(mapper.toDto((Artist) artistService.getEntityById(artistId)), HttpStatus.OK);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ArtistDTO> createArtist(@RequestPart("name") String artistName, @RequestPart("file") MultipartFile profilePicture) {
        Artist artist = artistService.createArtist(artistName, profilePicture);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("artist", "/api/artists" + artist.getId().toString());
        return new ResponseEntity<>(mapper.toDto(artist), httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"/{artistId}"})
    public ResponseEntity<ArtistDTO> updateArtist(@PathVariable("artistId") Long artistId, @RequestBody ArtistInsertDTO artistInsertDTO) {
        artistService.updateEntity(artistId, mapper.toEntity(artistInsertDTO));
        return new ResponseEntity<>(mapper.toDto((Artist) artistService.getEntityById(artistId)), HttpStatus.OK);
    }

    @PutMapping(value = "/{artistId}/profile-picture", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ArtistDTO> updateArtistProfilePicture(@RequestPart("id") String artistId, @RequestPart("file") MultipartFile profilePicture) {
        Artist artist = artistService.updateProfilePicture(artistId, profilePicture);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("artist", "/api/artists" + artist.getId().toString());
        return new ResponseEntity<>(mapper.toDto(artist), httpHeaders, HttpStatus.CREATED);
    }
    @DeleteMapping({"/{artistId}"})
    public ResponseEntity<?> deleteArtist(@PathVariable("artistId") Long artistId) {
        artistService.deleteEntity(artistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
