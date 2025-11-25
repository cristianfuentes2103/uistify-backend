package com.uistify.backend.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Playlist {

    Long id;
    String title;
    String description;
    Long userId;
	Boolean publicPlaylist;

    List<PlaylistSong> songs;
}
