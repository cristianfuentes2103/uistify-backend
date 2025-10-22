package com.uistify.backend.presentation.rest.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaylistDetailDto {
    private Long id;
    private String title;
    private String description;
    private List<SongDto> songs = new ArrayList<>();
}
