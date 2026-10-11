package com.cibertec.msresolucion.client;

import com.cibertec.msresolucion.dto.ReclamoActualizarRequest;
import com.cibertec.msresolucion.dto.ReclamoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-reclamos")
public interface ReclamoClient {

	@GetMapping("/reclamos/{numreclamo}")
	ReclamoResponse getReclamoByNumero(@PathVariable("numreclamo") Long numreclamo);

	@PutMapping("/reclamos/{numreclamo}")
	ReclamoResponse updateReclamo(
			@PathVariable("numreclamo") Long numreclamo,
			@RequestBody ReclamoActualizarRequest request
	);
}
