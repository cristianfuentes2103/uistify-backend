package com.uistify.backend.domain.port.in;

import java.util.List;

import com.uistify.backend.infraestructure.persistence.jpa.Entity.PlaylistEntity;

public interface PlaylistUseCase {
	List<PlaylistEntity> getAllPlaylistByUserEmail(String userEmail);
	PlaylistEntity createPlaylist(PlaylistEntity playlist);
}
