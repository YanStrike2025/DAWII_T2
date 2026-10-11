package com.cibertec.msresolucion.dto;

public record ReclamoActualizarRequest(
		Integer estado,
		String analista,
		String observacion
) {
}
