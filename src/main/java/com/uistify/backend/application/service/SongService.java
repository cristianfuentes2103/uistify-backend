package com.uistify.backend.application.service;

import com.uistify.backend.domain.model.Song;
import com.uistify.backend.domain.port.in.SongUseCase;
import com.uistify.backend.domain.port.out.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongService implements SongUseCase {

    private final SongRepository songRepository;

    @Override
    public List<Song> getAllSongs(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    @Override
    public Optional<Song> getSongById(Long id) {
        return songRepository.findById(id);
    }

    @Override
    public List<Song> findByTitleContaining(String fragment) {
        return songRepository.findByTitleContaining(fragment);
    }

    @Override
    public List<Song> findByArtistContaining(String fragment) {
        return songRepository.findByArtistContaining(fragment);
    }

    @Override
    public List<Song> findByAlbumContaining(String fragment) {
        return songRepository.findByAlbumContaining(fragment);
    }
}
