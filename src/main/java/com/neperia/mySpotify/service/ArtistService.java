package com.neperia.mySpotify.service;

import com.neperia.mySpotify.exception.NoSuchEntityExistsException;
import com.neperia.mySpotify.model.Artist;
import com.neperia.mySpotify.repository.ArtistRepository;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ArtistService extends GenericService<Artist> {

    private final ArtistRepository artistRepository;
    private final FileSystemStorageService storageService;


    public ArtistService(ArtistRepository artistRepository, FileSystemStorageService storageService) {
        this.artistRepository = artistRepository;
        this.storageService = storageService;
    }

    @Override
    public Page<Artist> getEntities(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable page = getSortingPageable(pageNo, pageSize, sortBy, sortDir);
        return artistRepository.findAll(page);
    }

    @Override
    public Artist getEntityById(Long id) {
        return artistRepository.findById(id).orElseThrow(
                () -> new NoSuchEntityExistsException(Artist.class.getSimpleName(), id)
        );
    }

    @Override
    public Artist insert(Artist entity) {
        return artistRepository.save(entity);
    }

    public Artist createArtist(String name, MultipartFile profilePicture){
        Artist artist = new Artist();
        artist.setName(name);
        storageService.store(profilePicture);
        Resource savedProfilePicture = storageService.loadAsResource(profilePicture.getOriginalFilename());
        artist.setProfilePicture("http://localhost:8080/api/files/"+ savedProfilePicture.getFilename());
        return artistRepository.save(artist);
    }

    @Override
    public void updateEntity(Long id, Artist entity) {
        Artist artistFromDb = artistRepository.findById(id).orElse(null);
        if (artistFromDb == null) throw new NoSuchEntityExistsException(Artist.class.getSimpleName(), id);
        artistFromDb.setName(entity.getName());
        artistRepository.save(artistFromDb);
    }

    @Override
    public void deleteEntity(Long entityId) {
        if (entityId == null) throw new NoSuchEntityExistsException(Artist.class.getSimpleName(), null);
        Artist artistFromDb = artistRepository.findById(entityId).orElse(null);
        if (artistFromDb == null) throw new NoSuchEntityExistsException(Artist.class.getSimpleName(), entityId);
        artistRepository.deleteById(entityId);
    }

    public List<Artist> getArtistsForTypeAhead(){
        return artistRepository.findAll();
    }

    public Artist updateProfilePicture(String artistId, MultipartFile profilePicture) {
        Long id = Long.parseLong(artistId);
        Artist artist = getEntityById(id);
        storageService.store(profilePicture);
        Resource savedProfilePicture = storageService.loadAsResource(profilePicture.getOriginalFilename());
        artist.setProfilePicture("http://localhost:8080/api/files/"+ savedProfilePicture.getFilename());
        return artistRepository.save(artist);
    }
}