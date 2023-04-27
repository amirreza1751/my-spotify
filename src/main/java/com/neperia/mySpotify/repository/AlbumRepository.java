package com.neperia.mySpotify.repository;

import com.neperia.mySpotify.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}