package com.uistify.backend.presentation.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uistify.backend.application.service.SongService;
import com.uistify.backend.infraestructure.persistence.jpa.Entity.SongEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Canciones", description = "Catálogo público de canciones")
@RestController
@RequestMapping("/api/songs")
public class SongController {

	@Autowired
	SongService songService;

	@Operation(summary = "Retorna una lista con las canciones existentes en la base de datos.",
		description = "Se obtiene un fragmento del total de canciones al especificar los parámetros `page` y `size` de la siguiente forma `/api/songs?page=<Número página>&size=<Tamaño página>`")
	@ApiResponse(responseCode = "200", description = "Catálogo de canciones.")
	@GetMapping("")
	public ResponseEntity<List<SongEntity>> getAllSongs(Pageable pageable){
		return new ResponseEntity<>(songService.getAllSongs(pageable) , HttpStatus.OK);
	}

	@Operation(summary = "Retorna la canción con el ID.")
	@ApiResponse(responseCode = "200", description = "Canción encontrada.")
	@ApiResponse(responseCode = "404", description = "No existe la canción con esa ID.")
	@GetMapping("/{songId}")
	public ResponseEntity<SongEntity> getSongById(@PathVariable Long songId){
		if (songService.getSongById(songId).isEmpty()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(songService.getSongById(songId).get(), HttpStatus.OK);
	}
}
