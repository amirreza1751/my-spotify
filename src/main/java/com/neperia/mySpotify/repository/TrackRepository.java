package com.neperia.mySpotify.repository;

import com.neperia.mySpotify.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
}