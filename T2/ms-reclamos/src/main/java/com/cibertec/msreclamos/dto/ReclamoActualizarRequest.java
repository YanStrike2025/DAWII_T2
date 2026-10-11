package com.cibertec.msreclamos.dto;

public record ReclamoActualizarRequest(
		Integer estado,
		String analista,
		String observacion
) {
}
