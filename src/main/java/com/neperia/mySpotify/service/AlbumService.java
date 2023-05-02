package com.neperia.mySpotify.service;

import com.neperia.mySpotify.exception.NoSuchEntityExistsException;
import com.neperia.mySpotify.model.Album;
import com.neperia.mySpotify.model.Artist;
import com.neperia.mySpotify.repository.AlbumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlbumService extends GenericService<Album> {

    private final AlbumRepository albumRepository;
    private final ArtistService artistService;

    public AlbumService(AlbumRepository albumRepository, ArtistService artistService) {
        this.albumRepository = albumRepository;
        this.artistService = artistService;
    }

    @Override
    Page<Album> getEntities(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable page = getSortingPageable(pageNo, pageSize, sortBy, sortDir);
        return albumRepository.findAll(page);
    }

    @Override
    Album getEntityById(Long id) {
        return albumRepository.findById(id).orElseThrow(
                () -> new NoSuchEntityExistsException(Album.class.getSimpleName(), id)
        );
    }

    @Override
    Album insert(Album entity) {
        return albumRepository.save(entity);
    }

    @Override
    void updateEntity(Long id, Album entity) {
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
    void deleteEntity(Long entityId) {
        if (entityId == null) throw new NoSuchEntityExistsException(Album.class.getSimpleName(), null);
        Album albumFromDb = albumRepository.findById(entityId).orElse(null);
        if (albumFromDb == null) throw new NoSuchEntityExistsException(Album.class.getSimpleName(), entityId);
        albumRepository.deleteById(entityId);
    }

    Album createAlbum(Album album, Long artistId){
        Artist artist = artistService.getEntityById(artistId);
        album.setArtist(artist);
        return insert(album);
    }
}