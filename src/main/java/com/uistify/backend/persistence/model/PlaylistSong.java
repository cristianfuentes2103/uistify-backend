package com.uistify.backend.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "playlist_song")
public class PlaylistSong {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "playlist_id")
	private Playlist playlist;

	@ManyToOne
	@JoinColumn(name = "song_id")
	private Song song;

	@Column(name = "number_song")
	private int numberSong;
}
