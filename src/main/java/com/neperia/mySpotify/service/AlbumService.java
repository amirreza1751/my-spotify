package com.neperia.mySpotify.service;

import com.neperia.mySpotify.dto.genre.GenreDTO;
import com.neperia.mySpotify.enums.Genre;
import com.neperia.mySpotify.exception.NoSuchEntityExistsException;
import com.neperia.mySpotify.model.Album;
import com.neperia.mySpotify.model.Artist;
import com.neperia.mySpotify.repository.AlbumRepository;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService extends GenericService<Album> {

    private final AlbumRepository albumRepository;
    private final ArtistService artistService;
    private final FileSystemStorageService storageService;

    public AlbumService(AlbumRepository albumRepository, ArtistService artistService, FileSystemStorageService storageService) {
        this.albumRepository = albumRepository;
        this.artistService = artistService;
        this.storageService = storageService;
    }

    @Override
    public Page<Album> getEntities(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable page = getSortingPageable(pageNo, pageSize, sortBy, sortDir);
        return albumRepository.findAll(page);
    }

    @Override
    public Album getEntityById(Long id) {
        return albumRepository.findById(id).orElseThrow(
                () -> new NoSuchEntityExistsException(Album.class.getSimpleName(), id)
        );
    }

    @Override
    public Album insert(Album entity) {
        return albumRepository.save(entity);
    }

    @Override
    public void updateEntity(Long id, Album entity) {
        Album albumFromDb = albumRepository.findById(id).orElse(null);
        if (albumFromDb == null) throw new NoSuchEntityExistsException(Album.class.getSimpleName(), id);
        albumFromDb.setTitle(entity.getTitle());
        albumFromDb.setGenre(entity.getGenre());
        albumFromDb.setCover(entity.getCover());
        albumFromDb.setReleaseDate(entity.getReleaseDate());
        //todo update artist and tracks
        albumRepository.save(albumFromDb);
    }

    @Override
    public void deleteEntity(Long entityId) {
        if (entityId == null) throw new NoSuchEntityExistsException(Album.class.getSimpleName(), null);
        Album albumFromDb = albumRepository.findById(entityId).orElse(null);
        if (albumFromDb == null) throw new NoSuchEntityExistsException(Album.class.getSimpleName(), entityId);
        for (var track : albumFromDb.getTracks()){
            track.setAlbum(null);
        }
        albumFromDb.setArtist(null);
        albumRepository.deleteById(entityId);
    }

    public Album createAlbum(String artistId, String genre, String albumTitle, String releaseDate, MultipartFile albumCover){
        Artist artist = artistService.getEntityById(Long.valueOf(artistId));
        DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Album album = new Album();
        try {
            Date date = formatter.parse(releaseDate);
            album.setReleaseDate(new Timestamp(date.getTime()));
        } catch (ParseException e) {
            throw new RuntimeException();
        }
        album.setTitle(albumTitle);
        album.setGenre(Genre.valueOf(genre));
        album.setArtist(artist);
        storageService.store(albumCover);
        Resource savedAlbumCover = storageService.loadAsResource(albumCover.getOriginalFilename());
        album.setCover("http://localhost:8080/api/files/"+ savedAlbumCover.getFilename());

        return insert(album);
    }

    public void updateAlbum(Long albumId, String albumTitle, String artistId, String genre, String releaseDate) {
        Artist artist = artistService.getEntityById(Long.valueOf(artistId));
        DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

        Album albumFromDb = albumRepository.findById(Long.valueOf(albumId)).orElse(null);
        if (albumFromDb == null) throw new NoSuchEntityExistsException(Album.class.getSimpleName(), albumId);

        try {
            Date date = formatter.parse(releaseDate);
            albumFromDb.setReleaseDate(new Timestamp(date.getTime()));
        } catch (ParseException e) {
            throw new RuntimeException();
        }
        albumFromDb.setTitle(albumTitle);
        albumFromDb.setGenre(Genre.valueOf(genre));
        albumFromDb.setArtist(artist);
        albumRepository.save(albumFromDb);
    }

    public List<GenreDTO> getAllGenres(){
        List<GenreDTO> genreDTOList = new ArrayList<>();
        for (var genre : Genre.values()){
            genreDTOList.add(new GenreDTO(genre.name()));
        }
        return genreDTOList;
    }
}