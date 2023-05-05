package com.neperia.mySpotify.controller;

import com.neperia.mySpotify.dto.artist.ArtistDTO;
import com.neperia.mySpotify.mapper.artist.ArtistMapper;
import com.neperia.mySpotify.model.Artist;
import com.neperia.mySpotify.service.ArtistService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
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
    @GetMapping({"/{artistId}"})
    public ResponseEntity<ArtistDTO> getArtist(@PathVariable Long artistId) {
        return new ResponseEntity<>(mapper.toDto((Artist) artistService.getEntityById(artistId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ArtistDTO> saveArtist(@RequestBody ArtistDTO artistDTO) {
        Artist artist = (Artist) artistService.insert(mapper.toEntity(artistDTO));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("artist", "/api/artists" + artist.getId().toString());
        return new ResponseEntity<>(mapper.toDto(artist), httpHeaders, HttpStatus.CREATED);
    }
    @PutMapping({"/{artistId}"})
    public ResponseEntity<ArtistDTO> updateArtist(@PathVariable("artistId") Long artistId, @RequestBody ArtistDTO artistDTO) {
        artistService.updateEntity(artistId, mapper.toEntity(artistDTO));
        return new ResponseEntity<>(mapper.toDto((Artist) artistService.getEntityById(artistId)), HttpStatus.OK);
    }
    @DeleteMapping({"/{artistId}"})
    public ResponseEntity<?> deleteArtist(@PathVariable("artistId") Long artistId) {
        artistService.deleteEntity(artistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
