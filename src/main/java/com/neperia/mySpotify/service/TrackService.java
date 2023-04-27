package com.neperia.mySpotify.service;

import com.neperia.mySpotify.exception.NoSuchEntityExistsException;
import com.neperia.mySpotify.model.Album;
import com.neperia.mySpotify.model.Track;
import com.neperia.mySpotify.repository.TrackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TrackService extends GenericService<Track> {
    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    Page<Track> getEntities(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable page = getSortingPageable(pageNo, pageSize, sortBy, sortDir);
        return trackRepository.findAll(page);
    }

    @Override
    Track getEntityById(Long id) {
        return trackRepository.findById(id).orElseThrow(
                () -> new NoSuchEntityExistsException(Track.class.getSimpleName(), id)
        );
    }

    @Override
    Track insert(Track entity) {
        return trackRepository.save(entity);
    }

    @Override
    void updateEntity(Long id, Track entity) {
        Track trackFromDb = trackRepository.findById(id).orElse(null);
        if (trackFromDb == null) throw new NoSuchEntityExistsException(Track.class.getSimpleName(), id);
        trackFromDb.setName(entity.getName());
        trackFromDb.setDuration(entity.getDuration());
        trackFromDb.setPath(entity.getPath());
        //todo update album
        trackRepository.save(trackFromDb);
    }

    @Override
    void deleteEntity(Long entityId) {

    }
}