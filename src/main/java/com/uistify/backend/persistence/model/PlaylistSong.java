package com.uistify.backend.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
