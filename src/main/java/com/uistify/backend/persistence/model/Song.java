package com.uistify.backend.persistence.model;

import java.time.Duration;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private Duration duration;

	@Column(name = "release_date", nullable = false)
	private Date releaseDate;

	@ManyToOne
	@JoinColumn(name = "artist_id")
	private Artist artist;

	@ManyToOne
	@JoinColumn(name = "album_id")
	private Album album;

	@Column(length = 128)
	private String genre;

	@Column(name = "picture_url", length = 512)
	private String pictureUrl;
}
