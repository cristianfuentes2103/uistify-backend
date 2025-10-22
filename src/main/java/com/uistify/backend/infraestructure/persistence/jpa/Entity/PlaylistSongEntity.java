package com.uistify.backend.infraestructure.persistence.jpa.Entity;

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
public class PlaylistSongEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "playlist_id")
	private PlaylistEntity playlist;

	@ManyToOne
	@JoinColumn(name = "song_id")
	private SongEntity song;

	@Column(name = "number_song")
	private int numberSong;
}
