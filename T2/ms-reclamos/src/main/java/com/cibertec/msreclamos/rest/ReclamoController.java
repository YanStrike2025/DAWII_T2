package com.cibertec.msreclamos.rest;

import com.cibertec.msreclamos.dto.ReclamoActualizarRequest;
import com.cibertec.msreclamos.dto.ReclamoRequest;
import com.cibertec.msreclamos.dto.ReclamoResponse;
import com.cibertec.msreclamos.negocio.ReclamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// revision y optimizacion CORRECTA

@RestController
@RequestMapping("/reclamos")
public class ReclamoController {

	private final ReclamoService reclamoService;

	public ReclamoController(ReclamoService reclamoService) {
		this.reclamoService = reclamoService;
	}

	@PostMapping
	public ResponseEntity<ReclamoResponse> create(@RequestBody ReclamoRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(reclamoService.createReclamo(request));
	}

	@GetMapping("/{numreclamo}")
	public ResponseEntity<ReclamoResponse> getByNumero(@PathVariable Long numreclamo) {
		return ResponseEntity.ok(reclamoService.getReclamoByNumero(numreclamo));
	}

	@PutMapping("/{numreclamo}")
	public ResponseEntity<ReclamoResponse> update(
			@PathVariable Long numreclamo,
			@RequestBody ReclamoActualizarRequest request
	) {
		return ResponseEntity.ok(reclamoService.updateReclamo(numreclamo, request));
	}
}
