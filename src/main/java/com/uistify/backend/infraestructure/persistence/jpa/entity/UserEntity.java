package com.uistify.backend.infraestructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private ArtistEntity artist;

}
