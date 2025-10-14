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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Canciones", description = "Catálogo público de canciones")
@RestController
@RequestMapping("/api/songs")
public class SongController {

	@Autowired
	SongService songService;

	@Operation(summary = "Retorna una lista con las canciones existentes en la base de datos.")
	@ApiResponse(responseCode = "200", description = "Catálogo de canciones.")
	@GetMapping("")
	public ResponseEntity<List<Song>> getAllSongs(){
		return new ResponseEntity<>(songService.getAllSongs() , HttpStatus.OK);
	}
}
