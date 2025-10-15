package com.uistify.backend.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "song")
public class Song {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 256, nullable = false)
	private String title;

	@Column(name = "duration", nullable = false)
	private int duration;

	@Column(name = "release_date", nullable = false)
	private LocalDate releaseDate;

	@Column(name = "artist")
	private String artist;

	@Column(name = "album")
	private String album;

	@Column(length = 128)
	private String genre;

	@Column(name = "picture_url", length = 512)
	private String pictureUrl;

	@Column(name = "source_url", length = 512, nullable = false)
	private String sourceUrl;
}
