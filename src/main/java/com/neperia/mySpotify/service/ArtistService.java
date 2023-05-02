package com.neperia.mySpotify.service;

import com.neperia.mySpotify.exception.NoSuchEntityExistsException;
import com.neperia.mySpotify.model.Artist;
import com.neperia.mySpotify.repository.ArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArtistService extends GenericService<Artist> {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
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

    @Override
    public void updateEntity(Long id, Artist entity) {
        Artist artistFromDb = artistRepository.findById(id).orElse(null);
        if (artistFromDb == null) throw new NoSuchEntityExistsException(Artist.class.getSimpleName(), id);
        artistFromDb.setName(entity.getName());
        artistFromDb.setProfilePicture(entity.getProfilePicture());
        //todo update albums
        artistRepository.save(artistFromDb);
    }

    @Override
    public void deleteEntity(Long entityId) {
        if (entityId == null) throw new NoSuchEntityExistsException(Artist.class.getSimpleName(), null);
        Artist artistFromDb = artistRepository.findById(entityId).orElse(null);
        if (artistFromDb == null) throw new NoSuchEntityExistsException(Artist.class.getSimpleName(), entityId);
        artistRepository.deleteById(entityId);
    }
}