package com.neperia.mySpotify.dto.track;

import lombok.Data;

import java.time.Duration;

@Data
public class TrackDTO {
    Long id;

    String name;

    String duration;

    String path;
}
