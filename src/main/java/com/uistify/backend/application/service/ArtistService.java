package com.uistify.backend.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uistify.backend.domain.model.Artist;
import com.uistify.backend.domain.model.Song;
import com.uistify.backend.domain.model.User;
import com.uistify.backend.domain.port.in.ArtistUseCase;
import com.uistify.backend.domain.port.out.ArtistRepository;
import com.uistify.backend.domain.port.out.SongRepository;
import com.uistify.backend.domain.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistService implements ArtistUseCase{

	
    private final UserRepository userRepository;
	private final ArtistRepository artistRepository;
	private final SongRepository songRepository;

	@Override
	public Optional<Artist> getOwnArtist(String userEmail){
		User user = getUserOrThrow(userEmail);
		return artistRepository.findByUserId(user.getId());
	}

	@Override
	public Artist createOwnArtist(String userEmail, Artist artist){
		User user = getUserOrThrow(userEmail);
		artistRepository.findByUserId(user.getId())
			.ifPresent(a -> { throw new IllegalArgumentException("Already exist an artist"); });
		Artist myArtist = Artist.builder()
			.name(artist.getName())
			.country(artist.getCountry())
			.portraitUrl(artist.getPortraitUrl())
			.userId(user.getId())
			.build();

		return artistRepository.save(myArtist);
	}

	@Override
	public Artist updateOwnArtist(String userEmail, Artist artist){
		User user = getUserOrThrow(userEmail);
		
		Artist oldArtist = artistRepository.findByUserId(user.getId())
			.orElseThrow(() -> new IllegalArgumentException("Doesn't exist the artist"));
		Artist newArtist = Artist.builder()
			.id(oldArtist.getId())
			.name(artist.getName())
			.country(artist.getCountry())
			.portraitUrl(artist.getPortraitUrl())
			.userId(user.getId())
			.build();
		return artistRepository.save(newArtist);
	}

	@Override
	public Song uploadSongDetail(String userEmail, Song song){
		User user = getUserOrThrow(userEmail);
		Artist artist = artistRepository.findByUserId(user.getId())
			.orElseThrow(() -> new IllegalArgumentException("Doesn't exist the artist"));

		Song newSong = Song.builder()
			.title(song.getTitle())
			.duration(song.getDuration())
			.releaseDate(song.getReleaseDate())
			.artistId(artist.getId())
			.album(song.getAlbum())
			.genre(song.getGenre())
			.pictureUrl(song.getPictureUrl())
			.sourceUrl(song.getSourceUrl())
			.build();

		return songRepository.save(newSong);
	}

    private User getUserOrThrow(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
