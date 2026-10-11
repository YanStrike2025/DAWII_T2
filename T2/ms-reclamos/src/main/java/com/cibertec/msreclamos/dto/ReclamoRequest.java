package com.cibertec.msreclamos.dto;

public record ReclamoRequest(
		String nomcliente,
		String ultimos4tarjeta,
		String motivo
) {
}
