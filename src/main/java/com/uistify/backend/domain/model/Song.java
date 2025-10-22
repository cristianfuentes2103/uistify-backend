package com.uistify.backend.domain.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class Song {

    Long id;
    String title;
    int duration;
    LocalDate releaseDate;
    String artist;
    String album;
    String genre;
    String pictureUrl;
    String sourceUrl;
}

