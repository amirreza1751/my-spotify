package com.neperia.mySpotify.dto.track;

import lombok.Data;

@Data
public class TrackInsertDTO {
    Long id;

    String name;

    String minutes;

    String seconds;
}
