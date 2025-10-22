package com.uistify.backend.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class PlaylistSong {

    Long id;
    Long playlistId;
    Song song;
    int trackNumber;
}

