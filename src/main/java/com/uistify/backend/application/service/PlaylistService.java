package com.uistify.backend.application.service;

import com.uistify.backend.domain.model.Playlist;
import com.uistify.backend.domain.model.PlaylistSong;
import com.uistify.backend.domain.model.Song;
import com.uistify.backend.domain.model.User;
import com.uistify.backend.domain.port.in.PlaylistUseCase;
import com.uistify.backend.domain.port.out.PlaylistRepository;
import com.uistify.backend.domain.port.out.PlaylistSongRepository;
import com.uistify.backend.domain.port.out.SongRepository;
import com.uistify.backend.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService implements PlaylistUseCase {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Override
    public List<Playlist> getAllPlaylistsByUserEmail(String userEmail) {
        return playlistRepository.findByUserEmail(userEmail);
    }

	@Override
	public List<Playlist> getAllPublicPlaylists(){
		return playlistRepository.findAllByPublicPlaylistTrue();
	}

	@Override
	public Playlist getPublicPlaylist(Long playlistId){
        Playlist publicPlaylist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));
		if (!publicPlaylist.getPublicPlaylist()){
			throw new SecurityException("Playlist is not public");
		}
		return publicPlaylist;
	}

	@Override
    public List<Song> getSongsFromPublicPlaylist(Long playlistId){
		Playlist publicPlaylist = getPublicPlaylist(playlistId);
		return publicPlaylist.getSongs().stream()
			.map(playlistSong -> playlistSong.getSong())
			.collect(Collectors.toList());
	}

    @Override
    public Playlist createPlaylist(String userEmail, Playlist playlist) {
        User owner = getUserOrThrow(userEmail);
        Playlist playlistToSave = playlist.toBuilder()
                .userId(owner.getId())
                .build();
        return playlistRepository.save(playlistToSave);
    }

    @Override
    public Playlist updatePlaylist(String userEmail, Playlist playlist) {
        Playlist existing = getOwnedPlaylist(userEmail, playlist.getId());
        Playlist toPersist = existing.toBuilder()
                .title(playlist.getTitle())
                .description(playlist.getDescription())
				.publicPlaylist(playlist.isPublicPlaylist())
                .build();
        return playlistRepository.save(toPersist);
    }

    @Override
    public Playlist getPlaylistDetail(String userEmail, Long playlistId) {
        return getOwnedPlaylist(userEmail, playlistId);
    }

	@Override
	public List<Song> getSongsFromPlaylist(String userEmail, Long playlistId){
		Playlist playlist = getOwnedPlaylist(userEmail, playlistId);
		return playlist.getSongs().stream()
			.map(playlistSong -> playlistSong.getSong())
			.collect(Collectors.toList());
	}

    @Override
    public void deletePlaylist(String userEmail, Long playlistId) {
        getOwnedPlaylist(userEmail, playlistId);
        playlistSongRepository.deleteAllByPlayListId(playlistId);
        playlistRepository.deleteById(playlistId);
    }

    @Override
    public void addSongToPlaylist(String userEmail, Long playlistId, Long songId) {
        Playlist playlist = getOwnedPlaylist(userEmail, playlistId);
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));
        if (playlistSongRepository.existsByPlaylistIdAndSongId(playlistId, songId)) {
            throw new IllegalStateException("Song already in playlist");
        }
        int nextTrackNumber = Optional.ofNullable(playlist.getSongs())
                .map(List::size)
                .orElse(0) + 1;
        PlaylistSong newAssociation = PlaylistSong.builder()
                .playlistId(playlistId)
                .song(song)
                .trackNumber(nextTrackNumber)
                .build();
        playlistSongRepository.save(newAssociation);
    }

    @Override
    public void deleteSongFromPlaylist(String userEmail, Long playlistId, Long songId) {
        getOwnedPlaylist(userEmail, playlistId);
        PlaylistSong playlistSong = playlistSongRepository.findByPlaylistIdAndSongId(playlistId, songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found in playlist"));
        playlistSongRepository.deleteById(playlistSong.getId());
    }

    private Playlist getOwnedPlaylist(String userEmail, Long playlistId) {
        User owner = getUserOrThrow(userEmail);
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));
        if (playlist.getUserId() == null || !playlist.getUserId().equals(owner.getId())) {
            throw new SecurityException("Playlist does not belong to current user");
        }
        return playlist;
    }

    private User getUserOrThrow(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}
