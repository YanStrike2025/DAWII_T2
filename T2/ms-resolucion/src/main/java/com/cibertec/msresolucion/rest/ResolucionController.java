package com.cibertec.msresolucion.rest;

import com.cibertec.msresolucion.dto.ResolucionRequest;
import com.cibertec.msresolucion.dto.ResolucionResponse;
import com.cibertec.msresolucion.negocio.ResolucionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resoluciones")
public class ResolucionController {

	private final ResolucionService resolucionService;

	public ResolucionController(ResolucionService resolucionService) {
		this.resolucionService = resolucionService;
	}

	@PutMapping("/{numreclamo}/procedente")
	public ResponseEntity<ResolucionResponse> procedente(
			@PathVariable Long numreclamo,
			@RequestBody ResolucionRequest request
	) {
		return ResponseEntity.ok(resolucionService.resolverProcedente(numreclamo, request));
	}

	@PutMapping("/{numreclamo}/improcedente")
	public ResponseEntity<ResolucionResponse> improcedente(
			@PathVariable Long numreclamo,
			@RequestBody ResolucionRequest request
	) {
		return ResponseEntity.ok(resolucionService.resolverImprocedente(numreclamo, request));
	}
}
