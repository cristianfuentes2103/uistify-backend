package com.uistify.backend.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uistify.backend.persistence.model.Song;
import com.uistify.backend.service.SongService;

@RestController
@RequestMapping("/api/songs")
public class SongController {

	@Autowired
	SongService songService;

	@GetMapping("")
	public ResponseEntity<List<Song>> getAllSongs(){
		return new ResponseEntity<>(songService.getAllSongs() , HttpStatus.OK);
	}
}
