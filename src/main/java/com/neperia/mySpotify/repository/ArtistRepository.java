package com.neperia.mySpotify.repository;

import com.neperia.mySpotify.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}