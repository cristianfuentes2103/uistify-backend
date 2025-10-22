package com.uistify.backend.presentation.rest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SongDto {
    private Long id;
    private String title;
    private int duration;
    private LocalDate releaseDate;
    private String artist;
    private String album;
    private String genre;
    private String pictureUrl;
    private String sourceUrl;
}

