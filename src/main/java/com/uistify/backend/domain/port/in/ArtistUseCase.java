package com.uistify.backend.domain.port.in;

import java.util.Optional;

import com.uistify.backend.domain.model.Artist;
import com.uistify.backend.domain.model.Song;

public interface ArtistUseCase {

	Optional<Artist> getOwnArtist(String userEmail);

	Artist createOwnArtist(String userEmail, Artist artist);

	Artist updateOwnArtist(String userEmail, Artist artist);

	Song uploadSongDetail(String userEmail, Song song);
}
