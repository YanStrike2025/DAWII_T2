package com.cibertec.msresolucion.dto;

public record ResolucionResponse(
		String mensaje,
		ReclamoResponse reclamo
) {
}
