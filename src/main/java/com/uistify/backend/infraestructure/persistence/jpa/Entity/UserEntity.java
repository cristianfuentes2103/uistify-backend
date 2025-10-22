package com.uistify.backend.infraestructure.persistence.jpa.Entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 256, nullable = false)
	private String name;
	@Column(length = 256, unique = true, nullable = false)
	private String email;
	@Column(length = 128, nullable = false)
	private String password;

	@OneToOne(mappedBy = "user")
	private ArtistEntity artist;

	@OneToMany(mappedBy = "user")
	private List<PlaylistEntity> playlists;
}
