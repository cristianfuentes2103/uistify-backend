package com.uistify.backend.infraestructure.persistence.jpa.entity;

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
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 256, nullable = false)
    private String title;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

    @Column(length = 128)
    private String genre;

    @Column(name = "picture_url", length = 512)
    private String pictureUrl;

    @Column(name = "source_url", length = 512, nullable = false)
    private String sourceUrl;
}
