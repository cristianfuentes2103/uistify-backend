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
@Table(name = "album")
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 128, nullable = false)
	private String name;

	@Column(name = "release_date", nullable = false)
	private LocalDate releaseDate;

	@Column(name = "cover_image_url")
	private String coverImageUrl;

	@ManyToOne
	@JoinColumn(name = "artist_id")
	private Artist artist;
}
